package com.game.questions.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

 //   @ManyToOne(targetEntity = Round.class)
 //   @JoinColumn(name ="belongs_to_round_id", referencedColumnName = "id")
 //   private Round round;

 //   @OneToOne(targetEntity = User.class)
 //   @JoinColumn(name = "belongs_to_user_id", referencedColumnName = "id")
 //   private User belongsToUser;

    @OneToMany(targetEntity = Vote.class)
    @JoinColumn(name = "belongs_to_answer_id", referencedColumnName = "id")
    private List<Vote> votesBelongToAnswer = new ArrayList<>();

       @ManyToOne(targetEntity = User.class)
       @JoinColumn(name ="belongs_to_user_id", referencedColumnName = "id")
       private User belongsToUser;

//    @Column(name = "created_at")
//    private Date createdAt = new Date();
/*
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name ="user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answer")
    private List<Vote> votes = new ArrayList<>();

 */
}
