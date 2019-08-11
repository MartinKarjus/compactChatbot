package objects.shared;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "User")
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "date_created")
    private Timestamp date_created;

    @Column(name = "date_modified")
    private Timestamp date_modified;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthdate")
    private Timestamp birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "team_id")
    private Long team_id;

    @Column(name = "company")
    private Long company_id;

    @Column(name = "score")
    private Long score;

    @Column(name = "question_group_id")
    private Long question_group_id;

}

