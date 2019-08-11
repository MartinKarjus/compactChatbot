package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Answers")
@Data
@Table(name = "answers")
public class Answers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answers_seq")
    @SequenceGenerator(name = "answers_seq", sequenceName = "answers_sequence")
    private Long id;

    @Column(name = "question_id")
    private Long question_id;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "points")
    private Long points;

    @Column(name = "answer")
    private String answer;
}
