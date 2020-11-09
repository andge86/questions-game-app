package com.game.questions.dto;

import lombok.Data;

@Data
public class VoteRequest {

    private String type;
    private VoteParams voteParams;

    @Data
    public class VoteParams {
        private int userId;
        private int answerId;
        private int round;
        private int gameId;
    }
}
