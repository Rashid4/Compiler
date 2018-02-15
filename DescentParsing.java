/*
Recursive Descent Parsing implementation
Name: Rashid Ahmed
Roll: 15
CSE, University of Dhaka
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DescentParsing {

    private static String input;
    private static int currentPos;

    private static boolean goal() {
        System.out.println("goal -> expr");
        return expr();
    }

    private static boolean expr() {
        System.out.println("expr -> term expr2");
        if (term()) {
            return expr2();
        }
        return false;
    }

    private static boolean term() {
        System.out.println("term -> factor term2");
        if (factor()) {
            return term2();
        }
        return false;
    }

    private static boolean expr2() {
        if (input.charAt(currentPos) == '+' || input.charAt(currentPos) == '-') {
            System.out.println("expr2 = " + input.charAt(currentPos) + " term expr2");
            currentPos++;
            System.out.println("Advanced");
            if (term()) {
                return expr2();
            }
            return false;
        }
        System.out.println("expr2 -> null");
        return true;
    }

    private static boolean factor() {
        if (Character.isLetterOrDigit(input.charAt(currentPos))) {
            System.out.println("factor -> " + input.charAt(currentPos));
            currentPos++;
            System.out.println("Advanced");
            return true;
        }
        if (input.charAt(currentPos) == '(') {
            System.out.println("factor -> (expr)");
            currentPos++;
            System.out.println("Advanced");
            if (expr()) {
                if (input.charAt(currentPos) == ')') {
                    System.out.println("Put )");
                    currentPos++;
                    System.out.println("Advanced");
                    return true;
                }
                System.out.println("Syntax error: missing colsing parenthesis");
                return false;
            }
            return false;
        }
        System.out.println("Expected number/identifier or parenthesis");
        System.out.println("Trying to backtrack");
        return false;
    }

    private static boolean term2() {

        if (input.charAt(currentPos) == '*' || input.charAt(currentPos) == '/') {
            System.out.println("term2 -> *// factor term2");
            currentPos++;
            if (factor()) {
                return term2();
            }
            return false;
        }
        if (input.charAt(currentPos) == '#') {
            System.out.println("term2 -> null");
            return true;
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("in.txt"));

        while (fileScanner.hasNext()) {
            input = fileScanner.nextLine();
            printBorder();
            System.out.println("Input: " + input);
            if (input.contains(" ")) {
                input = trim(input);
            }

            input = input + "#";
            currentPos = 0;
            System.out.println();
            boolean accept = goal();
            System.out.println();

            if (accept) {
                System.out.println("String accepted.");
            } else {
                System.out.println("String rejected.");
            }
            printBorder();
        }
    }

    private static String trim(String word) {
        String temp = "";
        int len = word.length(), i;
        for (i = 0; i < len; i++) {
            if (word.charAt(i) != ' ') {
                temp = temp + word.charAt(i);
            }
        }
        return temp;
    }

    private static void printBorder() {
        int i;
        for (i = 0; i < 40; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
