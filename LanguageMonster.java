import java.util.Random;
import java.util.Scanner;

public class LanguageMonster extends Monster {

    public LanguageMonster(int x, int y) {
        super(x, y);
        setImage("\uD83E\uDD89"); // 🦉 — сова, символ мудрости
    }

    @Override
    public boolean taskMonster(int difficulty, Scanner scanner) {
        Random r = new Random();
        LanguageQuestion q = LanguageQuestionBank.randomForDifficulty(difficulty, r);
        String[] options = q.shuffleOptions(r);
        return askMultipleChoice(
                scanner,
                "🦉 Мудрая сова задаёт вопрос:",
                q.getQuestion(),
                options,
                q.getCorrectAnswer()
        );
    }
}