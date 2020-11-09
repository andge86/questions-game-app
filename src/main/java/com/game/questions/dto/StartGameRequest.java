package com.game.questions.dto;

import lombok.Data;

@Data
public class StartGameRequest {

    private String type;
    private StartGameParams startGameParams = new StartGameParams();

    @Data
    public class StartGameParams{
        private int gameId;
    }

}
