package nl.infosupport.intern.recognition.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Identification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double confidence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    public Identification(double confidence) {
        this.confidence = confidence;
    }
}
