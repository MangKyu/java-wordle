package wordle.game.wordle;

import camp.nextstep.edu.missionutils.Console;
import wordle.domain.Words;

class Player {

    public Words inputWords() {
        return new Words(Console.readLine());
    }

}