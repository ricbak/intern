package nl.infosupport.intern.recognition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("prodd")
public class RecognitionApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hoi");
    }

}
