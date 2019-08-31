package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Plan")
@Data
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_seq")
    @SequenceGenerator(name = "plan_seq", sequenceName = "plan_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "time_to_send_id")
    private Long timeToSendId;

    @Column(name = "day")
    private Long day;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "question_group_id")
    private Long questionGroupId;

}
