package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "plan_accomplished")
public class PlanAccomplished { //todo remove? can just create a view based on plan+question+tranmission_log join to show it, also theres no way to find out if content is sent atm

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_accomplished_seq")
    @SequenceGenerator(name = "plan_accomplished_seq", sequenceName = "plan_accomplished_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "content_sent")
    private boolean contentSent;

}
