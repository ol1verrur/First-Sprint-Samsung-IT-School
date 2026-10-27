import java.util.Random;

public class LanguageQuestion {
    private final String question;
    private final String correctAnswer;
    private final String[] wrongAnswers;
    private final int difficulty;

    public LanguageQuestion(String question, String correctAnswer,
                            String[] wrongAnswers, int difficulty) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.wrongAnswers = wrongAnswers;
        this.difficulty = difficulty;
    }

    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
    public int getDifficulty() { return difficulty; }

    /** Возвращает массив из 4 вариантов, где верный — на случайной позиции. */
    public String[] shuffleOptions(Random r) {
        String[] opts = new String[4];
        opts[0] = correctAnswer;
        System.arraycopy(wrongAnswers, 0, opts, 1, 3);
        for (int i = opts.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            String tmp = opts[i]; opts[i] = opts[j]; opts[j] = tmp;
        }
        return opts;
    }
}