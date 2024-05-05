import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class days {

    private static final String EVENTS_FILE = "events.txt";
    private static List<String> events = new ArrayList<>();

    public static void main(String[] args) {
        loadEvents();

        if (args.length == 0) {
            System.out.println("Käyttö: days [toiminto] [parametrit]");
            return;
        }

        String action = args[0];

        switch (action) {
            case "list":
            int excludeIndex = Arrays.asList(args).indexOf("--exclude");
            if (excludeIndex != -1) {
                if (args.length >= excludeIndex + 2 && args[excludeIndex + 1].equals("--categories")) {
                    listEventsExcludingCategories(args);
                } else {
                    System.out.println("Virheellinen käyttö. Käyttöohjeet: days list --categories [kategoria1,kategoria2,...] --exclude");
                }
            } else if (args.length >= 3 && args[1].equals("--categories")) {
                listEventsByCategories(args);
            } else if (args.length >= 3 && args[1].equals("--date")) {
                listEventsOnDate(args);
            } else if (args.length >= 2 && args[1].equals("--today")) {
                listEventsToday();            
            } else if (args.length >= 5 && args[1].equals("--before-date") && args[3].equals("--after-date")) {
                listEventsBetweenDates(args);        
            } else if (args.length >= 3 && args[1].equals("--before-date")) {
                listEventsBeforeDate(args);
            } else if (args.length >= 3 && args[1].equals("--after-date")) {
                listEventsAfterDate(args);
            } else {
                // Tässä tapauksessa näytetään koko tapahtumalista
                for (String event : events) {
                    System.out.println(event);
                }
            }
            break;
        
            case "add":
                if (args.length == 7 && args[1].equals("--date") && args[3].equals("--category") && args[5].equals("--description")) {
                    addEventWithDateAndCategory(args);
                } else if (args.length == 5 && args[1].equals("--category") && args[3].equals("--description")) {
                    addEventWithoutDate(args);
                } else {
                    System.out.println("Virheellinen käyttö. Käyttöohjeet: days add --date [päivämäärä] --category [kategoria] --description [kuvaus]");
                }
                break;

            case "delete":
                int dryRunIndex = Arrays.asList(args).indexOf("--dry-run");
                if (dryRunIndex != -1) {
                    if (args.length >= dryRunIndex + 4 && args[dryRunIndex + 1].equals("--date") && args[dryRunIndex + 3].equals("--category")) {
                        dryRunDeleteEvents(Arrays.copyOfRange(args, 0, dryRunIndex + 4));
                    } else {
                        System.out.println("Virheellinen käyttö. Käyttöohjeet: days delete --date [päivämäärä] --category [kategoria] --dry-run");
                    }
                } else if (args.length >= 7 && args[1].equals("--date") && args[3].equals("--category") && args[5].equals("--description")) {
                    deleteEventsOnDateCategoryAndDescription(args);
                } else if (args.length >= 3 && args[1].equals("--description")) {
                    deleteEventsStartingWith(args);
                } else if (args.length >= 3 && args[1].equals("--date")) {
                    deleteEventsOnDate(args);
                } else {
                    deleteEvent(args);
                }
                break;

            default:
                System.out.println("Virheellinen toiminto: " + action);
                break;
        }

        saveEvents();
    }

    private static void addEventWithDateAndCategory(String[] args) {
        String date = args[2];
        String category = args[4];
        String description = args[6];
        events.add(date + " | " + category + " | " + description);
        System.out.println("Tapahtuma lisätty: " + date + " | " + category + " | " + description);
    }

    private static void addEventWithoutDate(String[] args) {
        String today = LocalDate.now().toString();
        String category = "";
        String description = "";

        // Käydään läpi argumentit ja etsitään kategoria ja kuvaus
        for (int i = 1; i < args.length; i += 2) {
            if (args[i].equals("--category")) {
                category = args[i + 1];
            } else if (args[i].equals("--description")) {
                description = args[i + 1];
            }
        }

        // Lisätään tapahtuma tälle päivälle annetulla kategoriolla ja kuvauksella
        events.add(today + " | " + category + " | " + description);
        System.out.println("Tapahtuma lisätty tälle päivälle: " + today + " | " + category + " | " + description);
    }

    private static void dryRunDeleteEvents(String[] args) {
        if (args.length < 5) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }

        String dateString = args[2];
        String category = args[4];

        LocalDate date = LocalDate.parse(dateString);
        List<String> toBeRemoved = new ArrayList<>();
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
            String eventCategory = parts[1];
            if (eventDate.equals(date) && eventCategory.equals(category)) {
                toBeRemoved.add(event);
            }
        }

        // Näytetään poistettavat tapahtumat dry-run -tilassa
        System.out.println("Poistettaisiin tapahtumat:");
        for (String event : toBeRemoved) {
            System.out.println(event);
        }
        System.out.println("Tapahtumia poistettaisiin yhteensä: " + toBeRemoved.size());
    }
    private static void deleteEvent(String[] args) {
        if (args.length < 6) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }
        String dateString = args[2];
        String category = args[4];
        List<String> toBeRemoved = new ArrayList<>();
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            String eventDate = parts[0];
            String eventCategory = parts[1];
            if (eventDate.equals(dateString) && eventCategory.equals(category)) {
                toBeRemoved.add(event);
            }
        }
        events.removeAll(toBeRemoved);
        System.out.println("Tapahtumat poistettu: " + toBeRemoved.size());
    }
    
    private static void deleteEventsOnDateCategoryAndDescription(String[] args) {
        if (args.length < 7) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }
    
        String dateString = args[2];
        String category = args[4];
        String descriptionKeyword = args[6];
    
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            System.out.println("Virheellinen päivämäärämuoto. Odotettiin muotoa yyyy-MM-dd.");
            return;
        }
    
        List<String> toBeRemoved = new ArrayList<>();
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate;
            try {
                eventDate = LocalDate.parse(parts[0]);
            } catch (DateTimeParseException e) {
                System.out.println("Virheellinen tapahtuman päivämäärämuoto: " + parts[0]);
                continue; // Siirry seuraavaan tapahtumaan
            }
            String eventCategory = parts[1];
            String eventDescription = parts[2];
            if (eventDate.equals(date) && eventCategory.equals(category) && eventDescription.startsWith(descriptionKeyword)) {
                toBeRemoved.add(event);
            }
        }
        events.removeAll(toBeRemoved);
        System.out.println("Tapahtumat poistettu: " + toBeRemoved.size());
    }
    
    private static void deleteEventsStartingWith(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }

        String searchString = args[2];
        List<String> toBeRemoved = new ArrayList<>();
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            String description = parts[2];
            if (description.startsWith(searchString)) {
                toBeRemoved.add(event);
            }
        }

        events.removeAll(toBeRemoved);
        System.out.println("Tapahtumat poistettu: " + toBeRemoved.size());
    }

    private static void deleteEventsOnDate(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuva päivämäärä");
            return;
        }
        String dateString = args[2];
        LocalDate date = LocalDate.parse(dateString);
        List<String> toBeRemoved = new ArrayList<>();
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
            if (eventDate.equals(date)) {
                toBeRemoved.add(event);
            }
        }
        events.removeAll(toBeRemoved);
        System.out.println("Tapahtumat poistettu: " + toBeRemoved.size());
    }

    private static void loadEvents() {
        // Lataa tapahtumat tiedostosta
        try (BufferedReader reader = new BufferedReader(new FileReader(EVENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                events.add(line);
            }
        } catch (IOException e) {
            System.out.println("Virhe tapahtumien lataamisessa: " + e.getMessage());
        }
    }

    private static void saveEvents() {
        // Tallenna tapahtumat tiedostoon
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (String event : events) {
                writer.println(event);
            }
        } catch (IOException e) {
            System.out.println("Virhe tapahtumien tallentamisessa: " + e.getMessage());
        }
    }

    private static void listEventsBeforeDate(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }
        String dateString = args[2];
        LocalDate date = LocalDate.parse(dateString);
        System.out.println("Tapahtumat ennen " + dateString + ":");
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
            if (eventDate.isBefore(date) || eventDate.equals(date)) {
                System.out.println(event);
            }
        }
    }

    private static void listEventsAfterDate(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }
        String dateString = args[2];
        LocalDate date = LocalDate.parse(dateString);
        System.out.println("Tapahtumat " + dateString + " jälkeen:");
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
            if (eventDate.isAfter(date) || eventDate.equals(date)) {
                System.out.println(event);
            }
        }
    }

    private static void listEventsToday() {
        LocalDate today = LocalDate.now();
        System.out.println("Tänään tapahtuvat tapahtumat:");
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate date = LocalDate.parse(parts[0]);
            if (date.equals(today)) {
                System.out.println(event);
            }
        }
    }
    private static void listEventsBetweenDates(String[] args) {
        if (args.length < 5) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }
        String beforeDateString = args[2];
        String afterDateString = args[4];
        LocalDate beforeDate = LocalDate.parse(beforeDateString);
        LocalDate afterDate = LocalDate.parse(afterDateString);
        System.out.println("Tapahtumat ennen " + beforeDateString + " ja jälkeen " + afterDateString + ":");
    
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
    
            // Tarkistetaan, että tapahtuman päivämäärä on ennen beforeDate:a ja jälkeen afterDate:a
            if (eventDate.isAfter(afterDate) && eventDate.isBefore(beforeDate)) {
                System.out.println(event);
            }
        }
    }

    private static void listEventsOnDate(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuva päivämäärä");
            return;
        }
        String dateString = args[2];
        LocalDate date = LocalDate.parse(dateString);
        System.out.println("Tapahtumat päivänä " + dateString + ":");
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            LocalDate eventDate = LocalDate.parse(parts[0]);
            if (eventDate.equals(date)) {
                System.out.println(event);
            }
        }
    }

    private static void listEventsByCategories(String[] args) {
        if (args.length < 3) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }

        // Erotellaan kategoriat pilkuilla
        String[] categories = args[2].split(",");

        // Tarkistetaan jokainen tapahtuma
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            String category = parts[1];

            // Tarkistetaan, kuuluuko tapahtuman kategoria annettuihin kategorioihin
            for (String cat : categories) {
                if (category.equals(cat)) {
                    System.out.println(event);
                    break;
                }
            }
        }
    }

    private static void listEventsExcludingCategories(String[] args) {
        if (args.length < 4) {
            System.out.println("Virhe: Puuttuvia parametrejä");
            return;
        }

        // Erotellaan kategoriat pilkuilla
        String[] categories = args[2].split(",");

        // Tarkistetaan jokainen tapahtuma
        for (String event : events) {
            String[] parts = event.split(" \\| ");
            String category = parts[1];
            boolean exclude = false;

            // Tarkistetaan, onko tapahtuman kategoria poissuljettujen kategorioiden joukossa
            for (String cat : categories) {
                if (category.equals(cat)) {
                    exclude = true;
                    break;
                }
            }

            // Tulostetaan tapahtuma, jos se ei kuulu poissuljettujen kategorioiden joukkoon
            if (!exclude) {
                System.out.println(event);
            }
        }
    }
}
