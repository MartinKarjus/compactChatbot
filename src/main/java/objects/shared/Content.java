package objects.shared;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_seq")
    @SequenceGenerator(name = "content_seq", sequenceName = "content_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "leads_to_content_id")
    private Long leadsToContentId;
    @Column(name = "leads_to_question_id")
    private Long leadsToQuestionId;
    @Column(name = "text")
    private String text;
    @Column(name = "media_id")
    private Long mediaId;

}
