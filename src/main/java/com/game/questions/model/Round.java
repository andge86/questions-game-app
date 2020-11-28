package com.game.questions.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "round_place")
    private int roundPlace;

    @Column(name = "state")
    private String state;

    @OneToOne(targetEntity = Question.class)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;

    @Column(name = "question_text")
    private String questionText;

    @OneToMany(targetEntity = Answer.class)
    @JoinColumn(name = "belongs_to_round_id", referencedColumnName = "id")
    private List<Answer> answers = new ArrayList<>();

       @ManyToMany
       @JoinTable(
               name = "rounds_questions",
               joinColumns = @JoinColumn(name = "round_id"),
               inverseJoinColumns = @JoinColumn(name = "game_id"))
       private List<User> usersFinishedRound = new ArrayList<>();


 //   @ManyToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name ="game_id", nullable = false)
 //   private Game game;

//    @Column(name = "created_at")
//    private Date createdAt = new Date();

 //   @ManyToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name ="answer_id", nullable = false)
 //   private Answer answer;

 //   @ManyToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name ="user_id", nullable = false)
 //   private User user;

}
