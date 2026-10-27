import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String CASTLE = "\uD83C\uDFF0"; // 🏰
    private static final String KEY    = "\uD83D\uDD11"; // 🔑
    private static final String EMPTY  = "  ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sizeBoard = 5;

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");
        String answer = scanner.nextLine().trim().toUpperCase();
        System.out.println("Ваш ответ:\t" + answer);

        if (!answer.equals("ДА")) {
            System.out.println("Жаль, приходи ещё!");
            return;
        }

        int difficulty = readInt(scanner, "Выбери сложность игры (от 1 до 5):", 1, 5);
        System.out.println("Выбранная сложность:\t" + difficulty);

        printLegend();

        // ---- Инициализация поля ----
        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 0; y < sizeBoard; y++)
            for (int x = 0; x < sizeBoard; x++)
                board[y][x] = EMPTY;

        Random random = new Random();

        // Замок
        int castleX = 1 + random.nextInt(sizeBoard);
        int castleY = 1;
        board[castleY - 1][castleX - 1] = CASTLE;

        // Игрок
        Person person = new Person(sizeBoard);

        // Ключ — в случайную свободную клетку
        int keyX, keyY;
        do {
            keyX = 1 + random.nextInt(sizeBoard);
            keyY = 1 + random.nextInt(sizeBoard);
        } while ((keyX == castleX && keyY == castleY)
                || (keyX == person.getX() && keyY == person.getY()));
        board[keyY - 1][keyX - 1] = KEY;

        // Монстры — во все оставшиеся клетки, кроме стартовой строки игрока
        List<Monster> monsters = new ArrayList<>();
        for (int y = 1; y < sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                if (!board[y - 1][x - 1].equals(EMPTY)) continue;
                if (x == person.getX() && y == person.getY()) continue;

                Monster m = spawnRandomMonster(random, x, y);
                board[y - 1][x - 1] = m.getImage();
                monsters.add(m);
            }
        }

        // ---- Игровой цикл ----
        int step = 0;
        boolean won = false;

        while (true) {
            board[person.getY() - 1][person.getX() - 1] = person.getImage();
            outputBoard(board, person.getLive(), person.hasKey());

            System.out.println("Введите, куда будет ходить персонаж "
                    + "(ход возможен только по вертикали или горизонтали на одну клетку)");
            System.out.println("Координаты персонажа — (x: " + person.getX()
                    + ", y: " + person.getY() + ")");

            int[] coords = readCoordinates(scanner);
            int x = coords[0];
            int y = coords[1];

            if (!person.isMoveCorrect(x, y)) {
                System.out.println("Некорректный ход. Можно ходить только "
                        + "вверх/вниз/влево/вправо на 1 клетку.");
                continue;
            }

            String cell = board[y - 1][x - 1];

            if (cell.equals(EMPTY)) {
                board[person.getY() - 1][person.getX() - 1] = EMPTY;
                person.move(x, y);
                step++;
                System.out.println("Вы шагнули в клетку (" + x + ", " + y
                        + "). Ход номер: " + step);

            } else if (cell.equals(KEY)) {
                board[person.getY() - 1][person.getX() - 1] = EMPTY;
                board[y - 1][x - 1] = EMPTY;
                person.move(x, y);
                person.pickUpKey();
                step++;
                System.out.println("🔑 Вы нашли ключ! Теперь замок открыт. Ход номер: " + step);

            } else if (cell.equals(CASTLE)) {
                if (person.hasKey()) {
                    board[person.getY() - 1][person.getX() - 1] = EMPTY;
                    person.move(x, y);
                    board[y - 1][x - 1] = person.getImage();
                    outputBoard(board, person.getLive(), person.hasKey());
                    System.out.println("🎉 Вы прошли игру!");
                    won = true;
                    break;
                } else {
                    System.out.println("🔒 Замок заперт. Сначала найдите ключ!");
                }

            } else {
                // на клетке монстр
                Monster targetMonster = null;
                for (Monster m : monsters) {
                    if (m.conflictPerson(x, y)) {
                        targetMonster = m;
                        break;
                    }
                }
                if (targetMonster == null) {
                    System.out.println("На этой клетке никого нет. Странно...");
                    continue;
                }

                boolean defeated = targetMonster.taskMonster(difficulty, scanner);
                if (defeated) {
                    monsters.remove(targetMonster);
                    board[person.getY() - 1][person.getX() - 1] = EMPTY;
                    board[y - 1][x - 1] = EMPTY;
                    person.move(x, y);
                    step++;
                } else {
                    person.downLive();
                    System.out.println("Осталось жизней: " + person.getLive());
                }
            }

            if (person.getLive() <= 0) {
                System.out.println("💀 Закончились жизни. Игра окончена.");
                break;
            }
        }

        if (!won && person.getLive() > 0) {
            System.out.println("Игра завершена.");
        }
    }

    // ---- Утилиты ----

    static Monster spawnRandomMonster(Random random, int x, int y) {
        int roll = random.nextInt(100);
        if (roll < 40) return new Monster(x, y);        // 🧟 — 40%
        if (roll < 70) return new BigMonster(x, y);     // 👹 — 30%
        return new LanguageMonster(x, y);               // 🦉 — 30%
    }

    static void printLegend() {
        System.out.println("=== Легенда ===");
        System.out.println("🧙 — ваш персонаж");
        System.out.println("🧟 👹 — монстры-математики (👹 сильнее: задача на 1 уровень сложнее)");
        System.out.println("🦉 — сова (вопрос по русскому языку, ответ буквой A/B/C/D)");
        System.out.println("🔑 — ключ от замка");
        System.out.println("🏰 — замок (нужен ключ, чтобы выйти)");
        System.out.println("Пустая клетка — можно свободно ходить");
        System.out.println("===============\n");
    }

    static void outputBoard(String[][] board, int live, boolean hasKey) {
        int size = board.length;
        StringBuilder wall = new StringBuilder();
        for (int i = 0; i < size; i++) wall.append("+ —— ");
        wall.append("+");
        String wallStr = wall.toString();

        for (String[] row : board) {
            System.out.println(wallStr);
            for (String col : row) {
                System.out.print("| " + col + " ");
            }
            System.out.println("|");
        }
        System.out.println(wallStr);
        System.out.println("Количество жизней:\t" + live
                + " | Ключ: " + (hasKey ? "есть 🔑" : "нет") + "\n");
    }

    static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Введите число от " + min + " до " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Это не число. Попробуйте ещё раз.");
            }
        }
    }

    static int[] readCoordinates(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Введите два числа через пробел (x y).");
                continue;
            }
            try {
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                return new int[]{x, y};
            } catch (NumberFormatException e) {
                System.out.println("Оба значения должны быть числами.");
            }
        }
    }
}
