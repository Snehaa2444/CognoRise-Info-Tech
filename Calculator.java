import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

        System.out.println("______________________________");
        System.out.println("|         Calculator         |");
        System.out.println("|____________________________|");
        System.out.println("|    1    |   2    |    3    |");
        System.out.println("|---------|--------|---------|");
        System.out.println("|    4    |   5    |    6    |");
        System.out.println("|---------|--------|---------|");
        System.out.println("|    7    |   8    |    9    |");
        System.out.println("|---------|--------|---------|");
        System.out.println("|    0    |   +    |    -    |");
        System.out.println("|---------|--------|---------|");
        System.out.println("|    C    |   *    |    /    |");
        System.out.println("|_________|________|_________|");
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter the second number: ");
            double num2 = scanner.nextDouble();

            System.out.print("Enter an operator (+, -, *, /): ");
            char operator = scanner.next().charAt(0);

            double result = 0;
            boolean validOperation = true;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new ArithmeticException("Division by zero is not allowed.");
                    }
                    result = num1 / num2;
                    break;
                default:
                    System.out.println("Error: Invalid operator.");
                    validOperation = false;
            }

            if (validOperation) {
                System.out.println("The result is: " + result);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter numeric values.");
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
