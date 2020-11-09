package com.game.questions.controller;

import com.game.questions.dto.AnswerRequest;
import com.game.questions.dto.StatisticsResponse;
import com.game.questions.model.*;
import com.game.questions.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    //    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return gameService.saveUser(user);
    }

    //   @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/game/create")
    public Game createGame(@RequestParam String gameName,
                           @RequestParam int rounds,
                           @RequestParam int createdUserId) {
        return gameService.createGame(gameName, rounds, createdUserId);
    }

    //   @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/game/join")
    public Game joinGame(@RequestParam int gameId,
                         @RequestParam int userId) {

        return gameService.addUserToGame(gameId, userId);
    }

    //  @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/game/list")
    public List<Game> getGamesList() {

        return gameService.retrieveGamesList();
    }

    @PostMapping("/game/info")
    public Game getGamesList(@RequestParam int gameId) {

        return gameService.retrieveGame(gameId);
    }

    @PostMapping("/game/statistics")
    public StatisticsResponse getGameStatistics(@RequestParam int gameId) {

        return gameService.getGameStatistics(gameId);
    }

    @PostMapping("/game/state")
    public Game updateGameState(@RequestParam int gameId,
                                @RequestParam String state) {

        return gameService.setGameState(gameId, state);
    }

    @PostMapping("/question/list")
    public List<Question> getQuestionsList(@RequestParam int gameId) {

        return gameService.retrieveQuestionsList(gameId);
    }

    //  @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/answer")
    public Answer storeAnswer(@RequestBody AnswerRequest answerRequest) {

        return gameService.saveAnswer(answerRequest);
    }


    //  @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/vote")
    public Vote vote(@RequestBody Vote vote) {
        return gameService.saveVote(vote);
    }
}
