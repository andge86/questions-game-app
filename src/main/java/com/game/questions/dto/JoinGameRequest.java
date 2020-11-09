package com.game.questions.dto;

import lombok.Data;

@Data
public class JoinGameRequest {

    private String type;
    private JoinGameParams joinGameParams = new JoinGameParams();

    @Data
    public class JoinGameParams{
        private int gameId;
        private int userId;
    }

}
