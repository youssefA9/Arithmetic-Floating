import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class BinaryArithmetic {
    public static void main(String args[]) {
        String text = "";
        double floatingCode = 0.0;
        int length = 0;
        Vector<String> Char = new Vector<String>();
        Vector<Double> Charp = new Vector<Double>();
        Scanner sc = new Scanner(System.in);

        text = sc.nextLine();
        Char = insertion(Char, text);

        // for (int i = 0; i < Char.size(); i++) {
        // System.out.println("Enter the Probability of Character (" + Char.get(i) + ") : ");
        //  Charp.add(sc.nextDouble());
        //  }
        Charp.add(0.8);
        Charp.add(0.02);
        Charp.add(0.18);

        System.out.println(tobinary(0.4, 6));
        String m = Arithmetic_binaryEncoder(text, Char, Charp);

        floatingCode = Arithmetic_floatingEncoder(text, Char, Charp);

        System.out.println("The Floating Code: " + floatingCode);
        System.out.println("The binary Code: " + m);

        System.out.println("Enter the length of the text : ");
        length = sc.nextInt();

        text = Arithmetic_floatingDecoder(floatingCode, length, Char, Charp);
        String text2 = Arithmetic_binaryDecoder(m, length, Char, Charp);

        System.out.println("The Decompressed Text: " + text);
        System.out.println("The Decompressed Text: " + text2);

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

    public static String Arithmetic_binaryEncoder(String text, Vector<String> Char, Vector<Double> CharPerc) {
        double range = 1.0;
        double upper = 0.0, lower = 0.0;
        int i = 0;
        String output = "";

        do {
            upper = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))));
            lower = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))) - 1);
            while ((lower < 0.5 && upper < 0.5) || (lower > 0.5 && upper > 0.5)) {
                if (lower < 0.5 && upper < 0.5) {
                    output += "0";
                    lower *= 2;
                    upper *= 2;
                } else if (lower > 0.5 && upper > 0.5) {
                    output += "1";
                    lower = (lower - 0.5) * 2;
                    upper = (upper - 0.5) * 2;
                }
            }
            range = upper - lower;
            i++;
        } while (i < text.length());
        double number = (Math.random() * (upper - lower)) + lower;
        while ((int) (number * 10) % 2 != 1) {
            number = (Math.random() * (upper - lower)) + lower;

        }
        System.out.println("bitch");
        System.out.println(number);

        String result = tobinary(number, getK(CharPerc));
        return output + result;
    }

    public static int getK(Vector<Double> m) {
        double min = m.get(0);
        for (int i = 0; i < m.size(); i++) {
            if (m.get(i) < min) {
                min = m.get(i);
            }
        }
        int k = 0;
        while (min < 1) {
            min *= 2;
            k++;
        }
        return k;
    }

    public static String tobinary(double n, int k) {
        int i;
        double number;
        StringBuilder s = new StringBuilder();
        while (k != 0) {
            i = (int) (n * 2);
            s.append(i);
            number = n * 2;
            n = number - i;
            k--;
        }
        return s.toString();
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
        double floatTemp = floatCode;
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
                lower = vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)) - 1);
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

    public static String Arithmetic_binaryDecoder(String binarycode, int EOF, Vector<String> Char, Vector<Double> CharPerc) {
        String text = "";
        double range = 1.0;
        double upper = 0.0, lower = 0.0;
        double temp = 0.0;
        int j = 0;
        int k = getK(CharPerc);
        String sub = binarycode.substring(0, k);
        double floatTemp = (Integer.parseInt(sub, 2)) / Math.pow(2, 6);
        boolean flag = false;
        int shift = 0;
        do {
            for (int i = 0; i < Char.size(); i++) {
                if ((i == 0 ? 0 : vectorDoubSum(CharPerc, i - 1)) <= floatTemp
                        && floatTemp <= vectorDoubSum(CharPerc, i)) {
                    text += Char.get(i);
                }
            }

            if (j == 0) {
                upper = vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)));
                lower = vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)) - 1);
            } else {
                temp = upper;
                upper = lower + (upper - lower) * vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)));
                lower = lower + (temp - lower) * vectorDoubSum(CharPerc, Char.indexOf(text.substring(text.length() - 1)) - 1);
                while ((lower < 0.5 && upper < 0.5) || (lower > 0.5 && upper > 0.5)) {
                    flag = true;
                    if (lower < 0.5 && upper < 0.5) {
                        shift++;
                        lower *= 2;
                        upper *= 2;
                    } else if (lower > 0.5 && upper > 0.5) {
                        shift++;
                        lower = (lower - 0.5) * 2;
                        upper = (upper - 0.5) * 2;
                    }
                }
            }

            range = upper - lower;
            j++;
            System.out.println(shift);
            if (flag) {
                sub = binarycode.substring(shift, k + shift);
                System.out.println(sub);
                floatTemp = (((Integer.parseInt(sub, 2)) / Math.pow(2, 6)) - lower) / range;
                flag = false;
            } else {
                floatTemp = (floatTemp - lower) / range;
            }

        } while (j != EOF);

        return text;
    }
}