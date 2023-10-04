import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.googlecode.lanterna.TextColor.*;
import static com.googlecode.lanterna.TextColor.Factory.*;

public class Arena {
    int width;
    int height;
    Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 30; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 15; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Wall> createWalls() {
        walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    Arena (int w, int h, Hero heroPassed){
        width = w;
        height = h;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        hero = heroPassed;

    }

    public boolean canHeroMove(Position position){
        boolean checkX = position.getX() >= 0 && position.getX() < width;
        boolean checkY = position.getY() >= 0 && position.getY() < height;
        if (checkY && checkX){
            for (Wall wall : walls){
                if (wall.getPosition().equals(position)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void draw(TextGraphics graphics) throws IOException {
        graphics.setBackgroundColor(fromString("#282828"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(73, 21), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster: monsters)
            monster.draw(graphics);
        hero.draw(graphics);

    }

    public void processKey(KeyStroke key, Screen screen) throws IOException {
        switch (key.getKeyType()) {
            case ArrowUp -> moveHero(hero.moveUp());
            case ArrowDown -> moveHero(hero.moveDown());
            case ArrowLeft -> moveHero(hero.moveLeft());
            case ArrowRight -> moveHero(hero.moveRight());
        }
        if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') screen.close();
        moveMonsters();
    }

    private void moveMonsters() {
        for (Monster monster : monsters){
            monster.move();
        }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
        retrieveCoins(position);
        verifyMonsterColision(position);
    }

    public boolean verifyMonsterColision(Position position) {
        for (Monster monster : monsters){
            if (monster.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    private void retrieveCoins(Position position) {
        for (Coin coin : coins){
            if (coin.getPosition().equals(position)){
                coins.remove(coin);
                break;
            }
        }
    }
}
