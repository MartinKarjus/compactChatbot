package objects.shared;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "plan")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_seq")
    @SequenceGenerator(name = "plan_seq", sequenceName = "plan_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "time_to_send_id")
    private Long timeToSendId;

    @Column(name = "day")
    private Long day;

    @Column(name = "usergroup_id")
    private Long usergroupId;

}
