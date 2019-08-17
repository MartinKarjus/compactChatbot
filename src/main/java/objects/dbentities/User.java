package objects.dbentities;


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
    private Timestamp dateCreated;

    @Column(name = "date_modified")
    private Timestamp dateModified;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthdate")
    private Timestamp birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "company")
    private Long companyId;

    @Column(name = "score")
    private Long score;

    @Column(name = "question_group_id")
    private Long questionGroupId;

}

