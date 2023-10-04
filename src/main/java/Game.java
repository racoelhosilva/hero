import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    private Screen screen;
    Arena arena;
    Hero hero;
    TextGraphics graphics;


    public Game(){
        try {
            Terminal terminal = new
                    DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            TerminalSize terminalSize = screen.doResizeIfNecessary();// resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveHero(Position position) {
        arena.moveHero(position);
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(graphics);
        screen.refresh();
    }

    private void processKey(KeyStroke key) throws IOException{
        arena.processKey(key, screen);
    }


    public void run() {
        this.hero = new Hero(37, 12);
        this.arena = new Arena(73, 21, hero);
        this.graphics = screen.newTextGraphics();
        try {
            while (true){
                this.draw();
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.EOF){
                    break;
                }
                this.processKey(key);
                if (!arena.verifyMonsterColision(hero.position)){
                    screen.close();
                    System.out.printf("Game Over!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
