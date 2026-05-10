package testare;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import proiect_ISP.Client;
import proiect_ISP.Comanda;
import proiect_ISP.Echipament;
import proiect_ISP.Stare_echipament;
import proiect_ISP.Tip_echipament;

class TestRaportareDefectiune {

    @Test
    void testFaraRaportareDefectiune() {
        Echipament e = new Echipament(
                1,
                Tip_echipament.trotineta,
                0.5f,
                Stare_echipament.functional,
                "Piata Unirii"
        );

        Client client = new Client(
                "Popescu",
                "Ion",
                101,
                "ion@mail.ro",
                "parola"
        );

        Comanda comanda = new Comanda(e, client.getID_client());

        client.raporteaza_defectiune(comanda, false, "");

        assertEquals(Stare_echipament.functional, e.getStare());
    }

    @Test
    void testCuRaportareDefectiune() {
        Echipament e = new Echipament(
                2,
                Tip_echipament.bicicleta,
                0.3f,
                Stare_echipament.functional,
                "Parcul Cismigiu"
        );

        Client client = new Client(
                "Ionescu",
                "Maria",
                102,
                "maria@mail.ro",
                "parola"
        );

        Comanda comanda = new Comanda(e, client.getID_client());

        client.raporteaza_defectiune(
                comanda,
                true,
                "Franele nu functioneaza corect."
        );

        assertEquals(Stare_echipament.defect, e.getStare());
    }

    @Test
    void testRaportareDefectiuneFaraDescriere() {
        Echipament e = new Echipament(
                3,
                Tip_echipament.masina,
                2.0f,
                Stare_echipament.functional,
                "Gara de Nord"
        );

        Client client = new Client(
                "Dumitru",
                "Andrei",
                103,
                "andrei@mail.ro",
                "parola"
        );

        Comanda comanda = new Comanda(e, client.getID_client());

        client.raporteaza_defectiune(comanda, true, "");

        assertEquals(Stare_echipament.functional, e.getStare());
    }
}