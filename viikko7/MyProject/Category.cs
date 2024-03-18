using System;

// Lisätään kaikki tarvittavat using-lauseet ennen muiden elementtien määrittelyä
public class Category
{
    // Properties for primary and secondary category names
    private string primary;
    private string? secondary;

    public string Primary
    {
        get => primary;
        set
        {
            if (string.IsNullOrEmpty(value))
            {
                throw new ArgumentException("Primary category cannot be null or empty");
            }

            if (value.Length > 50)
            {
                throw new ArgumentException("Primary category is too long");
            }

            primary = value.ToLower();
        }
    }

    public string? Secondary
    {
        get => secondary;
        set
        {
            if (value != null && value.Length > 50)
            {
                throw new ArgumentException("Secondary category is too long");
            }

            secondary = value?.ToLower(); // Null-conditional operator (?.) to ensure null is assigned if value is null
        }
    }

    // Constructor
    public Category(string primary, string? secondary = null)
    {
        Primary = primary;
        Secondary = secondary;
    }

    // ToString method
    public override string ToString()
    {
        if (Secondary != null)
        {
            return $"{Primary}/{Secondary}";
        }
        else
        {
            return Primary;
        }
    }
}
