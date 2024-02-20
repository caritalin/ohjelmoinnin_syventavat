using System;

class Program
{
    static void Main()
    {
        // Luodaan uusi kategoria
        Category category1 = new Category("apple", "macos");
        Category category2 = new Category("microsoft");

        // Luodaan uusi tapahtuma
        Event event1 = new Event(new DateOnly(2024, 2, 19), "Apple event", category1);
        Event event2 = new Event(new DateOnly(2024, 2, 20), "Microsoft event", category2);

        // Tulostetaan tapahtumat
        Console.WriteLine(event1);
        Console.WriteLine(event2);
    }
}
