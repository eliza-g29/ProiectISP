package proiect_ISP;

public class Main {
    public static void main(String[] args) {

        // 1. ADMINISTRATOR - creare si autentificare

        System.out.println("1. Administrator - creare si autentificare ");
        Administrator admin = new Administrator("Dumitrescu", "Ana", 0, "ana.admin@mail.ro", "adminpass");
        System.out.println(admin);
        System.out.println("Autentificare administrator: " + admin.autentificare());

        // 2. CATALOG + COMPOZITIE - echipamente create prin Administrator
        
        System.out.println("\n 2. Catalog - adaugare echipamente (prin Administrator) ");
        Catalog catalog = new Catalog();
        admin.adauga_echipament(catalog, 1, Tip_echipament.trotineta, 0.5f, Stare_echipament.functional, "Piata Unirii");
        admin.adauga_echipament(catalog, 2, Tip_echipament.bicicleta, 0.3f, Stare_echipament.defect, "Parcul Cismigiu");
        admin.adauga_echipament(catalog, 3, Tip_echipament.masina, 2.0f, Stare_echipament.functional, "Gara de Nord");
        System.out.println(catalog);

        // 3. ECHIPAMENT - afisare stare, actualizare stare
        
        System.out.println("\n 3. Echipament - afisare si actualizare stare ");
        Echipament e1 = catalog.gaseste_echipament(1);
        Echipament e2 = catalog.gaseste_echipament(2);
        Echipament e3 = catalog.gaseste_echipament(3);

        e1.afisare_stare();
        e2.afisare_stare();
        e3.afisare_stare();

        System.out.println("\n Actualizare stare echipament ID=2 (defect -> functional) prin Administrator:");
        admin.actualizare_stare_echipament(catalog, 2, Stare_echipament.functional);
        e2.afisare_stare();

        // 4. CATALOG - verifica harta, echipamente functionale la locatie
        
        System.out.println("\n 4. Catalog - harta echipamente disponibile ");
        System.out.println(catalog.verifica_harta());

        System.out.println("Echipamente functionale la locatia 'Piata Unirii':");
        for (Echipament e : catalog.echipamente_functionale_la_locatie("Piata Unirii")) {
            e.afisare_stare();
        }

        System.out.println("\n Exista echipamente functionale in catalog: " + catalog.exista_echipament_functional());

        // 5. CLIENT - creare, autentificare, creare cont
        
        System.out.println("\n 5. Client - creare si autentificare ");
        Client client1 = new Client("Popescu", "Ion", 101, "ion.popescu@mail.ro", "parola123");
        Client client2 = new Client("Ionescu", "Maria", 102, "maria.ionescu@mail.ro", "parola456");
        System.out.println(client1);
        System.out.println(client2);
        System.out.println("Autentificare client1: " + client1.autentificare());

        Cont cont = client1.creare_cont("Popescu", "Ion", "ion.popescu@mail.ro", "parola123", Tip_utilizator.Client);
        System.out.println("Cont creat: " + cont);

        // 6. COMANDA - plasare si plata (asociere Client - Comanda)

        System.out.println("\n 6. Comanda - plasare si efectuare plata");

        // Prima comanda: client1 inchiriaza trotineta
        Comanda comanda1 = new Comanda(e1, client1.getID_client());
        client1.adauga_comanda(comanda1);
        int idComanda1 = comanda1.plasare_comanda(comanda1);
        System.out.println("Comanda plasata cu ID: " + idComanda1);
        comanda1.efectuare_plata(30, e1.getPret_pe_min());
        System.out.println(comanda1);

        // A doua comanda: client2 inchiriaza masina
        Comanda comanda2 = new Comanda(e3, client2.getID_client());
        client2.adauga_comanda(comanda2);
        int idComanda2 = comanda2.plasare_comanda(comanda2);
        System.out.println("Comanda plasata cu ID: " + idComanda2);
        comanda2.efectuare_plata(60, e3.getPret_pe_min());
        System.out.println(comanda2);
        
        // 6.1 CLIENT - raportare defectiune dupa incheierea comenzii

        System.out.println("\n 6.1 Client - raportare defectiune dupa incheierea comenzii ");

        // Caz 1: client1 nu raporteaza defectiune
        client1.raporteaza_defectiune(comanda1, false, "");

        System.out.println("\nStare echipament dupa cazul 1:");
        e1.afisare_stare();

        // Caz 2: client2 raporteaza defectiune
        client2.raporteaza_defectiune(comanda2, true, "Masina nu mai porneste dupa finalizarea cursei.");

        System.out.println("\nStare echipament dupa cazul 2:");
        e3.afisare_stare();


        // Afisare comenzi per client
        System.out.println("\nComenzi client1:");
        for (Comanda c : client1.getComenzi()) {
            System.out.println("  " + c);
        }

        System.out.println("Comenzi client2:");
        for (Comanda c : client2.getComenzi()) {
            System.out.println("  " + c);
        }

        // 7. ADMINISTRATOR - vizualizare comenzi client (asociere Admin-Client)

        System.out.println("\n 7. Administrator - vizualizare comenzi per client ");
        admin.vizualizare_comenzi(client1);
        admin.vizualizare_comenzi(client2);

        // 8. ADMINISTRATOR - descarcare raport (asociere Admin-Catalog)

        System.out.println("\n 8. Administrator - descarcare raport ");
        admin.descarcare_raport(catalog);

        // 9. ADMINISTRATOR - stergere echipament

        System.out.println("\n 9. Administrator - stergere echipament ");
        admin.sterge_echipament(catalog, 2);
        System.out.println(catalog);
        System.out.println(catalog.verifica_harta());
    }
}
