package com.game.questions.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.*;


@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

 //   @OneToMany(targetEntity = Game.class)
 //   @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
 //   private List<Game> createdByUserGames = new ArrayList<>();

 //   @ManyToOne
 //   @JoinColumn(name ="belongs_to_game_id")
 //   private Game belongsToGame;

 //   @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
 //   private List<Vote> votes = new ArrayList<>();

//    @Column(name = "created_at")
//    private Date createdAt = new Date();

}
