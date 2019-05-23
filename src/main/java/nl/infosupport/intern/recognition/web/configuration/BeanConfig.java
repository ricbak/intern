package nl.infosupport.intern.recognition.web.configuration;

import nl.infosupport.intern.recognition.applicationservices.EntryService;
import nl.infosupport.intern.recognition.applicationservices.AzureEntryService;
import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import nl.infosupport.intern.recognition.domainservices.AzureStrategy;
import nl.infosupport.intern.recognition.domainservices.HttpClientBuilder;
import nl.infosupport.intern.recognition.domainservices.RecognitionStrategy;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryDecorator;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryDecoratorImpl;
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
    public RecognitionStrategy getAzureRecognitionStrategy(@Qualifier("getAzureRequestHandler") AzureRequestHandler requestHandler) {
        return new AzureStrategy(requestHandler);
    }

    @Bean
    public EntryService getAzureEntryService(@Qualifier("getAzureRecognitionStrategy") RecognitionStrategy strategy,
                                             @Qualifier("getPersonRepositoryAdapter") PersonRepositoryDecorator repo) {
        return new AzureEntryService(strategy, repo);
    }

    @Bean
    public PersonRepositoryDecorator getPersonRepositoryAdapter(@Qualifier("PersonRepository") PersonRepository repo){
        return new PersonRepositoryDecoratorImpl(repo);
    }

    @Bean
    public AzureRequestHandler getAzureRequestHandler(HttpClientBuilder factory) {
        return new AzureRequestHandler(factory);
    }
}
