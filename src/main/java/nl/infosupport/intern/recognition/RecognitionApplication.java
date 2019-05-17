package nl.infosupport.intern.recognition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class RecognitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecognitionApplication.class, args);
    }

}
