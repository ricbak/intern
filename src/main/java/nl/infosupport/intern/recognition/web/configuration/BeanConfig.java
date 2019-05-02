package nl.infosupport.intern.recognition.web.configuration;

import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonService;
import nl.infosupport.intern.recognition.domainservices.azure.CreatePersonServiceImpl;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.AzureCreatePersonCommand;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommand;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommandHandler;
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
    public String getAzureGroupId(){
        return groupId;
    }

    @Bean
    public String getAzureSubscription(){
        return subscription;
    }

    @Bean
    public CreatePersonService getAzureCreatePersonService(CreatePersonCommandHandler<CreatePersonCommand> handler, AzureCreatePersonCommand command){
        return new CreatePersonServiceImpl(handler, command);
    }
}
