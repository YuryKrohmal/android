package yura.android.elements.AI;

public class Ai {

    public static class Request{
        public final double[] input;

        public Request (String fromString){
            char[] str = fromString.toCharArray();
            double[] res = new double[str.length];

            for (int i=0; i < res.length; i++) {
                res[i] = (double) str[i];
            }
            input = res;
        }
    }


}
