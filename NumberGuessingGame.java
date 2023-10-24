package CodeAlpha;
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int lowerLimit = 1;
        int upperLimit = 100;
        int secretNumber = random.nextInt(upperLimit - lowerLimit + 1) + lowerLimit;
        int attempts = 0;
        
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have selected a random number between " + lowerLimit + " and " + upperLimit + ".");
        
        while (true) {
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();
            attempts++;
            
            if (userGuess < lowerLimit || userGuess > upperLimit) {
                System.out.println("Please enter a number between " + lowerLimit + " and " + upperLimit + ".");
            } else if (userGuess == secretNumber) {
                System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                break;
            } else if (userGuess < secretNumber) {
                System.out.println("The number is higher. Try again.");
            } else {
                System.out.println("The number is lower. Try again.");
            }
        }
        scanner.close();
    }
}

