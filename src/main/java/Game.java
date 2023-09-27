import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {

    private Screen screen;
    Hero hero;

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

    private void draw() throws IOException{
        screen.clear();
        hero.draw(screen);
        screen.refresh();
    }

    private void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()) {
            case ArrowUp -> hero.moveUp();
            case ArrowDown -> hero.moveDown();
            case ArrowLeft -> hero.moveLeft();
            case ArrowRight -> hero.moveRight();
        }
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();
    }

    public void run() {
        this.hero = new Hero(10, 10);
        try {
            while (true){
                this.draw();
                KeyStroke key = screen.readInput();
                if (key.getKeyType() == KeyType.EOF){
                    break;
                }
                this.processKey(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
