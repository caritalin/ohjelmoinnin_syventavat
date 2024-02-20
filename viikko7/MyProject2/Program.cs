using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using CsvHelper;
using CsvHelper.Configuration;

public class Event
{
    public DateTime Date { get; set; }
    public string Description { get; set; } = string.Empty; // Oletusarvo tyhjä merkkijono
    public string Category { get; set; } = string.Empty; // Oletusarvo tyhjä merkkijono
}

class Program
{
    static void Main(string[] args)
    {
        List<Event> events = ReadEventsFromCsv("events.csv");
        events.Sort((x, y) => y.Date.CompareTo(x.Date)); // Järjestetään päivämäärän mukaan, uusin ensin
        
        foreach (var e in events)
        {
            Console.WriteLine($"{e.Date.ToString("yyyy-MM-dd")}, {e.Description}, {e.Category}");
        }
    }

    static List<Event> ReadEventsFromCsv(string filePath)
    {
        List<Event> events = new List<Event>();

        using (var reader = new StreamReader(filePath))
        {
            var configuration = new CsvConfiguration(CultureInfo.InvariantCulture)
            {
                HasHeaderRecord = true,
                Delimiter = ","
            };

            using (var csv = new CsvReader(reader, configuration))
            {
                csv.Context.RegisterClassMap<EventMap>(); // Rekisteröi EventMap

                while (csv.Read())
                {
                    var record = csv.GetRecord<Event>();
                    events.Add(record);
                }
            }
        }

        return events;
    }
}

public sealed class EventMap : ClassMap<Event>
{
    public EventMap()
    {
        Map(m => m.Date).Index(0).Name("date");
        Map(m => m.Description).Index(1).Name("description");
        Map(m => m.Category).Index(2).Name("category");
    }
}
