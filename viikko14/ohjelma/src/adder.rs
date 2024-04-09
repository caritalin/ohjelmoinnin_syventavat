use std::env;

fn main() {
    // Haetaan komentoriviparametrit
    let args: Vec<String> = env::args().collect();

    // Ensimmäinen argumentti on ohjelman nimi, joten jätetään se huomiotta
    let numbers: Vec<i32> = args[1..].iter().map(|arg| {
        // Muunnetaan argumentit kokonaisluvuiksi
        arg.parse::<i32>().unwrap_or_else(|_| {
            // Virheenkäsittely: jos muunnos epäonnistuu, palautetaan oletusarvo 0
            println!("Virhe: {} ei ole kelvollinen kokonaisluku, ohitetaan.", arg);
            0
        })
    }).collect();
    
    // Lasketaan lukujen summa
    let sum: i32 = numbers.iter().sum();

    println!("{}", sum);
}
