package nl.infosupport.intern.recognition.domainservices;

import nl.infosupport.intern.recognition.domainservices.template.AzureActionTemplate;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AzureStrategyTest {

    @Mock
    AzureRequestHandler requestHandler;

    @Mock
    AzureActionTemplate action;

    @BeforeEach
    void setUp() {
//        action = Mockito.mock(AzureActionTemplate.class);
    }

    @Test
    void performActionShouldReturnResultOfAction() throws Exception {
        var validId = "904bcd46-aefc-4153-9d31-5d8ef07e5301";
        var strategy = new AzureStrategy(requestHandler);

        when(action.doAction()).thenReturn(validId);

        String result = strategy.performAction(action);


        assertThat(result.length(), is(36));
    }
}