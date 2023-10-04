import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {

    public Monster(int x, int y) {
        super();
        this.position = new Position(x, y);
    }

    public void move(){
        boolean checkX = position.getX() >= 0 && position.getX() < 73;
        boolean checkY = position.getY() >= 0 && position.getY() < 21;
        if (checkY && checkX) {
            Random random = new Random();
            this.position.setX(this.position.getX() + random.nextInt(-1, 2));
            this.position.setY(this.position.getY() + random.nextInt(-1, 2));
        }
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(),position.getY()), "X");
    }
}
