public class Element {
    public Position position;


    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    public Element() {}

    public Position getPosition() {
        return position;
    }
}
