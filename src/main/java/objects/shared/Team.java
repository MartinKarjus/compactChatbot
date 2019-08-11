package objects.shared;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Team")
@Data
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name = "team_seq", sequenceName = "team_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "date_modified")
    private Timestamp date_modified;

    @Column(name = "score")
    private Long score;
}
