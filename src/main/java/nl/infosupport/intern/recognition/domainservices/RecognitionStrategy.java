package nl.infosupport.intern.recognition.domainservices;

import java.awt.image.RenderedImage;

public interface RecognitionStrategy {

    String getName();
    String create(String name);
    boolean train();
    void addFace(String faceId, RenderedImage image);
}
