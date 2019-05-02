package nl.infosupport.intern.recognition.domainservices.azure;

import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommand;
import nl.infosupport.intern.recognition.domainservices.azure.actions.person.create.CreatePersonCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePersonServiceImpl implements CreatePersonService {

    private CreatePersonCommandHandler<CreatePersonCommand> handler;
    private CreatePersonCommand command;

    @Autowired
    public CreatePersonServiceImpl(CreatePersonCommandHandler<CreatePersonCommand> handler, CreatePersonCommand command) {
        this.handler = handler;
        this.command = command;
    }

    @Override
    public String createPerson(String name) {
        handler.handle(command.setData(name));
        return handler.getResult();
    }
}