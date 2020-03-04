package yura.android.elements.word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Language extends Space, Space.Word {

    public static class En implements Language {

        public static class Rules {

            public Rules(){
                super();
            }

            public ArrayList<Space.Word> split(String sentence) {
                if (sentence == null) return new ArrayList<Space.Word>();

                List<String> word_parts = Arrays.asList(sentence.toLowerCase().split("[ ,. ?]+"));

                ArrayList<Space.Word> res = new ArrayList<Space.Word>(word_parts.size());
                for (String s: word_parts) {
                    Space.Word unknownWord = Space.Word.Builder.create(s);
                    res.add(unknownWord);
                }
                return res;
            }
        }



        @Override
        public String word() {
            return "en";
        }



        public Space.Word pluralToSingle(Space.Word word) {
            return null;
        }




    }
}
