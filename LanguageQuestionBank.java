import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LanguageQuestionBank {

    private static final List<LanguageQuestion> QUESTIONS = new ArrayList<>();

    static {
        // ---------- Уровень 1 ----------
        QUESTIONS.add(new LanguageQuestion(
                "Сколько букв в русском алфавите?",
                "33", new String[]{"32", "34", "31"}, 1));
        QUESTIONS.add(new LanguageQuestion(
                "Какое слово обозначает предмет?",
                "стол", new String[]{"бежать", "красный", "быстро"}, 1));
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове 4 буквы?",
                "мама", new String[]{"кот", "дом", "сын"}, 1));

        // ---------- Уровень 2 ----------
        QUESTIONS.add(new LanguageQuestion(
                "Какое слово — глагол?",
                "писать", new String[]{"письмо", "письменный", "письменно"}, 2));
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове пишется «и» после «ц»?",
                "цифра", new String[]{"цыплёнок", "цыган", "огурцы"}, 2));
        QUESTIONS.add(new LanguageQuestion(
                "Какое слово пишется через дефис?",
                "кто-то", new String[]{"паровоз", "никто", "водолаз"}, 2));
        QUESTIONS.add(new LanguageQuestion(
                "Сколько слогов в слове «молоко»?",
                "3", new String[]{"2", "4", "1"}, 2));

        // ---------- Уровень 3 ----------
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове есть приставка?",
                "заехать", new String[]{"заяц", "зима", "зелень"}, 3));
        QUESTIONS.add(new LanguageQuestion(
                "Сколько звуков в слове «ёлка»?",
                "5", new String[]{"4", "6", "3"}, 3));
        QUESTIONS.add(new LanguageQuestion(
                "Какой падеж у слова «книге» в предложении «Я рад книге»?",
                "дательный", new String[]{"предложный", "родительный", "винительный"}, 3));

        // ---------- Уровень 4 ----------
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове пишется «нн»?",
                "оловянный", new String[]{"серебряный", "ветреный", "кожаный"}, 4));
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове пишется приставка ПРЕ-?",
                "преграда", new String[]{"приехать", "пришить", "присесть"}, 4));
        QUESTIONS.add(new LanguageQuestion(
                "Укажи слово с ударением на второй слог",
                "звонит", new String[]{"звонит (на первый)", "понял", "банты"}, 4));

        // ---------- Уровень 5 ----------
        QUESTIONS.add(new LanguageQuestion(
                "В каком слове пишется «ъ»?",
                "съел", new String[]{"семья", "вьюга", "пять"}, 5));
        QUESTIONS.add(new LanguageQuestion(
                "Сколько грамматических основ в предложении "
                        + "«Солнце село, и стало темно»?",
                "2", new String[]{"1", "3", "4"}, 5));
        QUESTIONS.add(new LanguageQuestion(
                "В каком предложении есть вводное слово?",
                "К счастью, дождь прекратился.",
                new String[]{
                        "Дождь прекратился к вечеру.",
                        "Он сказал, что дождь прекратился.",
                        "Когда дождь прекратился, мы вышли."
                }, 5));
    }

    public static LanguageQuestion randomForDifficulty(int difficulty, Random r) {
        List<LanguageQuestion> pool = new ArrayList<>();
        for (LanguageQuestion q : QUESTIONS) {
            if (q.getDifficulty() == difficulty) pool.add(q);
        }
        if (pool.isEmpty()) pool = QUESTIONS;
        return pool.get(r.nextInt(pool.size()));
    }
}