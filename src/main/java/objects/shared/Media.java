package objects.shared;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "media_seq")
    @SequenceGenerator(name = "media_seq", sequenceName = "media_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "source")
    private String source;

}
