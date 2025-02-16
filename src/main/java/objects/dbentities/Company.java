package objects.dbentities;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Company")
@Data
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "name")
    private String name;
}
