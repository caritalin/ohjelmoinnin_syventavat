import java.util.StringTokenizer;

public class Timestamp {
    private DateOnly date;   // Päivämääräolio
    private TimeOnly time;   // Kellonaikaolio

    // Konstruktori, joka ottaa vastaan DateOnly- ja TimeOnly-oliot
    public Timestamp(DateOnly date, TimeOnly time) {
        this.date = date;
        this.time = time;
    }

    // Getter-metodi päivämäärän saamiseksi
    public DateOnly getDate() {
        return date;
    }

    // Getter-metodi kellonajan saamiseksi
    public TimeOnly getTime() {
        return time;
    }

    // Ylimääritelty toString-metodi, palauttaa aikaleiman ISO 8601 -muodossa
    @Override
    public String toString() {
        return date.toString() + "T" + time.toString();
    }

    // Staattinen metodi aikaleiman parsimiseksi annetusta merkkijonosta
    public static Timestamp parse(String s) {
        // Erotetaan syötemerkkijä päivämäärä- ja kellonaikaosiksi
        StringTokenizer tokenizer = new StringTokenizer(s, "T");

        // Tarkistetaan, että osia on kaksi
        if (tokenizer.countTokens() != 2) {
            throw new IllegalArgumentException("Virheellinen aikaleiman muoto");
        }

        try {
            // Parsitaan päivämäärä- ja kellonaikaosat
            DateOnly parsedDate = parseDate(tokenizer.nextToken());
            TimeOnly parsedTime = parseTime(tokenizer.nextToken());

            // Palautetaan uusi Timestamp-olio
            return new Timestamp(parsedDate, parsedTime);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Virheellinen aikaleima: " + e.getMessage());
        }
    }

    // Yksityinen apumetodi päivämäärän parsimiseksi
    private static DateOnly parseDate(String s) {
        // Parsitaan päivämäärämerkkijono ja luodaan DateOnly-olio
        String[] parts = s.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Virheellinen päivämäärän muoto");
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            return new DateOnly(year, month, day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Virheellinen päivämäärän muoto");
        }
    }

    // Yksityinen apumetodi kellonajan parsimiseksi
    private static TimeOnly parseTime(String s) {
        // Parsitaan aikamerkkijono ja luodaan TimeOnly-olio
        String[] parts = s.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Virheellinen kellonajan muoto");
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);

            return new TimeOnly(hours, minutes, seconds);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Virheellinen kellonajan muoto");
        }
    }

    // Testiohjelma
    public static void main(String[] args) {
        try {
            // Luodaan Timestamp-olio
            Timestamp timestamp1 = new Timestamp(new DateOnly(2024, 1, 18), new TimeOnly(9, 45, 0));

            // Tulostetaan Timestamp-olio
            System.out.println("Timestamp 1: " + timestamp1);

            // Parsitaan aikaleima merkkijonosta
            Timestamp timestamp2 = Timestamp.parse("2024-01-18T09:45:00");
            System.out.println("Timestamp 2: " + timestamp2);
        } catch (IllegalArgumentException e) {
            System.err.println("Virhe: " + e.getMessage());
        }
    }
}
