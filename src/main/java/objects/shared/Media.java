package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Media")
@Data
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_seq")
    @SequenceGenerator(name = "media_seq", sequenceName = "media_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "date_modified")
    private Timestamp date_modified;

    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private String source;

}
