package nl.infosupport.intern.recognition.web.configuration;

import nl.infosupport.intern.recognition.applicationservices.EntryService;
import nl.infosupport.intern.recognition.applicationservices.AzureEntryService;
import nl.infosupport.intern.recognition.domainservices.AzureRequestHandler;
import nl.infosupport.intern.recognition.domainservices.AzureStrategy;
import nl.infosupport.intern.recognition.domainservices.HttpClientFactory;
import nl.infosupport.intern.recognition.domainservices.RecognitionStrategy;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepository;
import nl.infosupport.intern.recognition.domainservices.repositories.PersonRepositoryAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;

import java.awt.image.BufferedImage;

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
                                             PersonRepositoryAdapter repo, PersonRepository crudRepo) {
        return new AzureEntryService(strategy, repo, crudRepo);
    }

    @Bean
    public AzureRequestHandler getAzureRequestHandler(HttpClientFactory factory) {
        return new AzureRequestHandler(factory);
    }
}
