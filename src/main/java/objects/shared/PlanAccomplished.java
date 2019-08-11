package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "plan_accomplished")
public class PlanAccomplished {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_accomplished_seq")
    @SequenceGenerator(name = "plan_accomplished_seq", sequenceName = "plan_accomplished_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plan_id")
    private Long planId;

}
