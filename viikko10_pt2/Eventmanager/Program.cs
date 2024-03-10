using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

class Program
{
    static void Main(string[] args)
    {
        if (args.Length != 3)
        {
            Console.WriteLine("Usage: dotnet run <start-date> <end-date> <events-path>");
            return;
        }

        string startDateString = args[0];
        string endDateString = args[1];
        string eventsPath = args[2];

        if (!DateTime.TryParse(startDateString, out DateTime startDate) || !DateTime.TryParse(endDateString, out DateTime endDate))
        {
            Console.WriteLine("Invalid date format. Please use ISO 8601 format (yyyy-MM-dd).");
            return;
        }

        // Tarkista, onko annettu polku kelvollinen
        if (!File.Exists(eventsPath))
        {
            Console.Error.WriteLine($"The specified path '{eventsPath}' does not exist.");
            return;
        }

        // Luo EventManager-instanssi
        EventManager eventManager = new EventManager();

        // Aseta EventManager-luokan EventsPath-ominaisuus
        eventManager.EventsPath = eventsPath;

        // Testaa tapahtumien lukemista
        bool success = eventManager.ReadEvents();
        if (success)
        {
            Console.WriteLine($"Events between {startDate.ToShortDateString()} and {endDate.ToShortDateString()}:");
            var eventsBetweenDates = eventManager.Events?.Where(ev => ev.Date >= startDate && ev.Date <= endDate);
            if (eventsBetweenDates != null)
            {
                foreach (var ev in eventsBetweenDates)
                {
                    Console.WriteLine($"{ev.Date}: {ev.Description}");
                }
            }
        }
        else
        {
            Console.WriteLine("Failed to read events.");
        }
    }
}

// Luokka tapahtumien käsittelyä varten
public class EventManager
{
    // Luettelo tapahtumista
    public List<Event>? Events { get; private set; }

    // Tiedoston polku, josta tapahtumat luetaan
    public string? EventsPath { get; set; }

    // Konstruktori
    public EventManager()
    {
        Events = new List<Event>();
    }

    // Metodi tapahtumien lukemiseksi tiedostosta
    public bool ReadEvents()
    {
        if (string.IsNullOrEmpty(EventsPath))
        {
            Console.Error.WriteLine("EventsPath is not set.");
            return false;
        }

        if (!File.Exists(EventsPath))
        {
            Console.Error.WriteLine($"File '{EventsPath}' does not exist.");
            return false;
        }

        // Lue tapahtumatiedosto ja käsittele tapahtumat
        try
        {
            using (StreamReader sr = new StreamReader(EventsPath))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    // Käsittelee tapahtumatiedoston riviä ja lisää tapahtuma listaan
                    // Oletetaan tässä, että jokainen rivi sisältää tapahtuman päivämäärän ja kuvauksen eroteltuna esim. pilkulla
                    string[] parts = line.Split(',');
                    if (parts.Length >= 2)
                    {
                        DateTime date;
                        if (DateTime.TryParse(parts[0], out date))
                        {
                            Events?.Add(new Event(date, parts[1]));
                        }
                    }
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            Console.Error.WriteLine($"Failed to read events: {ex.Message}");
            return false;
        }
    }

    // Luokka tapahtumille
    public class Event
    {
        public DateTime Date { get; set; }
        public string Description { get; set; }

        public Event(DateTime date, string description)
        {
            Date = date;
            Description = description;
        }
    }
}
