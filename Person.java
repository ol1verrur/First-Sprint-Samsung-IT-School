import java.util.Random;

public class Person {
    private int x, y;
    private String image = "\uD83E\uDDD9"; // 🧙
    private int live = 3;
    private boolean hasKey = false;
    private final int sizeBoard;

    Person(int sizeBoard) {
        this.sizeBoard = sizeBoard;
        Random random = new Random();
        x = 1 + random.nextInt(sizeBoard);
        y = sizeBoard;
    }

    Person(int x, int y, int sizeBoard) {
        this.sizeBoard = sizeBoard;
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getLive() { return live; }
    public String getImage() { return image; }
    public boolean hasKey() { return hasKey; }

//    public void setImage(String image) { this.image = image; }
    public void pickUpKey() { this.hasKey = true; }

    public boolean isMoveCorrect(int x, int y) {
        if (x < 1 || x > sizeBoard || y < 1 || y > sizeBoard) return false;
        return (this.x == x && Math.abs(this.y - y) == 1)
                || (this.y == y && Math.abs(this.x - x) == 1);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void downLive() {
        if (live > 0) live--;
    }
}