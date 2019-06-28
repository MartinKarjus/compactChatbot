package objects;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "AskedQuestions")
@Data
@Table(name = "asked_questions")
public class AskedQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asked_questions_seq")
    @SequenceGenerator(name = "asked_questions_seq", sequenceName = "asked_questions_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "person_id")
    private Long personId;
    @Column(name = "question_group_id")
    private Long questionGroupId;
    @Column(name = "question_id")
    private Long questionId;


}
