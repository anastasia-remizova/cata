import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Назови мне свой пример и я его решу ");

        String input = scanner.nextLine();
        String result;
        try {
            result = calc(input);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    static String calc(String input) {
        // Remove any spaces and ensure there are exactly two parts separated by an operator
        String[] parts = input.trim().split("\\s*(?=[+\\-*/])|(?<=[+\\-*/])\\s*");
        if (parts.length != 3)
            throw new IllegalArgumentException("Упс Ошибка");

        String first = parts[0];
        String operator = parts[1];
        String second = parts[2];

        if (isRoman(first) && isArabic(second) || isArabic(first) && isRoman(second))
            throw new IllegalArgumentException("Нельзя Римлян С арабами смешивать");

        int a = parseNumber(first);
        int b = parseNumber(second);

        if (a < 1 || a > 10 || b < 1 || b > 10)
            throw new IllegalArgumentException("Число ты должен выбрать в промежутке от 1 до 10");

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalArgumentException("Упс Ошибка");
        }

        if (result <= 0)
            throw new IllegalArgumentException("Отрицательных римлян не бывает");

        if (isRoman(first))
            return toRoman(result);
        else
            return String.valueOf(result);
    }

    private static boolean isArabic(String input) {
        try {
            int number = Integer.parseInt(input);
            return number >= 1 && number <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRoman(String input) {
        return input.matches("^[IVX]+$");
    }

    private static int parseNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return fromRoman(number);
        }
    }

    private static final Map<Character, Integer> ROMAN_MAP = new HashMap<>();

    static {
        ROMAN_MAP.put('I', 1);
        ROMAN_MAP.put('V', 5);
        ROMAN_MAP.put('X', 10);
    }

    private static int fromRoman(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int newValue = ROMAN_MAP.get(roman.charAt(i));
            if (newValue < prevValue) {
                result -= newValue;
            } else {
                result += newValue;
            }
            prevValue = newValue;
        }

        return result;
    }

    private static final String[] ROMAN_SYMBOLS = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private static final int[] ROMAN_VALUES = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

    private static String toRoman(int number) {
        StringBuilder sb = new StringBuilder();
        int i = ROMAN_VALUES.length - 1;
        while (number > 0 && i >= 0) {
            if (number >= ROMAN_VALUES[i]) {
                sb.append(ROMAN_SYMBOLS[i]);
                number -= ROMAN_VALUES[i];
            } else {
                i--;
            }
        }
        return sb.toString();
    }
}
