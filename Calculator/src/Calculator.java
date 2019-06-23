import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator
{

    public static void main(String args[] )throws IOException {

        String inp = "";
        String in = "";


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine().trim();

        String arr[] = input.split("\\W");

        boolean check1 = isInteger(arr[0]);
        boolean check2 = isInteger(arr[1]);

        if(check1!=check2) {
            System.out.println("Введены данные разного вида. Выполение программы будет остановлено");
            System.exit(1);
        }

        if(arr[0].length()>arr[1].length())
        {
            inp = input.replace(arr[0],"");
            in = inp.replace(arr[1],"");
        } else
        {
            inp = input.replace(arr[1],"");
            in =  inp.replace(arr[0],"");
        }

        char op = in.charAt(0);


        int arr1[] = new int[input.length()-1];

        Pattern pattern = Pattern.compile("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

        for(int i = 0; i<arr.length;i++)
        {
            Matcher matcher = pattern.matcher(arr[i]);
            boolean valid = matcher.find();
            if (valid)
            {
                arr1[i] = romanToArabic(arr[i]);
            }
            else
            {
                arr1[i] = Integer.parseInt(arr[i]);
            }
        }

        int first = arr1[0];
        int last = arr1[1];

        int result = calc(op, first, last);

        if(!check1)
        {
            String result1 = arabicToRoman(result);
            System.out.println(result1);
        } else
            {
            System.out.println(result);
            }
    }

    static int calc(char op, int first, int last)
        {
            int result = 0;
            switch (op) {
                case '+':
                    result = (int) first + last;
                    return result;

                case '-':
                    result = first - last;
                    return result;

                case '/':
                    result = (int) first / last;
                    return result;

                case '*':
                    result = first * last;
                    return result;
            }
            return result;

    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }
        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

     static boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }


}
