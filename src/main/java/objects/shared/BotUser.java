package objects.shared;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "BotUser")
@Data
@Table(name = "bot_user")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bot_user_seq")
    @SequenceGenerator(name = "bot_user_seq", sequenceName = "bot_user_sequence")
    private Long id;

    @Column(name = "usergroup_id")
    private Long usergroupId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;


}

