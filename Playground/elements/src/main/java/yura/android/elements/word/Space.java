package yura.android.elements.word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Space {

    public static class Builder {

        public static class SpaceImpl implements Space, Word {
            private final String word;

            public SpaceImpl(String word){
                this.word = word;
            }

            @Override
            public String toString() {
                return Space.class.getSimpleName() + ":"+ word;
            }

            @Override
            public String word() {
                return word;
            }
        }

        static Space.Word create(String word){
            return new SpaceImpl(word);
        }
    }

    public static interface Word {
        String word();

        public static class Builder {

            static Space.Word create(String word){

                class WordImpl implements Word {
                    private final String word;

                    public WordImpl(String word){
                        this.word = word;
                    }

                    @Override
                    public String word() {
                        return word;
                    }

                    @Override
                    public String toString() {
                        return Word.class.getSimpleName() + ":"+ word;
                    }
                }

                return new WordImpl(word);
            }
        }
    }

    public static interface Question extends Word {
        String word();
        Language language();

        public static class Builder {

            static Space.Question create(String word, Language language){

                class QuestionImpl implements Question {
                    private final String word;
                    private final Language language;

                    public QuestionImpl(String word, Language language){
                        this.word = word;
                        this.language = language;
                    }

                    @Override
                    public Language language() {
                        return language;
                    }

                    @Override
                    public String word() {
                        return word;
                    }

                    @Override
                    public String toString() {
                        return Question.class.getSimpleName() + ":" + word;
                    }
                }

                return new QuestionImpl(word, language);
            }
        }
    }

    // Предложение — это высказывание, выражающее законченную мысль
    public static interface Sentence extends Word {

        public ArrayList<Space.Word> words();

        // сущестивтельное указывает на когото/чтото и отвечает на вопрос кто?/что?
        public static interface Noun extends Word {

            public static interface Questions {
                public static interface What_is_it extends Word {
                } // кто?

                public static interface Who_is_it extends Word {
                } // что?
            }


            // артикль
            public static interface Article extends Word {
                public static class Builder {
                    public static Article create(String word) {
                        class ArticleImpl implements Article {
                            private final String word;

                            public ArticleImpl(String word){
                                this.word = word;
                            }

                            @Override
                            public String word() {
                                return word;
                            }
                            @Override
                            public String toString() {
                                return Noun.class.getSimpleName() + "." + Article.class.getSimpleName() + ":"+ word;
                            }
                        }
                        return new ArticleImpl(word);
                    }
                }
            }

//            // исчесляемое
//            public static interface Countable extends Noun {
//                // ед число
//                public static interface Singular extends Noun {
//                    public static class Builder {
//                        public static Singular create(String word, Language language) {
//                            class SingularImpl implements Singular {
//                                private final String word;
//                                private final Language language;
//
//                                public SingularImpl(String word, Language language){
//                                    this.word = word;
//                                    this.language = language;
//                                }
//
//                                @Override
//                                public Language language() {
//                                    return language;
//                                }
//
//                                @Override
//                                public String word() {
//                                    return word;
//                                }
//                                @Override
//                                public String toString() {
//                                    return Noun.class.getSimpleName() + "." + Singular.class.getSimpleName() + ":"+ word;
//                                }
//                            }
//                            return new SingularImpl(word, language);
//                        }
//                    }
//                }
//                // мн число
//                public static interface Plural extends Noun {
//                    public static class Builder {
//                        public static Plural create(String word, Language language) {
//                            class PluralImpl implements Plural {
//                                private final String word;
//                                private final Language language;
//
//                                public PluralImpl(String word, Language language){
//                                    this.word = word;
//                                    this.language = language;
//                                }
//
//                                @Override
//                                public Language language() {
//                                    return language;
//                                }
//
//                                @Override
//                                public String word() {
//                                    return word;
//                                }
//                                @Override
//                                public String toString() {
//                                    return Noun.class.getSimpleName() + "." + Plural.class.getSimpleName() + ":"+ word;
//                                }
//                            }
//                            return new PluralImpl(word, language);
//                        }
//                    }
//                }
//            }

            // неисчесляемое
            public static interface Uncountable extends Noun {
            }

            public static class Builder {

                static class NounImpl implements Noun {
                    private final String word;

                    public NounImpl(String word){
                        this.word = word;
                    }

                    @Override
                    public String word() {
                        return word;
                    }

                    @Override
                    public String toString() {
                        return Noun.class.getSimpleName() + ":"+ word;
                    }
                }

                public static Noun create(String word) {

                    return new NounImpl(word);
                }

                public static Map<String, Word> create(String[] words) {
                    HashMap<String, Word> dic = new HashMap<String, Word>();
                    for (String currPronoun : words) {
                        dic.put(currPronoun, create(currPronoun));
                    }
                    return dic;
                }
            }
        }


        // местоимение указывает: одушевленное/неодушевленное, ед/мн число
        public static interface Pronouns extends Word {

            public static class Builder {

                private static class PronounsImpl implements Pronouns {
                    private final String word;

                    public PronounsImpl(String word){
                        this.word = word;
                    }

                    @Override
                    public String word() {
                        return word;
                    }
                    @Override
                    public String toString() {
                        return Pronouns.class.getSimpleName() + ":"+ word;
                    }
                }

                public static Pronouns create(String word) {
                    return new PronounsImpl(word);
                }

                public static Map<String, Word> create(String[] words) {
                    HashMap<String, Word> dic = new HashMap<String, Word>();
                    for (String currPronoun : words) {
                        dic.put(currPronoun, create(currPronoun));
                    }
                    return dic;
                }
            }

            public static interface Questions {
                public static interface Who extends Question {
                } // кто?

                public static interface What extends Question {
                } // что?

                public static interface To_Whom extends Question {
                } // кому?

                public static interface Whom extends Question {
                } // кем?

                public static interface By_Whom extends Question {
                } // чем?
            }
        }

        // глагол
        public static interface Verb extends Word {
            public static class Builder {
                public static Verb create(String word) {
                    class VerbImpl implements Verb {
                        private final String word;

                        public VerbImpl(String word){
                            this.word = word;
                        }

                        @Override
                        public String word() {
                            return word;
                        }
                        @Override
                        public String toString() {
                            return Verb.class.getSimpleName() + ":" + word;
                        }
                    }
                    return new VerbImpl(word);
                }
            }
        }

        // прилагательное
        public interface Adjectives extends Word {
            public static class Builder {
                public static Adjectives create(String word) {
                    class AdjectivesImpl implements Adjectives {
                        private final String word;

                        public AdjectivesImpl(String word){
                            this.word = word;
                        }

                        @Override
                        public String word() {
                            return word;
                        }
                        @Override
                        public String toString() {
                            return Adjectives.class.getSimpleName() + ":"+ word;
                        }
                    }
                    return new AdjectivesImpl(word);
                }
            }
        }

        // наречие
        public interface Adverbs extends Word {
        }

        // предлоги
        public interface Prepositions extends Word {
        }

        // союз
        public interface Conjunctions extends Word {
        }

        // знак припинания
        public interface PunctuationMark extends Word {

        }


        public static class Builder {

            static class SentenceImpl implements Space.Sentence {

                private ArrayList<Word> words;

                public SentenceImpl(ArrayList<Space.Word> words) {
                    this.words = words;
                }

                @Override
                public String word() {
                    return Space.Sentence.class.getSimpleName();
                }

                @Override
                public ArrayList<Word> words() {
                    return words;
                }

                @Override
                public String toString() {
                    StringBuilder text = new StringBuilder();
                    for (Word word:words) {
                        if (text.length() > 0) {text.append(" ");}
                        text.append(word.word());
                    }
                    return Space.Sentence.class.getSimpleName() +
                            ":" + text;
                }
            }

            public static Space.Sentence create(ArrayList<Space.Word> words) {
                Space.Sentence res = new SentenceImpl(words);
                return res;
            }

            public static <T> Space.Sentence create(T from, Creator<T> creator) {
                Space.Sentence res = new SentenceImpl( creator.from(from) );
                return res;
            }

            public interface Creator<T> {
                ArrayList<Space.Word> from(T from);
            }
        }

        public interface Operation {

            public interface Transform<From, To> {
                To from(From from);
            }
        }
    }


    //алгоритм (набор операций над данными и получение выходного результата)
    //      вых.данные = алгоритм(вх.ланные)

    // класс свойства операция
}

