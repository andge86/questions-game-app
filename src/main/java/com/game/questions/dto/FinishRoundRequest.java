package com.game.questions.dto;

import lombok.Data;

@Data
public class FinishRoundRequest {

    private String type;
    private FinishRoundParams finishRoundParams = new FinishRoundParams();

    @Data
    public class FinishRoundParams{
        private int userId;
        private int round;
        private int gameId;

    }

}
