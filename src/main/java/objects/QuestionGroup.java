package objects;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "QuestionGroup")
@Data
@Table(name = "question_group")
public class QuestionGroup {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_query_seq")
    @SequenceGenerator(name = "question_query_seq", sequenceName = "question_query_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "query_text")
    private String platform;
    @Column(name = "query_response")
    private String company;

}
