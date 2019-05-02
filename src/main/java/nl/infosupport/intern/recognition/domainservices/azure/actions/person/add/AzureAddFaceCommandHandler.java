package nl.infosupport.intern.recognition.domainservices.azure.actions.person.add;

import org.springframework.stereotype.Component;

@Component
public class AzureAddFaceCommandHandler{

//    private static Logger logger = LoggerFactory.getLogger(AzureAddFaceCommandHandler.class);
//    private final String subscription;
//
//    private String result;
//
//    public AzureAddFaceCommandHandler(@Qualifier("getAzureSubscription") String subscriptionKey) {
//        this.subscription = subscriptionKey;
//    }
//
//    @Override
//    public void handle(C command) {
//
//        UriBuilder uriBuilder = new DefaultUriBuilderFactory().builder();
//        URI uri = uriBuilder
//                .scheme("https")
//                .host("westeurope.api.cognitive.microsoft.com")
//                .path("face/v1.0")
//                .path("/persongroups")
//                .path("/" + command.getGroupId())
//                .path("/persons")
//                .path("/" + command.getPersonId())
//                .path("/persistedFaces")
//                .build();
//
//        try {
//            var byteArrayOutputStream = new ByteArrayOutputStream();
//            ImageIO.write(command.getImage(), "jpg", byteArrayOutputStream);
//
//            var httpClient = HttpClientFactory.faceApiHttpClient(subscription, APPLICATION_OCTET_STREAM);
//
//            var httpPost = new HttpPost(uri);
//            httpPost.setEntity(new ByteArrayEntity(byteArrayOutputStream.toByteArray()));
//
//            var response = httpClient.execute(httpPost);
//
//            logger.debug("StatusCode: {}", response.getStatusLine().getStatusCode());
//
//            JSONObject jsonResult = new JSONObject(EntityUtils.toString(response.getEntity()));
//
//            result = jsonResult.optString("persistedFaceId");
//
//        } catch (IOException | JSONException e) {
//            logger.debug("Exception", e);
//        }
//    }
//
//    @Override
//    public String getResult() {
//        return result;
//    }
}
