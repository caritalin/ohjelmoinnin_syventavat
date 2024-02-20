// Event.cs

using System;

public class Event : IComparable<Event>
{
    // Private backing field for description
    private string description;

    // Description property with validation
    public string Description
    {
        get => description;
        set
        {
            if (string.IsNullOrEmpty(value))
            {
                throw new ArgumentNullException(nameof(value), "Description cannot be null or empty");
            }

            if (value.Length > 500)
            {
                throw new ArgumentException("Description is too long", nameof(value));
            }

            description = value;
        }
    }

    // Category property using Category class
    public Category EventCategory { get; set; }

    // Date property
    public DateOnly Date { get; set; }

    // Constructor
    public Event(DateOnly date, string description, Category category)
    {
        Date = date;
        Description = description;
        EventCategory = category;
    }

    // ToString method
    public override string ToString()
    {
        return $"{Date} {Description} ({EventCategory})";
    }

    // IComparable implementation based on Date
    public int CompareTo(Event? other)
    {
        if (other == null)
        {
            return 1;
        }

        return Date.CompareTo(other.Date);
    }
}

