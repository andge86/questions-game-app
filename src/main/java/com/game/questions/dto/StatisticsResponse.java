package com.game.questions.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticsResponse {

    private List<UserStats> userStats = new ArrayList<>();

    @Data
    public static class UserStats {
        private int id;
        private String name;
        private int votes;
        private int place;
    }
}
