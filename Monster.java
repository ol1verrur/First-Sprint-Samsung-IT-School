import java.util.Random;
import java.util.Scanner;

public class Monster {
    private String image = "\uD83E\uDDDF"; // 🧟
    private final int x, y;

    Monster(int sizeBoard) {
        Random random = new Random();
        x = 1 + random.nextInt(sizeBoard);
        y = 1 + random.nextInt(sizeBoard - 1);
    }

    Monster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean conflictPerson(int personX, int personY) {
        return personX == x && personY == y;
    }

    public boolean taskMonster(int difficulty, Scanner scanner) {
        MathTask task = MathTask.generate(difficulty);
        System.out.println("Решите задачу:");
        System.out.println("Реши пример: " + task.getQuestion());

        while (true) {
            String line = scanner.nextLine().trim();
            try {
                int answer = Integer.parseInt(line);
                if (answer == task.getAnswer()) {
                    System.out.println("✅ Верно! Ты победил монстра");
                    return true;
                } else {
                    System.out.println("❌ Неверно! Правильный ответ: " + task.getAnswer());
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число:");
            }
        }
    }

    protected boolean askMultipleChoice(Scanner scanner, String header,
                                        String question, String[] options,
                                        String correctAnswer) {
        System.out.println(header);
        System.out.println(question);
        String[] letters = {"A", "B", "C", "D"};
        for (int i = 0; i < 4; i++) {
            System.out.println("  " + letters[i] + ") " + options[i]);
        }
        System.out.println("Введи букву ответа (A, B, C или D):");

        while (true) {
            String line = scanner.nextLine().trim().toUpperCase();
            if (line.length() != 1) {
                System.out.println("Введи одну букву: A, B, C или D");
                continue;
            }
            int idx = -1;
            for (int i = 0; i < 4; i++) {
                if (letters[i].charAt(0) == line.charAt(0)) { idx = i; break; }
            }
            if (idx < 0) {
                System.out.println("Такого варианта нет. Введи A, B, C или D:");
                continue;
            }
            if (options[idx].equals(correctAnswer)) {
                System.out.println("✅ Верно! Ты победил монстра.");
                return true;
            } else {
                System.out.println("❌ Неверно. Правильный ответ: " + correctAnswer);
                return false;
            }
        }
    }
}
