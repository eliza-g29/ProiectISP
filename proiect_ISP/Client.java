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

    @Override
    public String toString() {
        return "Client: ID=" + ID_client + ", nume=" + nume + " " + prenume + ", email=" + email + ".";
    }
}