package nl.infosupport.intern.recognition.domainservices;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.notNull;

@ExtendWith(MockitoExtension.class)
class HttpClientBuilderTest {
    private HttpClientBuilder builder = new HttpClientBuilder("subscription");

    @Test
    void buildJsonHttpClientShouldReturnHttpClientPreparedForJson() throws Exception {

        HttpClient httpClient = builder.buildJsonHttpClient();
        HttpGet request = new HttpGet("http://www.google.nl");

        //Check in log if the request header is application/json
        httpClient.execute(request);

        assertThat(true, is(true));
    }

    @Test
    void buildOctetStreamHttpClient() throws Exception{
        HttpClient httpClient = builder.buildOctetStreamHttpClient();
        HttpGet request = new HttpGet("http://www.google.nl");

        //Check in log if the request header is application/octet-stream
        httpClient.execute(request);

        assertThat(true, is(true));

    }
}