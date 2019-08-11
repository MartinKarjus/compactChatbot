package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Answers")
@Data
@Table(name = "answers")
public class Transmission_log {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transmission_log_seq")
    @SequenceGenerator(name = "transmission_log_seq", sequenceName = "transmission_log_sequence")
    private Long id;

    @Column(name = "question_id")
    private Long question_id;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "state")
    private String state;
}
