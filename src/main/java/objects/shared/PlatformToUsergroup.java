package objects.shared;

import lombok.Data;

import javax.persistence.*;



@Entity
@Data
@Table(name = "platform_to_usergroup")
public class PlatformToUsergroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_to_usergroup_seq")
    @SequenceGenerator(name = "platform_to_usergroup_seq", sequenceName = "platform_to_usergroup_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "usergroup_id")
    private Long usergroupId;

    @Column(name = "platform_id")
    private Long platformId;


}


