package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="time_as_string")
public class TimeAsString {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_as_string_seq")
    @SequenceGenerator(name = "time_as_string_seq", sequenceName = "time_as_string_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "send_time")
    private Date sendTime;
}
