package com.game.questions.dto;

import lombok.Data;

@Data
public class AnswerRequest {

    private String type;
    private AnswerParams answerParams;

    @Data
    public class AnswerParams {
        private String description;
        private int round;
        private int userId;
        private int gameId;
    }
}
