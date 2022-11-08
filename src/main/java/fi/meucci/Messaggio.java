package fi.meucci;

import java.util.ArrayList;

public class Messaggio 
{
    ArrayList<Biglietto> biglietti = new ArrayList<>();

    public Messaggio() {
    }

    public ArrayList<Biglietto> getBiglietti() {
        return biglietti;
    }
    
    public void setBiglietti(ArrayList<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

}
