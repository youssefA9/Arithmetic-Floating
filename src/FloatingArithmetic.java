import java.io.File;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

public class FloatingArithmetic {
    public static void main(String args[]) {
        String text = "";
        double floatingCode = 0.0;
        long length = 0;
        String Path = "";
        File fx;
        Vector<String> Char = new Vector<String>();
        Vector<Double> Charp = new Vector<Double>();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the Path: ");
        Path = sc.nextLine();

        fx = new File(Path);
        Char = insertion(Char, fx);

        fx = new File(Path);
        Charp = calcProb(Char, fx);

        fx = new File(Path);
        floatingCode = Arithmetic_floatingEncoder(fx, Char, Charp);

        System.out.println("The Floating Code: " + floatingCode);
        System.out.println();

        length = fx.length();

        createCompFile(Char, Charp, length, floatingCode, Path);

        text = Arithmetic_floatingDecoder(floatingCode, length, Char, Charp);

        System.out.println("The Decompressed Test: " + text);

    }

    public static Vector insertion(Vector<String> Char, File fx) {
        try {
            Scanner sc = new Scanner(fx);
            String temp = "";

            while (sc.hasNextLine()) {
                temp = sc.nextLine();
                for (int i = 0; i < temp.length(); i++) {
                    if (Char.contains(String.valueOf(temp.charAt(i)))) {
                    } else {
                        Char.add(String.valueOf(temp.charAt(i)));
                    }
                }
            }
            Collections.sort(Char);
        } catch (Exception e) {
        }
        return Char;
    }

    public static Vector calcProb(Vector<String> Char, File fx) {
        Vector<Double> Charp = new Vector<Double>();

        for (int i = 0; i < Char.size(); i++) {
            Charp.add(0.0);
        }

        try {

            Scanner sc = new Scanner(fx);
            String temp;
            double Counter = 0;
            int length = 0;

            while (sc.hasNextLine()) {
                temp = sc.nextLine();
                for (int i = 0; i < temp.length(); i++) {
                    // D:\Collage\text.txt

                    Counter = Charp.get(Char.indexOf(String.valueOf(temp.charAt(i))));
                    Counter++;
                    Charp.set(Char.indexOf(String.valueOf(temp.charAt(i))), Counter);

                    length++;
                }
            }

            for (int i = 0; i < Charp.size(); i++) {
                Charp.set(i, Charp.get(i) / length);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return Charp;
    }

    public static Double vectorDoubSum(Vector<Double> x, int num) {
        double sum = 0.0;
        for (int i = 0; i <= num; i++) {
            sum += x.get(i);
        }
        return sum;
    }

    public static double Arithmetic_floatingEncoder(File fx, Vector<String> Char, Vector<Double> CharPerc) {
        double range = 1.0;
        double upper = 0.0, lower = 0.0;
        int i = 0;
        String text = "";
        try {
            Scanner sc = new Scanner(fx);
            while (sc.hasNextLine()) {
                text = sc.nextLine();
                do {
                    upper = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))));
                    lower = lower + range * vectorDoubSum(CharPerc, Char.indexOf(String.valueOf(text.charAt(i))) - 1);
                    range = upper - lower;
                    i++;
                } while (i < text.length());
            }

        } catch (Exception e) {
            System.out.println("bitch here");
        }

        return (Math.random() * (upper - lower)) + lower;
    }

    public static String Arithmetic_floatingDecoder(double floatCode, long EOF, Vector<String> Char, Vector<Double> CharPerc) {
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

    public static void createCompFile(Vector<String> Char, Vector<Double> Charp, long length, double code, String Path) {
        Path = preparePath(Path) + "CompressedFile.txt";
        try {
            FileWriter file = new FileWriter(Path);
            for (int i = 0; i < Char.size(); i++) {
                file.write(Char.get(i) + " " + Charp.get(i) + "\n");
            }
            file.write(length + "\n");
            file.write(code + "");
            file.close();

        } catch (Exception e) {
        }
    }

    public static void readCompFile

    public static String preparePath(String Path) {
        String[] temp = Path.split(Pattern.quote("\\"));
        Path = "";
        for (int i = 0; i < temp.length - 1; i++) {
            Path += temp[i] + "\\";
        }
        return Path;
    }


}