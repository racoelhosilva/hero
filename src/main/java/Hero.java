import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private int x;
    private int y;
    public Hero(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Screen screen){
        screen.setCharacter(x,
                y,
                TextCharacter.fromCharacter('R')[0]);
    }

    public void moveUp(){
        y--;
    }
    public void moveDown(){
        y++;
    }
    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
