import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    public static void main(String[] args) {

        String[] words = {"java", "cat", "dog", "hat", "sun"};


        Random random = new Random();
        String word = words[random.nextInt(words.length)];


        char[] displayWord = new char[word.length()];
        for (int i = 0; i < word.length(); i++) {
            displayWord[i] = '_';
        }

        
        int wrongGuesses = 0;
        int maxGuesses = 6;
        Scanner scanner = new Scanner(System.in);


        while (wrongGuesses < maxGuesses) {
            System.out.println("Word: " + new String(displayWord));

            if(word == "java")
            {
                System.out.println("Hint :A programming Language");
            }
            if(word == "cat")
            {
                System.out.println("Hint :An Animal");
            }
            if(word == "dog")
            {
                System.out.println("Hint :An Animal");
            }
            if(word == "hat")
            {
                System.out.println("Hint :A thing to wear");
            }
            if(word == "sun")
            {
                System.out.println("Hint :part of the solar system");
            }
            System.out.print("Guess a letter: ");
            char guess = scanner.next().charAt(0);

            boolean correctGuess = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    displayWord[i] = guess;
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                wrongGuesses++;
                System.out.println("Wrong guess!");
            }

            if (new String(displayWord).equals(word)) {
                System.out.println("You won! The word was: " + word);
                break;
            }
        }

        if (wrongGuesses == maxGuesses) {
            System.out.println("You lost! The word was: " + word);
        }

        scanner.close();
    }
}



