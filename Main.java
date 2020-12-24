import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String args[]) {
        String text = "";
        double floatingCode = 0.0;
        Vector<String> Char = new Vector<String>();
        Vector<Double> Charp = new Vector<Double>();
        Scanner sc = new Scanner(System.in);
        text = sc.nextLine();
        Char = insertion(Char, text);

        for (int i = 0; i < Char.size(); i++) {
            System.out.println("Enter the Probability of Character (" + Char.get(i) + ") : ");
            Charp.add(sc.nextDouble());
        }

        floatingCode = Arithmetic_floatingEncoder(text, Char, Charp);
        String m=Arithmetic_binaryEncoder(text,Char,Charp);
        System.out.println(m);

        System.out.println("The Floating Code: " + floatingCode);

        text = Arithmetic_floatingDecoder(floatingCode, 4, Char, Charp);

        System.out.println("The Decompressed Test: " + text);

    }

    public static Vector insertion(Vector<String> Char, String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Char.contains(String.valueOf(text.charAt(i)))) {
                Char.add(String.valueOf(text.charAt(i)));
            }
        }
        Collections.sort(Char);
        return Char;
    }

    public static Double vectorDoubSum(Vector<Double> x, int num) {
        double sum = 0.0;
        for (int i = 0; i <= num; i++) {
            sum += x.get(i);
        }
        return sum;
    }



    public static double Arithmetic_floatingEncoder(String text, Vector<String> Char, Vector<Double> CharPerc) {
        double range = 1.0;
        double upper = 0.0, lower = 0.0;
        int i = 0;

        do {
            upper = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))));
            lower = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))) - 1);
            range = upper - lower;
            i++;
        } while (i < text.length());

        return (Math.random() * (upper - lower)) + lower;
    }

    public static String Arithmetic_floatingDecoder(double floatCode, int EOF, Vector<String> Char, Vector<Double> CharPerc) {
        String text = "";
        double range = 1.0;
        double upper = 0.0, lower = 0.0;
        double temp = 0.0;
        double floatTemp = 0.0;
        int j = 0;

        do {

            for (int i = 0; i < Char.size(); i++) {
                if ((i == 0 ? 0 : vectorDoubSum(CharPerc, i - 1)) <= floatTemp
                        && floatTemp <= vectorDoubSum(CharPerc, i)) {
                    text += Char.get(i);
                }
            }

            if (j == 0) {
                upper = vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)));
                lower = 0.0;
            } else {
                temp = upper;

                upper = lower + (upper - lower) * vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)));
                lower = lower + (temp - lower) * vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)) - 1);
            }

            range = upper - lower;
            floatTemp = (floatCode - lower) / range;
            j++;

        } while (j != EOF);

        return text;
    }
}