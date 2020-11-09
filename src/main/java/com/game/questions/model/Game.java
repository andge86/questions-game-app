package com.game.questions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.*;


@Data
@Entity
@Table(name = "games")
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "rounds")
    private int rounds;

    //  @ManyToOne
    //  @JoinColumn(name ="created_by_user_id")
    //   private User createdByUser;

    @Column(name = "state")
    private String state = "NEW"; // NEW ACTIVE FINISHED

    @OneToMany(targetEntity = User.class)
    @JoinColumn(name = "belongs_to_game_id", referencedColumnName = "id")
    private List<User> usersBelongToGame = new ArrayList<>();

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
    private User createdGameUser;

    @OneToMany(targetEntity = Round.class)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private List<Round> roundList = new ArrayList<>();

//    @Column(name = "created_at")
//    private Date createdAt = new Date();


 //   @ManyToMany ()
 //   @JoinTable(
 //           name = "games_questions",
 //           joinColumns = @JoinColumn(name = "game_id"),
 //           inverseJoinColumns = @JoinColumn(name = "question_id"))
 //   private List<Question> questions = new ArrayList<>();

}