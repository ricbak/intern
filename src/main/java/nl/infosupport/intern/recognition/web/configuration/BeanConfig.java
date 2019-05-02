package nl.infosupport.intern.recognition.web.configuration;

import nl.infosupport.intern.recognition.applicationservices.EntryService;
import nl.infosupport.intern.recognition.applicationservices.EntryServiceImpl;
import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import nl.infosupport.intern.recognition.domainservices.AzureStrategy;
import nl.infosupport.intern.recognition.domainservices.RecognitionStrategy;
import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonService;
import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonServiceImpl;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.AzureCreatePersonCommand;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommand;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommandHandler;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Value("${azure.subscription}")
    private String subscription;

    @Value("${azure.groupid}")
    private String groupId;

    @Bean
    public String getAzureGroupId() {
        return groupId;
    }

    @Bean
    public String getAzureSubscription() {
        return subscription;
    }

    @Bean
    public RecognitionStrategy getAzureRecognitionStrategy(AzureRequestHandler requestHandler) {
        return new AzureStrategy(requestHandler);
    }

    @Bean
    public EntryService getAzureEntryService(@Qualifier("getAzureRecognitionStrategy") RecognitionStrategy strategy,
                                             PersonRepositoryAdapter repo) {
        return new EntryServiceImpl(strategy, repo);
    }

    @Bean
    public CreatePersonService getAzureCreatePersonService(CreatePersonCommandHandler<CreatePersonCommand> handler, AzureCreatePersonCommand command) {
        return new CreatePersonServiceImpl(handler, command);
    }
}
