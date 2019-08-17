package objects.dbentities;

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
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Column(name = "score")
    private Long score;
}
