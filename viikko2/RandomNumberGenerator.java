import java.util.Random;

public class RandomNumberGenerator {
    public static void main(String[] args) {
        // Luo Random-olio satunnaislukujen generoimista varten
        Random random = new Random();

        // Arvo satunnainen kokonaisluku väliltä 1–100
        int randomNumber = random.nextInt(100) + 1;

        // Tulosta arvottu luku käyttäen printf-metodia
        System.out.printf("Luku, jota ajattelen on %d%n", randomNumber);
    }
}

