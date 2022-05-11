package wordle.app.word.domain;

import org.assertj.core.util.VisibleForTesting;

import java.util.List;

public class MatchResult {

    private static final List<MatchStatus> GREEN_ONLY = List.of(MatchStatus.GREEN);
    private final List<MatchStatus> matchStatusList;

    MatchResult(final List<MatchStatus> matchesList) {
        this.matchStatusList = matchesList;
    }

    public boolean isCorrect() {
        return containsGreenOnly();
    }

    private boolean containsGreenOnly() {
        return GREEN_ONLY.containsAll(matchStatusList);
    }

    @VisibleForTesting
    List<MatchStatus> getMatchStatusList() {
        return matchStatusList;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final MatchStatus status : matchStatusList) {
            stringBuilder.append(status);
        }
        return stringBuilder.toString();
    }

}