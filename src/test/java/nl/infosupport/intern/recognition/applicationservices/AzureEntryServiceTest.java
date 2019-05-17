package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domainservices.AzureStrategy;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryDecorator;
import nl.infosupport.intern.recognition.web.controllers.exceptions.ImageFormatException;
import nl.infosupport.intern.recognition.web.controllers.exceptions.NoUniqueNameException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AzureEntryServiceTest {

    @Mock
    private PersonRepositoryDecorator repo;

    @Mock
    AzureStrategy azureStrategy;

    private EntryService entryService;

    @BeforeEach
    void setUp() {
        entryService = new AzureEntryService(azureStrategy, repo);
    }

    @Test
    void WhenRegisterPersonAndHasUniqueNameResultShouldBeSucceeded() throws Exception {
        String uniquePersonName = "Unique Name";
        String mockedPersonId = "mocked-person-id";

        when(repo.isUniqueName(any())).thenReturn(Optional.of(uniquePersonName));
//        when(azureStrategy.performAction(any())).thenReturn(new JSONObject().put("personId", mockedPersonId).toString());

        when(repo.create(any(), any(), any())).thenReturn(mockedPersonId);

        String savedPersonId = entryService.register(uniquePersonName);

        assertThat(savedPersonId, is(mockedPersonId));

    }

    @Test
    void whenRegisterPersonAndHasNoUniqueNameResultShouldNotBeSucceeded() {
        String noUniqueName = "No Unique Name";

        when(repo.isUniqueName(noUniqueName)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUniqueNameException.class, () -> entryService.register(noUniqueName));

    }


    @Test
    void whenAddFaceWithNoImageInputStreamShouldThrowException() throws Exception {
        var inputStream = ByteArrayInputStream.nullInputStream();
        Assertions.assertThrows(ImageFormatException.class, () -> entryService.newFace("tst", inputStream));
    }

//    @Test
//    void displayImage() throws Exception {
////        FileInputStream image = new FileInputStream("C:\\Users\\ricob\\Google Drive\\School\\Informatica\\Afstuderen\\Opdracht\\Visualisatie\\woman-face.jpg");
////
////
////        BufferedImage bufferedImage = ImageIO.read(image);
////
////        JFrame frame = new JFrame();
////        JLabel label = new JLabel(new ImageIcon(bufferedImage));
////        frame.setLayout(new FlowLayout());
////        frame.setSize(200, 300);
////        frame.add(label);
////        frame.setDefaultCloseOperation
////                (JFrame.EXIT_ON_CLOSE);
////        frame.pack();
////        frame.setVisible(true);
////
////        Thread.currentThread().sleep(100000);
//    }
}