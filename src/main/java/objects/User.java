package objects;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Person")
@Data
@Table(name = "person")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "question_group_id")
    private Long questionGroupId;


}
