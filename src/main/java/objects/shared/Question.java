package objects.shared;


import lombok.Data;

import javax.persistence.*;


@Entity(name = "Question")
@Data
@Table(name = "question")
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "question_sequence", allocationSize = 1)
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "can_be_random_selected")
    boolean canBeRandomSelected;
    @Column(name = "media_id")
    private Long mediaId;

}
