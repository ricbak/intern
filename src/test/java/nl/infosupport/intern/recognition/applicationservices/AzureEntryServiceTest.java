package nl.infosupport.intern.recognition.applicationservices;

import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonService;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import nl.infosupport.intern.recognition.web.controllers.NoUniqueNameException;
import nl.infosupport.intern.recognition.web.models.Person.SavedPerson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureEntryServiceTest {

    @Mock
    private PersonRepositoryAdapter personRepositoryAdapter;

    @Mock
    private CreatePersonService createPersonService;

    private AzureEntryService aez;

    @BeforeEach
    void setUp() {
        aez = new AzureEntryService(personRepositoryAdapter, createPersonService);
    }

    @Test
    void WhenRegisterPersonAndHasUniqueNameResultShouldBeSucceeded() {
        String uniquePersonName = "Unique Name";

        when(personRepositoryAdapter.isUniqueName(uniquePersonName)).thenReturn(Optional.of(uniquePersonName));
        when(createPersonService.createPerson(uniquePersonName)).thenReturn("mocked-person-id");
        when(personRepositoryAdapter.create(any(),any(), any())).thenReturn("succeed");

        SavedPerson result = aez.register(uniquePersonName);

        assertThat(result.getName(), is(uniquePersonName));
        assertThat(result.getId(), is("succeed"));
    }

    @Test
    void whenRegisterPersonAndHasNoUniqueNameResultShouldNotBeSucceeded() {
        String noUniqueName = "No Unique Name";

        when(personRepositoryAdapter.isUniqueName(noUniqueName)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoUniqueNameException.class, ()-> aez.register(noUniqueName));

    }

//    @Test
//    void whenSavePersonAtAzureAndGetNoResponseShouldThrowAzureTimeOutException() {
//        when(personRepositoryAdapter.findById(any())).thenReturn(Optional.of(new Person("test-name", "test-id")));
//        when(createPersonService.createPerson(any())).then((i)-> {
//            TimeUnit.SECONDS.sleep(6);
//            return "";
//        });
//
//        Assertions.assertThrows(AzureTimeOutException.class, ()-> aez.register("Rico"));
//    }
}