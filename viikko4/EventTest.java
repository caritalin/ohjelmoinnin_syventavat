import java.time.LocalDate;

class Event implements Comparable<Event> {
    private LocalDate date;
    private Description description;
    private Category category;

    public Event(LocalDate date, Description description, Category category) {
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public Description getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public int compareTo(Event otherEvent) {
        int dateComparison = this.date.compareTo(otherEvent.date);
        if (dateComparison != 0) {
            return dateComparison;
        }

        int descriptionComparison = this.description.toString().compareTo(otherEvent.description.toString());
        if (descriptionComparison != 0) {
            return descriptionComparison;
        }

        return this.category.toString().compareTo(otherEvent.category.toString());
    }

    @Override
    public String toString() {
        return "Event{" +
                "date=" + date +
                ", description=" + description +
                ", category=" + category +
                '}';
    }
}

class Description {
    private String value;

    public Description(String value) {
        if (value == null || value.isEmpty() || value.length() > 500) {
            throw new IllegalArgumentException("Description must not be null, empty, or exceed 500 characters");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

class Category {
    private String value;

    public Category(String value) {
        if (value == null || value.isEmpty() || value.length() > 50) {
            throw new IllegalArgumentException("Category must not be null, empty, or exceed 50 characters");
        }
        this.value = value.toLowerCase();
    }

    @Override
    public String toString() {
        return value;
    }
}

public class EventTest {
    public static void main(String[] args) {
        Event event1 = new Event(LocalDate.of(2022, 2, 15),
                new Description("Java Workshop"),
                new Category("programming"));

        Event event2 = new Event(LocalDate.of(2022, 3, 10),
                new Description("Team Building Event"),
                new Category("team-building"));

        Event event3 = new Event(LocalDate.of(2022, 1, 30),
                new Description("Nature Hike"),
                new Category("nature"));

        System.out.println(event1);
        System.out.println(event2);
        System.out.println(event3);
    }
}
