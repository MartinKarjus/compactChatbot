package objects;


import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity(name = "ActionPlan")
@Data
@Table(name = "action_plan")
public class ActionPlan {
    /*
    CREATE TABLE action_plan (
 id BIGINT NOT NULL PRIMARY KEY,
 question_id BIGINT,
 ask_date DATE,
 asked BOOLEAN DEFAULT FALSE,
 question_group_id BIGINT,
 time_of_day_id BIGINT,
 ask_at_time TIME,
 FOREIGN KEY (question_id)
    REFERENCES question ON DELETE CASCADE,
 FOREIGN KEY (question_group_id)
    REFERENCES question_group ON DELETE CASCADE,
 FOREIGN KEY (time_of_day_id)
    REFERENCES time_of_day ON DELETE CASCADE
);
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_seq")
    @SequenceGenerator(name = "action_seq", sequenceName = "action_plan_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "question_id")
    private Long questionId;
    @Column(name = "ask_date")
    private Date askDate;
    @Column(name = "asked")
    private boolean asked;
    @Column(name = "question_group_id")
    private Long questionGroupId;
    @Column(name = "time_of_day_id")
    private Long timeOfDayId;
    @Column(name = "ask_at_time")
    private Time askAtTime;




}
