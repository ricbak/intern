package nl.infosupport.intern.recognition.web.controllers;

import nl.infosupport.intern.recognition.applicationservices.EntryService;
import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureTimeOutException;
import nl.infosupport.intern.recognition.web.controllers.exceptions.NoUniqueNameException;
import nl.infosupport.intern.recognition.web.models.Person.NewPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@RestController()
@RequestMapping(path = "/person")
@CrossOrigin
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final EntryService entryService;

    @Autowired
    public PersonController(@Qualifier("getAzureEntryService") EntryService entryService) {
        this.entryService = entryService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<String> registerPerson(@RequestBody NewPerson person) {

        logger.info("{}", person);

        return ResponseEntity.ok(entryService.register(person.getName()));

    }

    @GetMapping(path = "/train")
    public ResponseEntity<Boolean> train() {
        return ResponseEntity.ok(entryService.train());
    }

    @PostMapping(path = "/face/add")
    public ResponseEntity<String> registerFace(@RequestParam("personId") String personId, @RequestParam("image") MultipartFile image) {
        InputStream inputStream;

        try {
            inputStream = image.getInputStream();
        } catch (IOException e) {
            logger.info("Error: ", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(entryService.newFace(personId, inputStream));
    }

    @PostMapping(path = "/detect")
    public ResponseEntity<String> identifyPerson(@RequestParam("image") MultipartFile image) {

        InputStream inputStream;

        try {
            inputStream = image.getInputStream();
        } catch (IOException e) {
            logger.info("Error: ", e);
            return ResponseEntity.badRequest().body("Couldn't get input stream from image");
        }
            return ResponseEntity.ok(entryService.identifyPerson(inputStream));
    }
}
