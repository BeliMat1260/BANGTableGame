package Logica.Partita.Carte.Normali.BordoMarrone;


import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe crea la carta Bang per il gioco
 */
public class Bang extends BordoMarrone {
    /**
     * Il costruttore della carta Bang
     * @param seme il seme della carta
     */
    public Bang(String seme) {
        super(seme);
    }

    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
    }

    /**
     * Applica l'effetto della carta Bang
     * @param spara Il giocatore che spara
     * @param bersaglio Il bersaglio del giocatore
     * @return false se il giocatore non può sparare,true se può
     */

    public boolean ApplicaEffetto(Giocatore spara, Giocatore bersaglio) {
        //controllare il personaggio e l' arma
        if (spara.tabellone.getArma().getSeme().equals("10P") || spara.tabellone.getArma().getSeme().equals("10F") || spara.tabellone.getPersonaggio().equals(Personaggio.WILLY_THE_KID)) {
            spara.setHaSparato(false);
        }
        return !spara.haSparato();
    }

    /**
     * Ritorna la descrizione della carta Bang
     * @return la descrizione della carta Bang
     */
    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " Questa carta é il metodo principale per ridurre i punti vita degli altri giocatori.";
    }
}
