package objects;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity(name = "TimeOfDay")
@Data
@Table(name = "time_of_day")
public class TimeOfDay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_of_day_seq")
    @SequenceGenerator(name = "time_of_day_seq", sequenceName = "time_of_day_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "ask_before_time")
    private Time askBeforeTime;
    @Column(name = "ask_after_time")
    private Time askAfterTime;
}
