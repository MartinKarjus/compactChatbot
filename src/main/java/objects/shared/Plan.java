package objects.shared;


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
    private Timestamp date_created;

    @Column(name = "date_modified")
    private Timestamp date_modified;

    @Column(name = "question_id")
    private Long question_id;

    @Column(name = "time_to_send_id")
    private Long time_to_send_id;

    @Column(name = "day")
    private Long day;

    @Column(name = "company_id")
    private Long company_id;

}
