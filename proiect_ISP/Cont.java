package proiect_ISP;

public class Cont {
    private String nume;
    private String prenume;
    private String email;
    private String parola;
    private Tip_utilizator tip;

    public Cont(String nume, String prenume, String email, String parola, Tip_utilizator tip) {
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
        this.parola = parola;
        this.tip = tip;
    }

    public String getNume() { 
    	return nume; 
    	}
    public String getPrenume() { 
    	return prenume; 
    	}
    public String getEmail() { 
    	return email; 
    	}
    public String getParola() { 
    	return parola; 
    	}
    public Tip_utilizator getTip() { 
    	return tip; 
    	}

    @Override
    public String toString() {
        return "Cont: nume='" + nume + "', prenume='" + prenume + "', email='" + email + "', tip=" + tip + ".";
    }
}