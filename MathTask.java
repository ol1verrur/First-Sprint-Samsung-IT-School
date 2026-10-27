import java.util.Random;

public class MathTask {
    private final String question;
    private final int answer;

    public MathTask(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public int getAnswer() { return answer; }

    public static MathTask generate(int difficulty) {
        Random rnd = new Random();
        switch (difficulty) {
            case 1: { // лёгкое сложение в пределах 20
                int a = rnd.nextInt(10) + 1;
                int b = rnd.nextInt(10) + 1;
                return new MathTask(a + " + " + b + " = ?", a + b);
            }
            case 2: { // сложение в пределах 100
                int a = rnd.nextInt(50) + 10;
                int b = rnd.nextInt(50) + 10;
                return new MathTask(a + " + " + b + " = ?", a + b);
            }
            case 3: { // вычитание
                int a = rnd.nextInt(90) + 10;
                int b = rnd.nextInt(a - 1) + 1;
                return new MathTask(a + " - " + b + " = ?", a - b);
            }
            case 4: { // умножение
                int a = rnd.nextInt(9) + 2;
                int b = rnd.nextInt(9) + 2;
                return new MathTask(a + " * " + b + " = ?", a * b);
            }
            case 5: { // умножение с вычитанием
                int a = rnd.nextInt(9) + 2;
                int b = rnd.nextInt(9) + 2;
                int product = a * b;
                int c = rnd.nextInt(product - 1) + 1;
                return new MathTask(a + " * " + b + " - " + c + " = ?", product - c);
            }
            default:
                return generate(1);
        }
    }
}