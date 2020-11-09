package com.game.questions.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;


@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;


//    @Column(name = "created_at")
//    private Date createdAt = new Date();

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
//    private List<Answer> answers = new ArrayList<>();

 //   @ManyToMany(mappedBy = "questions")
 //   private List<Game> games = new ArrayList<>();

}
