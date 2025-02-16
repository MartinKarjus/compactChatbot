package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "QuestionGroup")
@Data
@Table(name = "question_group")
public class QuestionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_group_seq")
    @SequenceGenerator(name = "question_group_seq", sequenceName = "question_group_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "active")
    private boolean active;


}
