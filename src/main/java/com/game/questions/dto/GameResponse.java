package com.game.questions.dto;

import com.game.questions.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameResponse {

    private String type;
    private Game game;
}
