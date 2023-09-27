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
    private int x = 10;
    private int y = 10;

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
        screen.setCharacter(this.x, this.y, TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }

    private void processKey(KeyStroke key) throws IOException {
        switch (key.getKeyType()) {
            case ArrowUp -> this.y -= 1;
            case ArrowDown -> this.y += 1;
            case ArrowLeft -> this.x -= 1;
            case ArrowRight -> this.x += 1;
        }
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();
    }

    public void run() {
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
