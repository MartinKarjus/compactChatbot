package objects.shared;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "QuestionQuery")
@Data
@Table(name = "question_query")
public class QuestionQuery {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_query_seq")
    @SequenceGenerator(name = "question_query_seq", sequenceName = "question_query_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "query_text")
    private String queryText;
    @Column(name = "query_response")
    private String queryResponse;
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "media_id")
    private Long mediaId;
    @Column(name = "leads_to_content_id")
    private Long leadsToContentId;


}
