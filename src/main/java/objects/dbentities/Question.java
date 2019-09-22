package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity(name = "Question")
@Data
@Table(name = "question")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "text")
    private String text;

    @Column(name = "can_be_random_selected")
    private boolean canBeRandomSelected;

    @Column(name = "leads_to_question_id")
    private Long leadsToQuestionId;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "leads_to_question_name")
    private String leadsToQuestionName;

}
