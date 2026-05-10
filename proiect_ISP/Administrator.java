package proiect_ISP;

public class Administrator extends Utilizator {

    public Administrator(String nume, String prenume, int ID_client, String email, String parola) {
        super(nume, prenume, ID_client, email, parola, Tip_utilizator.Administrator_echipamente);
    }

    public void adauga_echipament(Catalog catalog, int ID_echipament, Tip_echipament tip, float pret_pe_min, Stare_echipament stare, String locatie) {
        catalog.adauga_echipament(ID_echipament, tip, pret_pe_min, stare, locatie);
        System.out.println("Echipament ID=" + ID_echipament + " adaugat in catalog.");
    }

    public void sterge_echipament(Catalog catalog, int ID_echipament) {
        catalog.sterge_echipament(ID_echipament);
        System.out.println("Echipament ID=" + ID_echipament + " sters din catalog.");
    }

    public void actualizare_stare_echipament(Catalog catalog, int ID_echipament, Stare_echipament stare_noua) {
        Echipament e = catalog.gaseste_echipament(ID_echipament);
        if (e == null) {
            System.err.println("Echipamentul cu ID=" + ID_echipament + " nu a fost gasit.");
            return;
        }
        e.actualizare_stare(stare_noua);
        System.out.println("Stare echipament ID=" + ID_echipament + " actualizata la: " + stare_noua);
    }

    public void descarcare_raport(Catalog catalog) {
        String numeFisier = System.getProperty("user.dir") + "/raport_echipamente.txt";

        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(numeFisier));
            writer.println("Raport Echipamente");
            writer.println("Administrator: " + nume + " " + prenume);
            writer.println("Data raport: " + new java.util.Date());
            writer.println("Total: " + catalog.getEchipamente().size() + " echipamente");
            writer.println("=".repeat(40));
            for (Echipament e : catalog.getEchipamente()) {
                writer.println("ID echipament: " + e.getID_echipament());
                writer.println("Tip: " + e.getTip_echipament());
                writer.println("Stare: " + e.getStare());
                writer.println("Locatie: " + e.getLocatie());
                writer.println("Pret/min: " + e.getPret_pe_min());
                writer.println("-".repeat(40));
            }
            writer.close();
            System.out.println("Raport salvat in fisierul: " + numeFisier);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Eroare la crearea fisierului: " + e.getMessage());
        }
    }

    public void vizualizare_comenzi(Client client) {
        if (client.getComenzi().isEmpty()) {
            System.out.println("Clientul " + client.getNume() + " " + client.getPrenume() + " nu are comenzi.");
            return;
        }
        System.out.println("Comenzi client " + client.getNume() + " " + client.getPrenume() + ":");
        for (Comanda c : client.getComenzi()) {
            System.out.println(c);
        }
    }

    @Override
    public String toString() {
        return "Administrator: ID=" + ID_client + ", nume=" + nume + " " + prenume + ", email=" + email + ".";
    }
}
