import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Event {
    private LocalDate date;
    private String description;
    private String category;

    public Event(LocalDate date, String description, String category) {
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}

public class Main {
    public static void main(String[] args) {
        // Luodaan tapahtumalista ja lisätään siihen tapahtumia
        List<Event> events = new ArrayList<>();
        events.add(new Event(LocalDate.of(2022, 3, 15), "Java Conference", "Tech"));
        events.add(new Event(LocalDate.of(2022, 4, 20), "Networking Event", "Business"));
        events.add(new Event(LocalDate.of(2022, 5, 10), "Music Festival", "Entertainment"));
        events.add(new Event(LocalDate.of(2022, 6, 5), "Food Expo", "Food"));

        // Määritellään suodatuskriteeri (päivämääräväli)
        LocalDate startDate = LocalDate.of(2022, 4, 1);
        LocalDate endDate = LocalDate.of(2022, 6, 30);

        // Suodatetaan tapahtumat annetun päivämäärävälin perusteella
        List<Event> filteredEvents = events.stream()
                                            .filter(e -> e.getDate().isAfter(startDate) && e.getDate().isBefore(endDate))
                                            .collect(Collectors.toList());

        // Tulostetaan suodatetut tapahtumat
        System.out.println("Events between " + startDate + " and " + endDate + ":");
        for (Event event : filteredEvents) {
            System.out.println(event.getDescription() + " - " + event.getDate() + " - " + event.getCategory());
        }
    }
}
