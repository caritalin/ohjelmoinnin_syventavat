extern crate chrono;
use chrono::naive::NaiveDate;

// Tapahtuma-struct
struct Tapahtuma {
    paivays: NaiveDate,
    kuvaus: String,
    kategoria: String,
}

fn main() {
    // Luo tapahtumia
    let tapahtuma1 = Tapahtuma {
        paivays: NaiveDate::from_ymd(2024, 4, 17),
        kuvaus: String::from("Ensimmäinen tapahtuma"),
        kategoria: String::from("Tärkeä tapahtuma"),
    };
    let tapahtuma2 = Tapahtuma {
        paivays: NaiveDate::from_ymd(2024, 4, 18),
        kuvaus: String::from("Toinen tapahtuma"),
        kategoria: String::from("Vähemmän tärkeä tapahtuma"),
    };
    let tapahtuma3 = Tapahtuma {
        paivays: NaiveDate::from_ymd(2024, 4, 19),
        kuvaus: String::from("Kolmas tapahtuma"),
        kategoria: String::from("Ei niin tärkeä tapahtuma"),
    };

    // Luo vektori ja lisää tapahtumat siihen
    let mut tapahtumat: Vec<Tapahtuma> = Vec::new();
    tapahtumat.push(tapahtuma1);
    tapahtumat.push(tapahtuma2);
    tapahtumat.push(tapahtuma3);

    // Tulosta tapahtumat
    println!("Tapahtumat:");
    for tapahtuma in &tapahtumat {
        println!(
            "Päiväys: {}, Kuvaus: {}, Kategoria: {}",
            tapahtuma.paivays, tapahtuma.kuvaus, tapahtuma.kategoria
        );
    }
}
