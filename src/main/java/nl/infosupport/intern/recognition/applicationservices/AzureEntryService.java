package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domain.Person;
import nl.infosupport.intern.recognition.domainservices.RecognitionStrategy;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import nl.infosupport.intern.recognition.domainservices.template.*;
import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureCouldNotIdentifyException;
import nl.infosupport.intern.recognition.web.controllers.exceptions.ImageFormatException;
import nl.infosupport.intern.recognition.web.controllers.exceptions.NoUniqueNameException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class AzureEntryService implements EntryService {
    private static Logger logger = LoggerFactory.getLogger(AzureEntryService.class);

    //Could be any Azure, Google
    private RecognitionStrategy strategy;

    private PersonRepositoryAdapter repo;
    private PersonRepository crudRepo;

    public AzureEntryService(@Qualifier("getAzureRecognitionStrategy") RecognitionStrategy strategy,
                             PersonRepositoryAdapter repo, PersonRepository crudRepo) {
        this.strategy = strategy;
        this.repo = repo;
        this.crudRepo = crudRepo;
    }

    @Override
    public String register(String name) {
        logger.info("{}", Thread.currentThread().getName());

        repo.isUniqueName(name)
                .orElseThrow(() -> new NoUniqueNameException(name));

        var action = new AzureActionCreatePerson(name);

        var personId = CompletableFuture
                .supplyAsync(() ->
                        strategy.performAction(action));

        String savedPersonId = repo.create(name, personId, strategy.getStrategyId());

        return savedPersonId;
    }

    @Override
    public boolean train() {
        String result = strategy.performAction(new AzureActionTrain());

        return result.isEmpty();
    }

    @Override
    public String newFace(String personId, InputStream image) {

        byte[] imageBytes;

        try {
            imageBytes = image.readAllBytes();

            //Check if image is valid
            ImageIO.read(new ByteArrayInputStream(imageBytes)).toString();

        } catch (Exception e) {
            throw new ImageFormatException("Not a image");
        }


        String newFaceId = strategy.performAction(new AzureActionAddFace(personId, imageBytes));

        Executors.newSingleThreadExecutor().submit(() -> strategy.performAction(new AzureActionTrain()));

        return newFaceId;

    }

    @Override
    public String identifyPerson(InputStream image) {

        byte[] imageBytes = {};

        try {
            imageBytes = image.readAllBytes();

            //Check if image is valid
            ImageIO.read(new ByteArrayInputStream(imageBytes)).toString();
        } catch (Exception e) {
            throw new ImageFormatException("Not a image");
        }

        //detect face in the image
        String faceId = strategy.performAction(new AzureActionDetectFace(imageBytes));

        //identify face based on detected faceId
        String candidate = strategy.performAction(new AzureActionIdentifyPerson(faceId));
        logger.info("{}",candidate);

        //check if confidence is above 0.5 and return person's name
        try {
            var jsonObject = new JSONObject(candidate);
            int confidence = jsonObject.optInt("confidence");
            if(confidence > 0.5){
                String personId = jsonObject.optString("personId");
                Optional<Person> identifiedPerson = crudRepo.findByAzureId(personId);

                return identifiedPerson.orElseThrow().getName();
            } else {
                throw new AzureCouldNotIdentifyException("Kan geen identificatie doen, zekerheid is: " + confidence);
            }
        } catch (JSONException e) {
            logger.info("Error: ", e);
            throw new RuntimeException(e);
        }
    }
}
