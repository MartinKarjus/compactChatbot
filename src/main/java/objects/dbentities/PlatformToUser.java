package objects.dbentities;

import lombok.Data;

import javax.persistence.*;



@Entity(name = "PlatformToUser")
@Data
@Table(name = "platform_to_user")
public class PlatformToUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_to_user_seq")
    @SequenceGenerator(name = "platform_to_user_seq", sequenceName = "platform_to_user_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "platform_specific_data")
    private String platformSpecificData;

}


