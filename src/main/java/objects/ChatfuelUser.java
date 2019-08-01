package objects;

import lombok.Data;

import javax.persistence.*;


@Entity(name = "ChatfuelUser")
@Data
@Table(name = "chatfuel")
public class ChatfuelUser {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chatfuel_seq")
    @SequenceGenerator(name = "chatfuel_seq", sequenceName = "chatfuel_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "person_id")
    private Long personId;
    @Column(name = "chatfuel_id")
    private String chatfuelId;


}
