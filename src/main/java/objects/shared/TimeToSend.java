package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Table(name = "time_to_send")
public class TimeToSend {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_to_send_seq")
    @SequenceGenerator(name = "time_to_send_seq", sequenceName = "time_to_send_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "time_to_send")
    private Date time_to_send;
}
