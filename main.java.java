import java.util.Scanner;
abstract class Operation {
    abstract double calculate(double a, double b);}

class Add extends Operation {
    double calculate(double a, double b) { return a + b; }
}
class Subtract extends Operation {
    double calculate(double a, double b) { return a - b; }
}
class Multiply extends Operation {
    double calculate(double a, double b) { return a * b; }
}
class Divide extends Operation {
    double calculate(double a, double b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }}
class Modulus extends Operation {
    double calculate(double a, double b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a % b;
    }
}
class SquareRoot extends Operation {
    double calculate(double a, double b) {
        if (a < 0) throw new ArithmeticException("Square root of negative number not allowed");
        return Math.sqrt(a); // ignore b
    }}
class Power extends Operation {
    double calculate(double a, double b) { return Math.pow(a, b); }}
class Calculator {
    double lastResult = 0;
    String[] history = new String[10];
    int historyCount = 0;

    void addHistory(String action) {
        if (historyCount < 10) {
            history[historyCount++] = action;
        } else {
            for (int i = 1; i < 10; i++) {
                history[i - 1] = history[i];
            }
            history[9] = action;}}
    void showHistory() {
        System.out.println("\n--- History ---");
        if (historyCount == 0) {
            System.out.println("No past actions.");
        } else {
            for (int i = 0; i < historyCount; i++) {
                System.out.println((i + 1) + ". " + history[i]);
            }}}
    void previous() {  
        if (historyCount > 0) {
            System.out.println("Returning to previous state: " + history[historyCount - 1]);
            historyCount--;
            lastResult = (historyCount > 0) ? 
                          Double.parseDouble(history[historyCount - 1].split("=")[1].trim()) 
                          : 0;
        } else {
            System.out.println("Nothing to go back to.");
            lastResult = 0;
        }}
    void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nLast Result: " + lastResult);

            System.out.print("Enter first digit or 'last': ");  // changed text here
            String in1 = sc.next();
            double num1 = in1.equalsIgnoreCase("last") ? lastResult : Double.parseDouble(in1);

            System.out.print("Enter operation (+, -, *, /, %, sqrt, ^, history, previous, exit): ");
            String op = sc.next();
            if (op.equalsIgnoreCase("exit")) break;

            if (op.equalsIgnoreCase("history")) {
                showHistory();
                continue;
            }
            if (op.equalsIgnoreCase("previous")) {
                previous();
                continue;
            }

            double num2 = 0; // default
            if (!op.equalsIgnoreCase("sqrt")) {
                System.out.print("Enter second number or 'last': ");
                String in2 = sc.next();
                num2 = in2.equalsIgnoreCase("last") ? lastResult : Double.parseDouble(in2); }
            Operation operation;
            switch(op) {
                case "+": operation = new Add(); break;
                case "-": operation = new Subtract(); break;
                case "*": operation = new Multiply(); break;
                case "/": operation = new Divide(); break;
                case "%": operation = new Modulus(); break;
                case "sqrt": operation = new SquareRoot(); break;
                case "^": operation = new Power(); break;
                default:
                    System.out.println("Invalid operation.");
                    continue;
            }

            try {
                lastResult = operation.calculate(num1, num2);
                String action = num1 + " " + op + " " + num2 + " = " + lastResult;
                addHistory(action);
                System.out.println("Result: " + lastResult);
            } catch(ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Calculator exited.");
        sc.close();
    }}
public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.start();
    }}
