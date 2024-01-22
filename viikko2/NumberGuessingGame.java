import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        // Luo Random-olio satunnaislukujen generoimista varten
        Random random = new Random();

        // Arvo satunnainen kokonaisluku väliltä 1–100
        int secretNumber = random.nextInt(100) + 1;

        // Luo Scanner-olio käyttäjän syötteen lukemista varten
        Scanner scanner = new Scanner(System.in);

        // Alustetaan pelin tila
        boolean gameWon = false;
        int guessesLeft = 7;

        // Peli jatkuu, kunnes käyttäjä voittaa tai arvaukset loppuvat
        while (!gameWon && guessesLeft > 0) {
            System.out.print("Arvaa luku (1-100): ");
            int userGuess = scanner.nextInt();

            // Tarkista käyttäjän arvaus
            if (userGuess == secretNumber) {
                System.out.println("Onneksi olkoon! Arvasit oikein.");
                gameWon = true;
            } else {
                System.out.println("Väärin! Yritä uudelleen.");
                guessesLeft--;
                System.out.println("Arvauksia jäljellä: " + guessesLeft);
            }
        }

        // Peli päättyi, tulosta oikea vastaus
        System.out.println("Oikea vastaus oli: " + secretNumber);

        // Sulje Scanner
        scanner.close();
    }
}

