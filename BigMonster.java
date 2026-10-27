import java.util.Scanner;

public class BigMonster extends Monster {
    BigMonster(int sizeBoard) {
        super(sizeBoard);
        setImage("\uD83D\uDC79"); // 👹
    }

    BigMonster(int x, int y) {
        super(x, y);
        setImage("\uD83D\uDC79");
    }

    @Override
    public boolean taskMonster(int difficulty, Scanner scanner) {
        // Большой монстр всегда на одну ступень сложнее
        int boosted = Math.min(difficulty + 1, 5);
        System.out.println("👹 Это БОЛЬШОЙ монстр — задача сложнее!");
        return super.taskMonster(boosted, scanner);
    }
}