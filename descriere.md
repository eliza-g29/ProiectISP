# Proiect ISP — Descriere detaliata a relatiilor si fluxurilor
 
## Contextul aplicatiei
 
Proiectul implementeaza un sistem de inchiriere a echipamentelor publice de micro-mobilitate: trotinete, biciclete si masini electrice. Sistemul are doua categorii de utilizatori cu responsabilitati distincte: clientii care inchiriaza vehicule si platesc in functie de timpul de utilizare, si administratorii care supravegheaza si gestioneaza parcul de echipamente.
 
---
 
## Diagrama cazurilor de utilizare
 
### Actori si relatia de generalizare
 
Sistemul defineste trei actori: `Utilizator`, `Client` si `Administrator echipamente`. Relatia dintre `Utilizator` si cei doi actori derivati este una de **generalizare**, motivata de faptul ca ambii au comportament comun care nu trebuie duplicat: orice persoana care foloseste sistemul, indiferent de rol, trebuie sa isi creeze un cont si sa se autentifice. Generalizarea permite ca aceste doua cazuri de utilizare sa fie definite o singura data la nivelul actorului de baza si mostenite automat de `Client` si `Administrator`.
 
### Cazurile de utilizare ale actorului Utilizator
 
`Creare cont` si `Autentificare` sunt legate printr-o relatie `<<include>>`. Aceasta relatie este justificata prin faptul ca fluxul de creare a unui cont presupune in mod obligatoriu si neconditionat pasul de autentificare: nu exista scenariu in care un cont este creat fara ca utilizatorul sa fie ulterior identificat in sistem. `<<include>>` este alegerea corecta fata de `<<extend>>` tocmai pentru ca dependenta este permanenta si nu optionala.
 
### Fluxul clientului
 
Clientul are doua puncte de contact cu sistemul: `Incepere Plasare comanda` si `Raportare defectiuni`.
 
`Raportare defectiuni` este legat de `Client` printr-o simpla relatie de asociere, fara nicio conexiune cu restul cazurilor de utilizare. Motivatia este ca raportarea unei defectiuni este o actiune complet independenta de fluxul de inchiriere: un client poate raporta o problema oricand, fara sa fi initiat sau finalizat o comanda.
 
`Incepere Plasare comanda` este punctul de intrare intr-un lant liniar de relatii `<<include>>`. Motivatia folosirii `<<include>>` de-a lungul intregului lant este ca fiecare pas este o preconditie stricta si obligatorie a celui anterior: nu exista scenariu in care un caz de utilizare din lant se executa fara ca cel pe care il include sa fi fost parcurs.
 
`Incepere Plasare comanda` include `Solicita catalog`. Motivatia este ca initierea unei comenzi presupune cunoasterea ofertei disponibile: fara date despre echipamente, intregul flux nu poate porni. Catalogul este sursa primara de informatie a sistemului.
 
`Solicita catalog` include `Verifica harta`. Motivatia este ca datele din catalog nu sunt utile clientului in forma bruta: ele trebuie reprezentate spatial, pe o harta, pentru ca utilizatorul sa poata evalua disponibilitatea si proximitatea echipamentelor. Verificarea hartii este deci pasul imediat urmator solicitarii catalogului.
 
`Verifica harta` include `Selectare vehicul`. Motivatia este ca harta este instrumentul prin care clientul identifica si alege un echipament concret. Nu poti selecta un vehicul fara sa fi vazut mai intai harta: selectia este rezultatul direct al inspectarii hartii.
 
`Selectare vehicul` include `Efectuare plata`. Motivatia este ca odata ce vehiculul a fost ales si utilizat, plata este o consecinta obligatorie si inevitabila a selectiei: modelul de business al aplicatiei nu permite utilizarea fara plata. Nu exista scenariu in care un vehicul este selectat si folosit fara ca o tranzactie sa fie generata.
 
### Fluxul administratorului
 
Cazul central al administratorului este `Verificare stare echipamente`, spre care converg doua relatii `<<extend>>` independente si din care porneste indirect inca una.
 
`Localizare echipament pe harta` extinde `Verificare stare echipamente`. Motivatia folosirii `<<extend>>` este ca localizarea unui echipament pe harta este un comportament optional si contextual: un administrator poate verifica starea unui echipament fara sa il fi localizat explicit pe harta, de exemplu daca stie deja unde se afla. Localizarea este deci o extensie care se declanseaza doar in anumite situatii, nu o preconditie permanenta.
 
`Actualizare status echipament` extinde de asemenea `Verificare stare echipamente`. Motivatia este similara: verificarea starii nu implica obligatoriu si modificarea ei. Un administrator poate constata ca starea unui echipament este corecta si sa nu faca nicio actualizare. Actualizarea este un comportament suplimentar, conditionat de rezultatul verificarii, ceea ce justifica `<<extend>>`.
 
`Descarcare raport` extinde `Actualizare status echipament`. Motivatia este ca descarcarea unui raport are sens in contextul in care statusurile au fost aduse la zi, insa nu este o consecinta obligatorie a oricarei actualizari: un administrator poate actualiza starea unui echipament fara sa genereze un raport de fiecare data. Raportul este un comportament optional, declansat selectiv dupa actualizare.
 
---

# Proiect ISP — Diagrama de clase

## Ierarhia de mostenire

`Utilizator` este clasa de baza cu atributele comune, toate cu vizibilitate protejata: `nume`, `prenume`, `ID_client`, `email`, `parola` si `tip` de tip `Tip_utilizator`. Ofera doua metode publice mostenite de toti utilizatorii: `autentificare()` care returneaza bool, si `creare_cont()` care primeste nume, prenume, email, parola si tip si returneaza un obiect `Cont`.

Atat `Client` cat si `Administrator` mostenesc `Utilizator` prin relatii de mostenire distincte, vizibile in diagrama prin sagetile cu triunghi gol care converg spre `Utilizator`. Mostenirea este justificata de nucleul comun de identitate si comportament pe care ambele clase il impart fara sa il redefineasca.

`Client` adauga un singur atribut propriu: `comanda` de tip `Comanda`, cu vizibilitate privata. Nu adauga metode proprii -- comportamentul sau specific este gestionat prin asocierea cu `Comanda`.

`Administrator` nu adauga atribute proprii, ci o singura metoda publica: `descarcare_raport(echipament: Echipament): void`. Parametrul este un `Echipament` concret, ceea ce inseamna ca administratorul genereaza raportul pentru un echipament specific, nu pentru intregul parc dintr-o singura apelare.

## Asocierea Client -- Comanda

Multiplicitatea este `1` pe partea `Client` si `0..*` pe partea `Comanda`: un client poate fi asociat cu zero sau mai multe comenzi, iar o comanda apartine unui singur client. Relatia este asociere simpla, nu compozitie, deoarece `Comanda` retine `ID_client` ca valoare primitiva, nu o referinta gestionata de ciclul de viata al clientului.

## Clasa Comanda

Are patru atribute private: `ID_comanda` de tip Integer, `echipament` de tip `Echipament`, `ID_client` de tip Integer, `Timp_utilizare` de tip Integer si `pret_final` de tip Float. `echipament` este un atribut intern al comenzii, fara o relatie explicita in diagrama intre `Comanda` si `Echipament` ca clase separate. Expune doua metode publice: `plasare_comanda(comanda: Comanda): Integer`, care primeste redundant propria instanta ca parametru si returneaza ID-ul comenzii, si `efectuare_plata(Timp_utilizare: Integer, pret_pe_min: Integer): void`, care calculeaza pretul final.

## Compozitia Catalog -- Echipament

Relatia dintre `Catalog` si `Echipament` este de **compozitie**, cu multiplicitate `1..*` pe partea `Echipament`. Compozitia este justificata de faptul ca echipamentele nu au existenta independenta de catalog: ciclul lor de viata este gestionat in intregime de catalog. Un echipament nu poate exista in sistem in afara unui catalog, iar distrugerea catalogului implica distrugerea echipamentelor continute. Multiplicitatea `1..*` impune ca un catalog sa contina cel putin un echipament pentru a fi valid.

## Clasa Echipament

Are cinci atribute cu vizibilitate protejata: `ID_echipament` de tip Integer, `tip_echipament` de tip `Tip_echipament`, `pret_pe_min` de tip Float, `stare` de tip `Stare_echipament` si `locatie` de tip String. Expune doua metode publice: `actualizare_stare(stare_noua: Stare_echipament): void` si `afisare_stare(): void`.

## Enumerarile

`Tip_utilizator` tipizeaza atributul `tip` din `Utilizator` cu valorile `Client` si `Administrator echipamente`, eliminand posibilitatea asignarii unor valori invalide la nivel de compilator.

`Stare_echipament` tipizeaza atributul `stare` din `Echipament` cu valorile `functional` si `defect`, garantand ca starea unui echipament nu poate lua decat una dintre cele doua valori definite.

`Tip_echipament` tipizeaza atributul `tip_echipament` din `Echipament` cu valorile `trotineta`, `bicicleta` si `masina`.

Toate trei sunt relatii de dependenta de tip: clasele le folosesc ca domeniu de valori pentru atribute, nu pastreaza referinte la instante ale enumerarilor.
