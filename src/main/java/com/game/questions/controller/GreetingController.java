package com.game.questions.controller;

import com.game.questions.dto.*;
import com.game.questions.model.Answer;
import com.game.questions.model.Game;
import com.game.questions.model.Round;
import com.game.questions.model.Vote;
import com.game.questions.service.GameServiceWebSockets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


@Controller
public class GreetingController {

    @Autowired
    private GameServiceWebSockets gameServiceWebSockets;

    @MessageMapping("/newAnswer")
    @SendTo("/topic/greetings")
    public GameResponse newAnswer(AnswerRequest answerRequest) {

        Game game = gameServiceWebSockets.saveAnswer(
                answerRequest.getAnswerParams().getUserId(),
                answerRequest.getAnswerParams().getGameId(),
                answerRequest.getAnswerParams().getRound(),
                answerRequest.getAnswerParams().getDescription()
        );

        if (game.getUsersBelongToGame().size() ==
                game.getRoundList().get(answerRequest.getAnswerParams().getRound() - 1).getAnswers().size())
            return new GameResponse("ALLANSWERED", game);
        else return new GameResponse(answerRequest.getType(), game);
    }

    @MessageMapping("/newGame")
    @SendTo("/topic/greetings")
    public GameResponse newGame(GameRequest gameRequest) {
        Game game = gameServiceWebSockets.saveGame(
                gameRequest.getGameParams().getCreatedUserId(),
                gameRequest.getGameParams().getName(),
                gameRequest.getGameParams().getRounds());

        return new GameResponse(gameRequest.getType(), game);
    }

    @MessageMapping("/joinGame")
    @SendTo("/topic/greetings")
    public GameResponse joinGame(JoinGameRequest joinGameRequest) {
        Game game = gameServiceWebSockets.addUserToGame(
                joinGameRequest.getJoinGameParams().getGameId(),
                joinGameRequest.getJoinGameParams().getUserId()
        );

        return new GameResponse(joinGameRequest.getType(), game);
    }

    @MessageMapping("/startGame")
    @SendTo("/topic/greetings")
    public GameResponse startGame(StartGameRequest startGameRequest) {
        Game game = gameServiceWebSockets.changeGameState(
                startGameRequest.getStartGameParams().getGameId(),
                "ACTIVE");

        return new GameResponse(startGameRequest.getType(), game);
    }

    @MessageMapping("/newVote")
    @SendTo("/topic/greetings")
    public GameResponse newVote(VoteRequest voteRequest) {
        Game game = gameServiceWebSockets.saveVote(
                voteRequest.getVoteParams().getUserId(),
                voteRequest.getVoteParams().getAnswerId(),
                voteRequest.getVoteParams().getRound(),
                voteRequest.getVoteParams().getGameId());

        List<Vote> allVotes = new ArrayList<>();
        game.getRoundList().get(voteRequest.getVoteParams().getRound() - 1).getAnswers()
                .forEach(answer -> allVotes.addAll(answer.getVotesBelongToAnswer()));

        if (game.getUsersBelongToGame().size() == allVotes.size())
            return new GameResponse("ALLVOTED", game);
        else return new GameResponse(voteRequest.getType(), game);
    }

    @MessageMapping("/finishRound")
    @SendTo("/topic/greetings")
    public GameResponse finishRound(FinishRoundRequest finishRoundRequest) {
        Game game = gameServiceWebSockets.saveFinishedRoundUser(
                finishRoundRequest.getFinishRoundParams().getUserId(),
                finishRoundRequest.getFinishRoundParams().getRound(),
                finishRoundRequest.getFinishRoundParams().getGameId());

        if (game.getState().equals("FINISHED")) {return new GameResponse("GAMEFINISHED", game);}
        else if (game.getUsersBelongToGame().size() ==
                game.getRoundList().get(finishRoundRequest.getFinishRoundParams().getRound() -1).getUsersFinishedRound().size())
            return new GameResponse("ALLFINISHEDROUNDS", game);
        else return new GameResponse(finishRoundRequest.getType(), game);
    }

}