package com.game.questions.service;

import com.game.questions.dto.AnswerRequest;
import com.game.questions.dto.StatisticsResponse;
import com.game.questions.model.*;
import com.game.questions.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Game createGame(String gameName, int rounds, int createdUserId) {
        Game game = new Game();
        game.setName(gameName);
        game.setRounds(rounds);
        User user = userRepository.findById(createdUserId).orElse(null);
        List<User> belongToGameUsers = game.getUsersBelongToGame();
        belongToGameUsers.add(user);
        game.setUsersBelongToGame(belongToGameUsers);
        game.setCreatedGameUser(user);

        List<Question> givenList = questionRepository.findAll();
        Collections.shuffle(givenList);
        List<Question> randomSeries = givenList.subList(0, rounds);

        return gameRepository.save(game);
    }

    public Game addUserToGame(int gameId, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        Game game = gameRepository.findById(gameId).orElse(null);

        List<User> belongToGameUsers = game.getUsersBelongToGame();
        belongToGameUsers.add(user);
        game.setUsersBelongToGame(belongToGameUsers);
        return gameRepository.save(game);
    }

    public List<Game> retrieveGamesList() {
        return gameRepository.findAll();
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public Answer saveAnswer(AnswerRequest answerRequest) {
        Answer newAnswer = new Answer();

        User user = userRepository.findById(answerRequest.getAnswerParams().getUserId()).orElse(null);
        Game game = gameRepository.findById(answerRequest.getAnswerParams().getGameId()).orElse(null);

        newAnswer.setDescription(answerRequest.getAnswerParams().getDescription());

        newAnswer.setBelongsToUser(user);

        return answerRepository.save(newAnswer);
    }

    public List<Question> retrieveQuestionsList(int gameId) {
        //  return gameRepository.findById(gameId).orElse(null);
        return null;
    }

    public Game setGameState(int gameId, String state) {
        Game game = gameRepository.findById(gameId).orElse(null);
        game.setState(state);
        return gameRepository.save(game);
    }

    public Game retrieveGame(int gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }

    public StatisticsResponse getGameStatistics(int gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
       List<StatisticsResponse.UserStats> userStatsList = new ArrayList<>();
        List<StatisticsResponse.UserStats> userStatsListNew = new ArrayList<>();
        game.getRoundList().forEach(round -> {
            round.getAnswers().forEach(answer -> {
                StatisticsResponse.UserStats userStats = new StatisticsResponse.UserStats();
                userStats.setVotes(answer.getVotesBelongToAnswer().size());
                userStats.setName(answer.getBelongsToUser().getName());
                userStats.setId(answer.getBelongsToUser().getId());
                userStats.setPlace(0);
                userStatsList.add(userStats);
                if (userStatsList.stream().filter(u -> u.getId() == userStats.getId()).count() < 1) {
                    userStatsListNew.add(userStats);
                } else {
                    StatisticsResponse.UserStats us = userStatsList.stream().filter(u -> u.getId()
                            == userStats.getId()).findFirst().orElse(null);
                    StatisticsResponse.UserStats userStats2 = new StatisticsResponse.UserStats();
                    userStats2.setId(us.getId());
                    userStats2.setName(us.getName());
                    userStats2.setVotes(us.getVotes() + userStats.getVotes());
                    userStats2.setPlace(1);
                    userStatsListNew.remove(userStatsListNew.stream().filter(u -> u.getId()
                            == userStats.getId()).findFirst().orElse(null));
                    userStatsListNew.add(userStats2);
                }

            });

        } );
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        userStatsListNew.sort(Comparator.comparing(StatisticsResponse.UserStats::getVotes));
        userStatsListNew.forEach(us -> us.setPlace(us.getPlace() + 1));
        statisticsResponse.setUserStats(userStatsListNew);
        return statisticsResponse;
}

}
