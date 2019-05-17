package nl.infosupport.intern.recognition.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Person {

    private static Logger logger = LoggerFactory.getLogger(Person.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;
    private String azureId;
    private String source;

    @OneToMany(
            mappedBy="person",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Identification> identifications;

    public Person(String name, String azureId) {
        this.name = name;
        this.azureId = azureId;

        identifications = new ArrayList<>();
    }

    public void setName(String name){
        logger.info("set name: {}", name);
        this.name = name;
    }

    public void setAzureId(String azureId){
        logger.info("set azureId: {}", azureId);
        this.azureId = azureId;
    }

    public void addIdentification(Identification identification){
        this.identifications.add(identification);
    }

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public void setSource(String source) {
        this.source = source;
    }
}
