package objects.dbentities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_seq")
    @SequenceGenerator(name = "platform_seq", sequenceName = "platform_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

}