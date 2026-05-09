package proiect_ISP;

import java.util.ArrayList;

public class Catalog {

    private ArrayList<Echipament> echipamente;

    public Catalog() {
        this.echipamente = new ArrayList<Echipament>();
    }

    public ArrayList<Echipament> getEchipamente() {
        return echipamente;
    }

    public void adauga_echipament(int ID_echipament, Tip_echipament tip_echipament, float pret_pe_min, Stare_echipament stare, String locatie) {
        Echipament e = new Echipament(ID_echipament, tip_echipament, pret_pe_min, stare, locatie);
        echipamente.add(e);
    }

    public void sterge_echipament(int ID_echipament) {
        echipamente.removeIf(e -> e.getID_echipament() == ID_echipament);
    }

    public Echipament gaseste_echipament(int ID_echipament) {
        for (Echipament e : echipamente) {
            if (e.getID_echipament() == ID_echipament) {
                return e;
            }
        }
        return null;
    }

    public boolean exista_echipament_functional() {
        for (Echipament e : echipamente) {
            if (e.getStare() == Stare_echipament.functional) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Echipament> echipamente_functionale_la_locatie(String locatie) {
        ArrayList<Echipament> rezultat = new ArrayList<>();
        for (Echipament e : echipamente) {
            if (e.getStare() == Stare_echipament.functional && e.getLocatie().equalsIgnoreCase(locatie)) {
                rezultat.add(e);
            }
        }
        return rezultat;
    }

    public String verifica_harta() {
        StringBuilder sb = new StringBuilder("Echipamente disponibile:\n");
        for (Echipament e : echipamente) {
            if (e.getStare() == Stare_echipament.functional) {
                sb.append("  - ID=").append(e.getID_echipament())
                  .append(" | ").append(e.getTip_echipament())
                  .append(" @ ").append(e.getLocatie())
                  .append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Catalog: numar_echipamente=" + echipamente.size() + ".";
    }
}