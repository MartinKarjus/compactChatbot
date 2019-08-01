package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "usergroup")
public class Usergroup {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usergroup_seq")
    @SequenceGenerator(name = "usergroup_seq", sequenceName = "usergroup_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "start_date")
    private Date startDate;


}
