package proiect_ISP;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class EchipamentTest {

    @AfterEach
    public void cleanup() throws Exception {
        Path p = Paths.get(System.getProperty("user.dir"), "raport_echipamente.txt");
        Files.deleteIfExists(p);
    }

    @Test
    public void testAdaugaEchipament() {
        Catalog catalog = new Catalog();
        Administrator admin = new Administrator("Ion", "Pop", 1, "ion@ex.com", "pass");

        admin.adauga_echipament(catalog, 100, Tip_echipament.bicicleta, 1.5f, Stare_echipament.functional, "Piata");

        Echipament e = catalog.gaseste_echipament(100);
        assertNotNull(e);
        assertEquals(Tip_echipament.bicicleta, e.getTip_echipament());
        assertEquals(1.5f, e.getPret_pe_min(), 0.0001f);
        assertEquals(Stare_echipament.functional, e.getStare());
        assertEquals("Piata", e.getLocatie());
    }

    @Test
    public void testStergeEchipament() {
        Catalog catalog = new Catalog();
        Administrator admin = new Administrator("Ion", "Pop", 1, "ion@ex.com", "pass");

        admin.adauga_echipament(catalog, 1, Tip_echipament.trotineta, 0.5f, Stare_echipament.functional, "Loc1");
        admin.adauga_echipament(catalog, 2, Tip_echipament.bicicleta, 0.3f, Stare_echipament.defect, "Loc2");

        assertEquals(2, catalog.getEchipamente().size());

        admin.sterge_echipament(catalog, 2);

        assertNull(catalog.gaseste_echipament(2));
        assertEquals(1, catalog.getEchipamente().size());
    }

    @Test
    public void testActualizareStareEchipament() {
        Catalog catalog = new Catalog();
        Administrator admin = new Administrator("Ion", "Pop", 1, "ion@ex.com", "pass");

        admin.adauga_echipament(catalog, 50, Tip_echipament.masina, 2.0f, Stare_echipament.defect, "Garaj");
        Echipament e = catalog.gaseste_echipament(50);
        assertNotNull(e);
        assertEquals(Stare_echipament.defect, e.getStare());

        admin.actualizare_stare_echipament(catalog, 50, Stare_echipament.functional);
        assertEquals(Stare_echipament.functional, e.getStare());
    }

    @Test
    public void testDescarcareRaport() throws Exception {
        Catalog catalog = new Catalog();
        Administrator admin = new Administrator("Ion", "Pop", 1, "ion@ex.com", "pass");

        admin.adauga_echipament(catalog, 10, Tip_echipament.bicicleta, 0.7f, Stare_echipament.functional, "A");
        admin.adauga_echipament(catalog, 11, Tip_echipament.trotineta, 0.4f, Stare_echipament.defect, "B");

        admin.descarcare_raport(catalog);

        Path p = Paths.get(System.getProperty("user.dir"), "raport_echipamente.txt");
        assertTrue(Files.exists(p), "Raport file should exist");

        String content = Files.readString(p);
        assertTrue(content.contains("Raport Echipamente"));
        assertTrue(content.contains("Total: " + catalog.getEchipamente().size()));
    }
}
