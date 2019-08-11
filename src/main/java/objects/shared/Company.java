package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "Company")
@Data
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_sequence")
    private Long id;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "name")
    private String name;
}
