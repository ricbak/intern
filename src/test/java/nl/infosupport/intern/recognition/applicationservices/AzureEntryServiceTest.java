package nl.infosupport.intern.recognition.applicationservices;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureEntryServiceTest {
//
//    @Mock
//    private PersonRepositoryAdapter personRepositoryAdapter;
//
//    @Mock
//    private CreatePersonService createPersonService;
//
//    private AzureEntryService aez;
//
//    @BeforeEach
//    void setUp() {
//        aez = new AzureEntryService(personRepositoryAdapter, createPersonService);
//    }
//
//    @Test
//    void WhenRegisterPersonAndHasUniqueNameResultShouldBeSucceeded() {
//        String uniquePersonName = "Unique Name";
//
//        when(personRepositoryAdapter.isUniqueName(uniquePersonName)).thenReturn(Optional.of(uniquePersonName));
//        when(createPersonService.createPerson(uniquePersonName)).thenReturn("mocked-person-id");
//        when(personRepositoryAdapter.create(any(),any(), any())).thenReturn("succeed");
//
//        SavedPerson result = aez.register(uniquePersonName);
//
//        assertThat(result.getName(), is(uniquePersonName));
//        assertThat(result.getId(), is("succeed"));
//    }
//
//    @Test
//    void whenRegisterPersonAndHasNoUniqueNameResultShouldNotBeSucceeded() {
//        String noUniqueName = "No Unique Name";
//
//        when(personRepositoryAdapter.isUniqueName(noUniqueName)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(NoUniqueNameException.class, ()-> aez.register(noUniqueName));
//
//    }
//
////    @Test
////    void whenSavePersonAtAzureAndGetNoResponseShouldThrowAzureTimeOutException() {
////        when(personRepositoryAdapter.findById(any())).thenReturn(Optional.of(new Person("test-name", "test-id")));
////        when(createPersonService.createPerson(any())).then((i)-> {
////            TimeUnit.SECONDS.sleep(6);
////            return "";
////        });
////
////        Assertions.assertThrows(AzureTimeOutException.class, ()-> aez.register("Rico"));
////    }
}