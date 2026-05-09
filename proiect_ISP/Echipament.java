package proiect_ISP;

public class Echipament {
    protected int ID_echipament;
    protected Tip_echipament tip_echipament;
    protected float pret_pe_min;
    protected Stare_echipament stare;
    protected String locatie;

    public Echipament(int ID_echipament, Tip_echipament tip_echipament, float pret_pe_min, Stare_echipament stare, String locatie) {
        this.ID_echipament = ID_echipament;
        this.tip_echipament = tip_echipament;
        this.pret_pe_min = pret_pe_min;
        this.stare = stare;
        this.locatie = locatie;
    }

    public void actualizare_stare(Stare_echipament stare_noua) {
        this.stare = stare_noua;
    }

    public void afisare_stare() {
        System.out.println("Echipament ID=" + ID_echipament + " | Tip=" + tip_echipament + " | Stare=" + stare + " | Locatie=" + locatie + " | Pret/min=" + pret_pe_min);
    }

    public int getID_echipament() { 
    	return ID_echipament; 
    	}
    
    public void setID_echipament(int ID_echipament) { 
    	this.ID_echipament = ID_echipament; 
    	}

    public Tip_echipament getTip_echipament() { 
    	return tip_echipament; 
    	}
    
    public void setTip_echipament(Tip_echipament tip_echipament) { 
    	this.tip_echipament = tip_echipament; 
    	}

    public float getPret_pe_min() { 
    	return pret_pe_min; 
    	}
    
    public void setPret_pe_min(float pret_pe_min) { 
    	this.pret_pe_min = pret_pe_min; 
    	}

    public Stare_echipament getStare() { 
    	return stare; 
    	}
    
    public void setStare(Stare_echipament stare) { 
    	this.stare = stare; 
    	}

    public String getLocatie() { 
    	return locatie; 
    	}
    public void setLocatie(String locatie) { 
    	this.locatie = locatie; 
    	}
}