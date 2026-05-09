package testare;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import proiect_ISP.*;

class PlasareComandaTest {

    @Test
    void testPlasareComanda() {

        // Preconditie: utilizatorul trebuie sa fie autentificat
        Client client = new Client("Nedelcu", "Bianca", 0, "nedelcubianca@gmail.ro", "parola123");
        assertTrue(client.autentificare());
        // Client fara email -> autentificare esuata
        Client clientInvalid = new Client("Ionescu", "Mihai", 999, "", "parola");
        assertFalse(clientInvalid.autentificare());

        // ETAPA 1: Solicita catalog
        Catalog catalog = new Catalog();
        Administrator admin = new Administrator("Rosu", "Maria", 1, "mariarosu@gmail.com", "admin");
        admin.adauga_echipament(catalog, 1, Tip_echipament.trotineta, 0.5f, Stare_echipament.functional, "Piata Romana");
        admin.adauga_echipament(catalog, 2, Tip_echipament.bicicleta, 0.3f, Stare_echipament.defect, "AFI Cotroceni");
        admin.adauga_echipament(catalog, 3, Tip_echipament.masina, 1.0f, Stare_echipament.functional, "Drumul Sarii");

        assertEquals(3, catalog.getEchipamente().size());

        // ETAPA 2: Verificare harta
        // Decizie: exista cel putin un vehicul functional

        assertTrue(catalog.exista_echipament_functional()); // catalog cu echipamente functionale -> true
        Catalog catalogGol = new Catalog();
        assertFalse(catalogGol.exista_echipament_functional()); // catalog gol -> false (limita inferioara: 0 elemente)

        Catalog catalogDefecte = new Catalog(); // toate echipamentele defecte -> false (bucla parcurge tot fara sa gaseasca)
        catalogDefecte.adauga_echipament(10, Tip_echipament.trotineta, 0.5f, Stare_echipament.defect, "Centrul Vechi");
        catalogDefecte.adauga_echipament(11, Tip_echipament.bicicleta, 0.3f, Stare_echipament.defect, "Centrul Vechi");
        assertFalse(catalogDefecte.exista_echipament_functional());

        // Filtrare dupa locatie
        assertEquals(1, catalog.echipamente_functionale_la_locatie("Drumul Sarii").size()); // locatie cu 1 echipament functional -> size 1
        assertEquals(0, catalog.echipamente_functionale_la_locatie("Piata Universitatii").size()); // locatie inexistenta -> size 0
        assertEquals(1, catalog.echipamente_functionale_la_locatie("piata romana").size()); // case insensitive -> gaseste echipamentul functional
        assertEquals(0, catalog.echipamente_functionale_la_locatie("AFI Cotroceni").size()); // locatie cu echipament defect -> size 0

        String harta = catalog.verifica_harta();  // Verificare harta -> returneaza string cu echipamente functionale
        assertTrue(harta.contains("Piata Romana"));
        assertTrue(harta.contains("Drumul Sarii"));
        assertFalse(harta.contains("AFI Cotroceni")); // defect, nu apare

        // Administratorul actualizeaza bicicleta de la AFI Cotroceni: defect -> functional
        admin.actualizare_stare_echipament(catalog, 2, Stare_echipament.functional);
        Echipament eActualizat = catalog.getEchipamente().get(1);
        assertEquals(Stare_echipament.functional, eActualizat.getStare());

        // Dupa actualizare, AFI Cotroceni are acum 1 echipament functional
        assertEquals(1, catalog.echipamente_functionale_la_locatie("AFI Cotroceni").size());

        // Actualizare pe echipament inexistent -> nu arunca exceptie, afiseaza eroare
        assertDoesNotThrow(() -> admin.actualizare_stare_echipament(catalog, 999, Stare_echipament.defect));

        // ETAPA 3: Selectare vehicul
        // Clientul selecteaza din echipamentele functionale de la o anumita locatie
        // selectare din lista filtrata la "Piata Romana" -> 1 echipament functional
        java.util.ArrayList<Echipament> e_disponibile_PiataRomana = catalog.echipamente_functionale_la_locatie("Piata Romana");
        assertEquals(1, e_disponibile_PiataRomana.size());
        Echipament e1 = e_disponibile_PiataRomana.get(0);

        // selectare din lista filtrata la "AFI Cotroceni" -> 1 echipament functional (dupa actualizare)
        java.util.ArrayList<Echipament> e_disponibile_AFI = catalog.echipamente_functionale_la_locatie("AFI Cotroceni");
        assertEquals(1, e_disponibile_AFI.size());
        Echipament e2 = e_disponibile_AFI.get(0);

        // selectare din lista filtrata la "Drumul Sarii" -> 1 echipament functional
        java.util.ArrayList<Echipament> e_disponibile_DrumulSarii = catalog.echipamente_functionale_la_locatie("Drumul Sarii");
        assertEquals(1, e_disponibile_DrumulSarii.size());
        Echipament e3 = e_disponibile_DrumulSarii.get(0);

        // ETAPA 4: Confirmare detalii + Plasare comanda
        // Clientul confirma echipamentul selectat si plaseaza comanda
        Comanda comanda1 = new Comanda(e1, client.getID_client()); 
        int idComanda = comanda1.plasare_comanda(comanda1);
        assertTrue(idComanda > 0); // echipament functional -> comanda plasata cu succes, ID > 0

        Comanda comanda2 = new Comanda(e2, client.getID_client());
        int idComanda2 = comanda2.plasare_comanda(comanda2);
        assertTrue(idComanda2 > 0); // Plasare comanda pe bicicleta reparata -> succes

        admin.actualizare_stare_echipament(catalog, 2, Stare_echipament.defect); // Stricam bicicleta inapoi pentru a testa cazul defect
        assertEquals(Stare_echipament.defect, e2.getStare());

        // echipament defect -> IllegalStateException
        Comanda comanda3 = new Comanda(e2, client.getID_client());
        assertThrows(IllegalStateException.class, () -> comanda3.plasare_comanda(comanda3));

        Comanda comanda4 = new Comanda(null, client.getID_client()); // echipament null -> IllegalStateException
        assertThrows(IllegalStateException.class, () -> comanda4.plasare_comanda(comanda4));

        // ETAPA 5: Efectuare plata
        comanda1.efectuare_plata(30, 0.5f);
        assertEquals(15.0f, comanda1.getPret_final(), 0.01f);
        assertEquals(30, comanda1.getTimp_utilizare());

        Comanda c1 = new Comanda(e3, client.getID_client());
        c1.efectuare_plata(60, 1.0f);
        assertEquals(60.0f, c1.getPret_final(), 0.01f);

        Comanda c2 = new Comanda(e1, client.getID_client());
        c2.efectuare_plata(1, 0.5f); // limita inferioara valida: 1 min * 0.5 = 0.5 RON
        assertEquals(0.5f, c2.getPret_final(), 0.01f);

        Comanda c3 = new Comanda(e1, client.getID_client());
        assertThrows(IllegalArgumentException.class, () -> c3.efectuare_plata(0, 0.5f)); // timp = 0 -> IllegalArgumentException

        Comanda c4 = new Comanda(e1, client.getID_client());
        assertThrows(IllegalArgumentException.class, () -> c4.efectuare_plata(-5, 0.5f)); // timp negativ -> IllegalArgumentException

        Comanda c5 = new Comanda(e1, client.getID_client());
        assertThrows(IllegalArgumentException.class, () -> c5.efectuare_plata(30, 0.0f)); // pret = 0 -> IllegalArgumentException

        Comanda c6 = new Comanda(e1, client.getID_client());
        assertThrows(IllegalArgumentException.class, () -> c6.efectuare_plata(30, -1.0f)); // pret negativ -> IllegalArgumentException

        Comanda c7 = new Comanda(e2, client.getID_client());
        assertThrows(IllegalStateException.class, () -> c7.efectuare_plata(30, 0.3f)); // echipament defect -> IllegalStateException la plata

        // ETAPA 6: Finalizare comanda : comanda se adauga in lista clientului
        assertEquals(0, client.getComenzi().size());
        client.adauga_comanda(comanda1); // adaugare comanda -> lista clientului creste
        assertEquals(1, client.getComenzi().size());

        client.adauga_comanda(c2); // adaugare a doua comanda -> lista creste la 2
        assertEquals(2, client.getComenzi().size());

        assertTrue(client.getComenzi().contains(comanda1)); // verificare ca comenzile sunt cele corecte
        assertTrue(client.getComenzi().contains(c2)); // verificare ca comenzile sunt cele corecte

        assertDoesNotThrow(() -> admin.vizualizare_comenzi(client)); // administrator vizualizeaza comenzile clientului (asociere Admin-Client)

        Client clientNou = new Client("Georgescu", "Andrei", 103, "andrei@mail.ro", "pass789");
        assertEquals(0, clientNou.getComenzi().size());
        assertDoesNotThrow(() -> admin.vizualizare_comenzi(clientNou)); // administrator vizualizeaza comenzile unui client fara comenzi
    }
}