package proiect_ISP;

public class Utilizator {
    protected String nume;
    protected String prenume;
    protected int ID_client;
    protected String email;
    protected String parola;
    protected Tip_utilizator tip;

    public Utilizator(String nume, String prenume, int ID_client, String email, String parola, Tip_utilizator tip) {
        this.nume = nume;
        this.prenume = prenume;
        this.ID_client = ID_client;
        this.email = email;
        this.parola = parola;
        this.tip = tip;
    }

    public boolean autentificare() {
        return email != null && !email.isEmpty() && parola != null && !parola.isEmpty();
    }

    public Cont creare_cont(String nume, String prenume, String email, String parola, Tip_utilizator tip) {
        return new Cont(nume, prenume, email, parola, tip);
    }

    public String getNume() { 
    	return nume; 
    	}
    
    public void setNume(String nume) { 
    	this.nume = nume; 
    	}

    public String getPrenume() { 
    	return prenume; 
    	}
    
    public void setPrenume(String prenume) { 
    	this.prenume = prenume; 
    	}

    public int getID_client() { 
    	return ID_client; 
    	}
    
    public void setID_client(int ID_client) { 
    	this.ID_client = ID_client; 
    	}

    public String getEmail() { 
    	return email; 
    	}
    
    public void setEmail(String email) { 
    	this.email = email; 
    	}

    public String getParola() { 
    	return parola; 
    	}
    
    public void setParola(String parola) { 
    	this.parola = parola; 
    	}

    public Tip_utilizator getTip() { 
    	return tip; 
    	}
    
    public void setTip(Tip_utilizator tip) { 
    	this.tip = tip; 
    	}
}