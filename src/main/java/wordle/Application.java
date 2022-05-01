package wordle;

import wordle.game.Game;
import wordle.game.GameView;

public class Application {
    public static void main(String[] args) {
        final String filePath = "src/main/resources/words.txt";
        final Game game = new Game(filePath, new GameView());
        game.play();
    }
}