package yura.android.elements.word;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class PlaygroundWordTest {

    //// Порядок слов в предложении (Word order Sentence):

    // существительное (subject): кто? что? I am reading a book -  я читаю книгу
    // глагол (verb): кто, что? деалет? read - читать
    // вспомогательный глагол (): например чтобы сделать отрицательное предложение  I am don't reading a book - я не читаю книгу
    // обстоятельство перед глаголом: I should reading a book - я должен читать книгу, I should don't reading a book  - я не должен читать книгу
    // дополнение, слова после сказуемого

    // Вопросительное предложение: (present continues) Is he reading? - он читает?
    // Вопросительные слова в вопросительном предложении ставятся в начало: (present continues) What Is he reading? - что он читает?
    // Вопросительные слова в вопросительном предложении ставятся в начало: (present indefinitif) What Is he readed? - что он прочитал?

    // Повелительное предложение (иди сделай чтото): Go read - иди читай
    // Повелительное предложение, отрицание (иди сделай чтото): Don't go read - не иди читать


    // !!! Простое предложение состоит только из подлежащего и сказуемого:  (present continues) I am reading a book
    // !!! 1. Сложное предложение состоит из нескольких простых несвязанных между собой: (present continues) I am reading a book so James went out - я читаю книгу поэтому Джеймс вышел
    // !!! 2. Сложное предложение состоит из нескольких простых связанных между собой так что их нельзя использовать отдельно: I am reading a book because James should went out


    // I work in a big bank - я
    // You work in a big bank - вы, ты
    // We work in a big bank - мы
    // They work in a big bank - они


    private Language.En en = new Language.En();

    private Space.Sentence.Builder.Creator<String> splitStringToWords = (String from) -> {
        Language.En.Rules rules = new Language.En.Rules();
        ArrayList<Space.Word> words = rules.split(from);
        return words;
    };

    @Test
    public void sentence_Base() {
        String[] base = {
                "be", // я есть
                // am, is, are настоящее время
                // was, were - прошлое
                // will - будующее
                "must", "can", "should",

        };
    }

    @Test
    public void createSentence_Subject_Helper_Verb() {

        // помогают задать время
        {
            String[] helpers = {
                    "to be",
                    // am, is, are настоящее время
                    // was, were - прошлое
                    // will - будующее
                    "must", "can", "should",

            };
        }

        // помогают задавть время
        {
            String text = "I work in a bank"; // я работаю в банке
            String text1 = "I worked in a bank"; // я работал в банке
            String text2 = "I will work in a bank"; // я буду работать в банке
        }



        // чтото происходит в конкретный момент: сейчас, прошлом, будующем
        {
            String text = "I am working now"; // я работаю сейчас
            String text1 = "I was working"; // я работал
            String text2 = "I will working "; // я буду работать
        }

        // чтото завершено
        {
            String text = "I have worked"; // я поработал дополнение:<тогдато>
        }

        // чтото будет завершено в какойто временной интервал
        {
            String text = "I have been working"; // я поработаю дополнение:<тогдато>
        }

        // выразить отношение к чему либо: must, can, should
        {
            String[] text = {
                    "I can do it", // я могу сделать это
                    "I must do it", // я должен сделать это
                    "I should do it" // мне следует сделать это
            };

            String[] question_negative = {
                    "I can do it", // я могу сделать это
                    "I can't do it", // я не могу сделать это
                    "Can I do it" // могу я сделать это?
            };

            // с условием
            {
                String[] cindition = {
                        "I will do it if the weather was fine", // я бы сделал это если бы погода была хорошей
                };
            }

            // предложение в котором нет действия обязательно дополняется глаголом to be
            {
                String[] condition = {
                        "I write letter", // я пишу писбмо, есть глагол действия
                        "The letter is writen", // письмо <есть> написанно, нет глагола действия
                        "I am happy", // я <есть> счастлив, нет глагола действия
                        "I am busy", // я <есть> занят, нет глагола действия
                };
            }
        }
    }



    private Map<String, Space.Word> createDictionary(Space.Word word) {
        Map<String, Space.Word> map = new HashMap<String, Space.Word>();

        // местоимение
        {
            String[] who_what = {"i","he","she","it","we","you","they"}; // кто/who? что/what?
            String[] whom_than = {"me","him","her","it","us","you","them"}; // кому/to whom? кого/whom? чем/than? что/what?
            String[] whose = {"my","his","her","its","our","your","their"}; // чей/whose?

            for (String currPronoun : who_what) {
                map.put(currPronoun, Space.Sentence.Pronouns.Builder.create(currPronoun));
            }
            for (String currPronoun : whom_than) {
                map.put(currPronoun, Space.Sentence.Pronouns.Builder.create(currPronoun));
            }
            for (String currPronoun : whose) {
                map.put(currPronoun, Space.Sentence.Pronouns.Builder.create(currPronoun));
            }

        }

        // подлежащее
        {

            {
                String[] article = {
                        "a",
                        "an",
                };
                for (String currPronoun : article) {
                    map.put(currPronoun, Space.Sentence.Noun.Article.Builder.create(currPronoun));
                }
            }

            //Space.Word single_form = pluralToSingle(word);

            String[] noun = {
                    "pen",
                    "pencil",
                    "pianist",
                    "worker",
                    "book",
                    "country",
                    "kiev",
            };

            for (String currPronoun : noun) {
                map.put(currPronoun, Space.Sentence.Noun.Builder.create(currPronoun));
            }
        }

        // сказуемое
        {
            String[] verb = {
                    "play",
                    "swim",
                    "like"
            };
            for (String currVerb : verb) {
                map.put(currVerb, Space.Sentence.Verb.Builder.create(currVerb));
            }
        }

        //прилагательное
        {
            String[] verb = {"broken",
                    "good"};
            for (String currVerb : verb) {
                map.put(currVerb, Space.Sentence.Adjectives.Builder.create(currVerb));
            }
        }

        // хелперы
        {
            // Subject + Helper + Verb
            String[] tobe = {"am","is", "are",/* настоящее*/
                    "was", "were",/* прошлое */
                    "will be" /* будующее */};
            String[] helpers = {"must", "can", "should"};

            for (String currHelper : tobe) {
                map.put(currHelper, Space.Sentence.Verb.Builder.create(currHelper));
            }
            for (String currHelper : helpers) {
                map.put(currHelper, Space.Sentence.Verb.Builder.create(currHelper));
            }
        }
        return map;
    }


    @Test
    public void testWord() {
        Space.Word word = Space.Word.Builder.create("anyword");

        assertThat(word, instanceOf(Space.Word.class));
        assertThat(word.word(), equalTo("anyword"));
        assertThat(word.toString(), equalTo("Word:anyword"));
    }

    @Test
    public void testSentence() {
        Space.Sentence sentence = Space.Sentence.Builder.create("My pencil is broken", splitStringToWords);

        assertThat(sentence, instanceOf(Space.Sentence.class));
        assertThat(sentence.word(), equalTo("Sentence"));
        assertThat(sentence.toString(), equalTo("Sentence:my pencil is broken"));

        assertThat(sentence.words().get(0).word(), equalTo("my"));
        assertThat(sentence.words().get(1).word(), equalTo("pencil"));
        assertThat(sentence.words().get(2).word(), equalTo("is"));
        assertThat(sentence.words().get(3).word(), equalTo("broken"));

    }

    @Test
    public void testSentenceSpace() {
        //знак припинания
        Space.Sentence.PunctuationMark punctuationMark;
        // существительные (noun)
        Space.Sentence.Noun noun;
        // местоимения (pronouns)
        Space.Sentence.Pronouns pronouns;
        // глаголы (verbs)
        Space.Sentence.Verb verb;
        // прилагательные (adjectives)
        Space.Sentence.Adjectives adjectives;
        // наречия (adverbs)
        Space.Sentence.Adverbs adverbs;
        // предлоги (prepositions)
        Space.Sentence.Prepositions prepositions;
        // союзы (conjunctions)
        Space.Sentence.Conjunctions conjunctions;
    }

    @Test
    public void englishTest() {
        Language.En en = new Language.En();
        Space.Word word = en;
        Space language = en;
    }

    @Test
    public void Questions() {
        Space.Sentence.Pronouns.Questions.Who who;
        Space.Sentence.Pronouns.Questions.What what;
        Space.Sentence.Pronouns.Questions.To_Whom to_whom;
        Space.Sentence.Pronouns.Questions.By_Whom by_whom;
        Space.Sentence.Pronouns.Questions.Whom whom;
    }

    @Test
    public void Nouns() {
        // существительное кто? что? What is it?  Who is it?
        // действие: мужской, женский род
        // действие: единственное, множественное число
        // действие: исчесляемое, неисчесляемое
        // действие: выбор артикля перед существительным the, a/an, нет артикля

        Language.En.Rules rules = new Language.En.Rules();

        Space.Word noun = Space.Sentence.Noun.Builder.create("pencil");
        assertThat(noun.toString(), equalTo("Noun:pencil"));

        // простое существительное
        {
            Space.Sentence sentence = Space.Sentence.Builder.create("pen", splitStringToWords);

            assertThat(sentence.words().size(), equalTo(1));
            assertThat(sentence.words().get(0).toString(), equalTo("Noun:pen"));
        }

        // производные существительное
        {
            Space.Sentence sentence = Space.Sentence.Builder.create("worker", splitStringToWords);

            assertThat(sentence.words().size(), equalTo(1));
            assertThat(sentence.words().get(0).toString(), equalTo("Noun:worker"));
        }

        // существительное
        {
            // ед число
            {
                Space.Sentence sentence = Space.Sentence.Builder.create("book", splitStringToWords);

                assertThat(sentence.words().size(), equalTo(1));
                assertThat(sentence.words().get(0).toString(), equalTo("Noun:book"));
            }

            // мн число
            {
                Space.Sentence sentence = Space.Sentence.Builder.create("books", splitStringToWords);

                assertThat(sentence.words().size(), equalTo(1));
                assertThat(sentence.words().get(0).toString(), equalTo("Noun:books"));
            }
        }


        // выбор артикля пред существительным
        // исчесляемое существительное в единственном числе -> конкретный предмет? -> да the -> нет a/an
        // исчесляемое существительное в множественном числе -> конкретный предмет? -> да (the) -> нет (без артикля)
        // неисчесляемое существительное -> конкретный предмет? -> да (the) -> нет (без артикля)

        Space.Sentence.Noun.Article article;
        {
            String text = "I need a pen"; // pen - исчесляемое в единственном числе
        }

        {
            String text = "The water in this cup is cold"; // water - неисчесляемое но говорится о конкретной воде в чашке
        }

    }

    @Test
    public void verb() {
        // сказуемое: кто делает? что делает? каков предмет?
        // действие: согласуется с подлежащим


        // простое сказуемое: 1 форма
        {
            String text = "I like music"; // like
        }

        // если в предложении нет сказуемого то обязательно добавляется to be
        {
            String[] to_be = {
                    "am", // i
                    "is", // he, she, it
                    "are", // you, we, they
            };
            String[] text = {
                    "I am beautiful",
                    "She is beautiful",
                    "You are beautiful",
            };

        }

        // составное глагольное сказуемое
        {
            // составное сказуемое: модальное сказуемое (can, ought to, may, ...) + инфинитив
            {
                String text = "She had been writing a letter"; // had been + writing
            }

            // составное сказуемое: сказуемое (начало либо конец действия) + инфинитив
            {
                String text = "It continued snowing"; // had been + writing
            }
        }

        // составное именное сказуемое: to be (в любом времени) + подлежащее/прилагательное/числительное/наречие/причастие/герундий
        {
            // составное подлежащее
            {
                String text = "My dad is a pilot"; //is a pilot - подлежащее
            }

            // составное прилагательное
            {
                String text = "The wall is high"; // high - прилагательное
            }

            // составное наречие
            {
                String text = "The game is over"; // over - наречие
            }

            // составное причастие
            {
                String text = "His heart is broken"; // broken - причастие
            }
        }

    }

    @Test
    public void object() {
        // дополнение кого? что? кому? кем? чем? о чем? ...
        // действие: определяет предмет и лицо на которое направленио воздействие

        // составное причастие
        {
            String text = "I read an article"; // article - дополнение
        }


    }

    @Test
    public void attribute() {
        // определение: который? какой? чей?
        // действие: называет признак предмета уточняя либо дополняя его

        // составное причастие
        {
            String text = "I want to buy that white pen"; // white - определение
        }
    }

    @Test
    public void adverbial() {
        // обстоятельство: указывает на обстоятельство в котором происходит действие (when, where, how, why)

        // составное причастие
        {
            String text = "She’ll meet you at 9 o’clock"; // at 9 o’clock - обстоятельство, когда?
        }
    }

}