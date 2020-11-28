package com.game.questions.service;

import com.game.questions.model.*;
import com.game.questions.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceWebSockets {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private VoteRepository voteRepository;

    public Game saveAnswer(int userId, int gameId, int roundOrder, String description) {
        User user = userRepository.findById(userId).orElse(null);

        Answer answer = new Answer();
        answer.setDescription(description);
        answer.setBelongsToUser(user);
        //   answer.setRound(round1);
        answerRepository.save(answer);

        Game game = gameRepository.findById(gameId).orElse(null);
        List<Round> rounds = game.getRoundList();
        Round round = rounds.get(roundOrder - 1);
        List<Answer> answers = round.getAnswers();
        answers.add(answer);

        round.setAnswers(answers);
        round.setState("ACTIVE");
        roundRepository.save(round);

        return gameRepository.findById(gameId).orElse(null);
    }

    public Game saveGame(int createdUserId, String gameName, int roundQuantity) {
        User user = userRepository.findById(createdUserId).orElse(null);
        Game game = new Game();
        game.setCreatedGameUser(user);
        game.setUsersBelongToGame(Arrays.asList(user));
        game.setName(gameName);
        game.setRounds(roundQuantity);

        List<Question> givenList = questionRepository.findAll();
        Collections.shuffle(givenList);
        List<Question> randomQuestionsList = givenList.subList(0, roundQuantity);

        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < roundQuantity; i++) {
            Round round = new Round();
            Question question = randomQuestionsList.get(i);
            round.setQuestion(question);
            round.setRoundPlace(i + 1);
            round.setState("NEW");
            rounds.add(roundRepository.save(round));
        }
        game.setRoundList(rounds);

        return gameRepository.save(game);
    }

    public Game changeGameState(int gameId, String state) {
        Game game = gameRepository.findById(gameId).orElse(null);
        game.setState(state);
        randomizeNamesInQuestions(game);

        return gameRepository.save(game);
    }

    public void randomizeNamesInQuestions(Game game) {
        List<String> userNames = game.getUsersBelongToGame()
                .stream().map(user -> user.getName()).collect(Collectors.toList());
      //  List<Round> roundList = new ArrayList<>();
        game.getRoundList().forEach(round -> {
            Collections.shuffle(userNames);
            String questionText = round.getQuestion().getDescription()
                    .replaceAll("name1", userNames.subList(0, 2).get(0))
                    .replaceAll("name2", userNames.subList(0, 2).get(1));
            round.setQuestionText(questionText);
          //  roundList.add(round);
            roundRepository.save(round);
        });
    }

    public Game addUserToGame(int gameId, int userId) {
        User user = userRepository.findById(userId).orElse(null);
        Game game = gameRepository.findById(gameId).orElse(null);
        List<User> belongToGameUsers = game.getUsersBelongToGame();
        belongToGameUsers.add(user);
        game.setUsersBelongToGame(belongToGameUsers);

        return gameRepository.save(game);
    }

    public Game saveVote(int userId, int answerId, int round, int gameId) {
        User user = userRepository.findById(userId).orElse(null);
        Vote vote = new Vote();
        vote.setBelongsToUser(user);
        voteRepository.save(vote);

        Answer answer = answerRepository.findById(answerId).orElse(null);
        List<Vote> votes = answer.getVotesBelongToAnswer();
        votes.add(vote);
        answerRepository.save(answer);

        return gameRepository.findById(gameId).orElse(null);
    }

    public Game saveFinishedRoundUser(int userId, int round, int gameId) {
        User user = userRepository.findById(userId).orElse(null);
        Game game = gameRepository.findById(gameId).orElse(null);

        Round round1 = game.getRoundList().get(round - 1);
        if (round1.getUsersFinishedRound() == null) {
            round1.setUsersFinishedRound(Arrays.asList(user));
        } else {
            List<User> usersFinishedRound = round1.getUsersFinishedRound();
            usersFinishedRound.add(user);
            round1.setUsersFinishedRound(usersFinishedRound);
        }
        if (round1.getUsersFinishedRound().size() == game.getUsersBelongToGame().size()) {
            round1.setState("FINISHED");
        }
        roundRepository.save(round1);

        Game game1 = gameRepository.findById(gameId).orElse(null);
        if (game1.getRoundList().stream().filter(r -> r.getState().equals("FINISHED")).count()
                == game1.getRoundList().size()) {
            game1.setState("FINISHED");
            return gameRepository.save(game1);
        } else {
            return gameRepository.findById(gameId).orElse(null);
        }
    }
}
