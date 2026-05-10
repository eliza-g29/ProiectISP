package proiect_ISP;

import java.util.ArrayList;

public class Client extends Utilizator {

    private ArrayList<Comanda> comenzi;

    public Client(String nume, String prenume, int ID_client, String email, String parola) {
        super(nume, prenume, ID_client, email, parola, Tip_utilizator.Client);
        this.comenzi = new ArrayList<Comanda>();
    }

    public ArrayList<Comanda> getComenzi() {
        return comenzi;
    }

    public void adauga_comanda(Comanda comanda) {
        comenzi.add(comanda);
    }
    
    public void raporteaza_defectiune(Comanda comanda, boolean raporteazaDefect, String descriere) {
        if (comanda == null) {
            System.out.println("Comanda nu exista.");
            return;
        }

        Echipament echipament = comanda.getEchipament();

        if (echipament == null) {
            System.out.println("Comanda nu are echipament asociat.");
            return;
        }

        System.out.println("\nIncheiere comanda ID=" + comanda.getID_comanda());

        if (!raporteazaDefect) {
            System.out.println("Clientul nu a raportat nicio defectiune.");
            return;
        }

        if (descriere == null || descriere.isEmpty()) {
            System.out.println("Raportul nu poate fi trimis fara descrierea defectiunii.");
            return;
        }

        echipament.actualizare_stare(Stare_echipament.defect);

        System.out.println("Defectiune raportata pentru echipamentul ID=" + echipament.getID_echipament());
        System.out.println("Descriere defectiune: " + descriere);
        System.out.println("Raport trimis catre administrator.");
    }

    @Override
    public String toString() {
        return "Client: ID=" + ID_client + ", nume=" + nume + " " + prenume + ", email=" + email + ".";
    }
}
