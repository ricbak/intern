package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapterImpl;
import nl.infosupport.intern.recognition.web.controllers.exceptions.AzureTimeOutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class PersonRepositoryAdapterImplTest {

    @Mock
    private PersonRepository repo;
    private PersonRepositoryAdapter cs;

    @BeforeEach
    void setUp() {
        cs = new PersonRepositoryAdapterImpl(repo);
    }

    @Test
    void newPersonShouldHaveFaceIdFromAzureWhenAzureCompletesInTime() throws Exception {
        //
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("test-face-id");

        String result = cs.create("test-name-in-time", completedFuture, "");

        assertThat(result, is("test-face-id"));
    }

    @Test
    void whenAzureDoesNotCompleteInTimeShouldThrowAzureTimeOutException() {

        CompletableFuture<String> neverCompletedFuture = new CompletableFuture<>();

        Assertions.assertThrows(AzureTimeOutException.class, ()-> cs.create("Rico", neverCompletedFuture, " "));
    }

    private void sleep(long sleep, TimeUnit timeUnit) {
        int i = 0;
        try {
            while (i < sleep) {
                i++;
                System.out.printf("%s: sleeping %d %s\n", Thread.currentThread().getName(), i, timeUnit.toString());
                timeUnit.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("%s: done waiting\n", Thread.currentThread().getName());
    }
}