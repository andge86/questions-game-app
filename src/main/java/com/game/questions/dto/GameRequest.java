package com.game.questions.dto;

import lombok.Data;

@Data
public class GameRequest {

    private String type;
    private GameParams gameParams = new GameParams();

    @Data
    public class GameParams{
        private String name;
        private int rounds;
        private int createdUserId;
    }

}
