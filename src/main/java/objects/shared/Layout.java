package objects.shared;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "layout")
public class Layout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "layout_seq")
    @SequenceGenerator(name = "layout_seq", sequenceName = "layout_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "type")
    private String type;
}
