package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "time_to_send")
public class TimeToSend {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_to_send_seq")
    @SequenceGenerator(name = "time_to_send_seq", sequenceName = "time_to_send_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "time_as_datetime")
    private Date timeAsDateTime;
    @Column(name = "time_as_string_id")
    private Long timeAsStringId;
}
