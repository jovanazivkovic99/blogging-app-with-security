# Dokerizovana blog aplikacija sa JWT Security

## Opis

Blog aplikacija razvijena korišćenjem Spring Boot-a, koja omogućava korisnicima da kreiraju, čitaju, ažuriraju i brišu blog postove, komentare i kategorije. 
Aplikacija podržava dve uloge: korisnik (USER) i administrator (ADMIN), sa različitim nivoima pristupa.

## Funkcionalnosti

- **Registracija i prijava korisnika**: Omogućava registraciju i prijavu korisnika, uz generisanje JWT tokena.
- **Upravljanje blog postovima**: Korisnici mogu kreirati, pregledati, ažurirati i brisati svoje blog postove.
- **Upravljanje komentarima**: Korisnici mogu dodavati komentare na postove i upravljati svojim komentarima.
- **Upravljanje kategorijama**: Samo administratori mogu kreirati, ažurirati i brisati kategorije postova.

## Tehnologije

- **Spring Boot**: Za razvoj aplikacije.
- **Spring Security**: Za implementaciju bezbednosti, uključujući autentifikaciju i autorizaciju korisnika.
- **JWT (JSON Web Token)**: Za autentifikaciju korisnika i upravljanje sesijama.
- **PostgreSQL**: Baza podataka za skladištenje informacija o korisnicima, postovima, komentarima i kategorijama.
