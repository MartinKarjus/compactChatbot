package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "transmission_log")
public class TransmissionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transmission_log_seq")
    @SequenceGenerator(name = "transmission_log_seq", sequenceName = "transmission_log_sequence")
    private Long id;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "state")
    private String state;

    @Column(name = "user_id")
    private Long userId;
}
