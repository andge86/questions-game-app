package com.game.questions.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

  //  @OneToMany(targetEntity = User.class)
  //  @JoinColumn(name = "belongs_to_user_id", referencedColumnName = "id")
  //  private List<User> belongsToUser = new ArrayList<>();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name ="belongs_to_user_id", referencedColumnName = "id")
    private User belongsToUser;

//    @Column(name = "created_at")
//    private Date createdAt = new Date();



 //   @ManyToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name ="answer_id", nullable = false)
 //   private Answer answer;

 //   @ManyToOne(fetch = FetchType.LAZY)
 //   @JoinColumn(name ="user_id", nullable = false)
 //   private User user;

}
