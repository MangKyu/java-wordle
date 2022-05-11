package wordle.app.game.wordle;

import wordle.app.bucket.adapter.ContainWordsAdapter;
import wordle.app.game.base.Game;
import wordle.app.word.adapter.InputWordsAdapter;
import wordle.app.word.domain.Answer;
import wordle.app.word.domain.MatchResult;
import wordle.app.word.domain.Words;
import wordle.app.bucket.adapter.FindAnswerAdapter;
import wordle.app.word.adapter.MatchWordsAdapter;
import wordle.app.word.domain.MatchResults;

public class WordleGame extends Game {

    private final ContainWordsAdapter containWordsAdapter;
    private final InputWordsAdapter inputWordsAdapter;
    private final MatchWordsAdapter matchWordsAdapter;
    private final WordleGameView gameView;
    private final PlayingInfo playingInfo;

    public WordleGame(
            final ContainWordsAdapter containWordsAdapter,
            final FindAnswerAdapter answerAdapter,
            final InputWordsAdapter inputWordsAdapter,
            final MatchWordsAdapter matchWordsAdapter) {

        this.containWordsAdapter = containWordsAdapter;
        this.inputWordsAdapter = inputWordsAdapter;
        this.matchWordsAdapter = matchWordsAdapter;
        this.playingInfo = new PlayingInfo(answerAdapter.findAnswer());
        this.gameView = new WordleGameView();
    }

    @Override
    protected void init() {
        gameView.initGame();
    }

    @Override
    protected void start() {
        do {
            startRound();
            updateMatchesResult(matches(inputWords()));
        } while (!playingInfo.isFinish());
    }

    private void startRound() {
        playingInfo.start();
    }

    private Words inputWords() {
        gameView.inputWords();

        Words words;
        do {
            words = inputWordsAdapter.inputWords();
        } while (!containWordsAdapter.contains(words));

        return words;
    }

    private MatchResult matches(final Words inputWords) {
        return matchWordsAdapter.matches(playingInfo.answer, inputWords);
    }

    private void updateMatchesResult(final MatchResult matchResult) {
        playingInfo.updateResult(matchResult);
        gameView.wordsMatchResults(playingInfo.matchResults);
    }

    @Override
    protected void complete() {
        if (playingInfo.isCorrect()) {
            gameView.round(playingInfo.round);
        }
    }

    private static class PlayingInfo {

        private final Answer answer;
        private final Round round;
        private final MatchResults matchResults;
        private PlayingStatus status;

        PlayingInfo(final Answer answer) {
            this.answer = answer;
            this.round = new Round();
            this.matchResults = new MatchResults();
            this.status = PlayingStatus.PLAYING;
        }

        void updateResult(final MatchResult matchResult) {
            if (matchResult.isCorrect()) {
                this.status = PlayingStatus.FINISH_CORRECT;
            }
            matchResults.add(matchResult);
        }

        boolean isFinish() {
            return status.isFinish;
        }

        boolean isCorrect() {
            return status == PlayingStatus.FINISH_CORRECT;
        }

        void start() {
            round.start();
            if (round.isFinish()) {
                this.status = PlayingStatus.FINISH_INCORRECT;
            }
        }

        private enum PlayingStatus {
            PLAYING(false),
            FINISH_INCORRECT(true),
            FINISH_CORRECT(true),
            ;

            private final boolean isFinish;

            PlayingStatus(final boolean isFinish) {
                this.isFinish = isFinish;
            }
        }

    }

}