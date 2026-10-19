package Logica.Partita.Carte.Normali.BordoMarrone;

import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe crea la  carta Diligenza per questo gioco
 */
public class Diligenza extends BordoMarrone{
    /**
     * Il costruttore della carta Diligenza
     * @param seme il seme della carta
     */
    public Diligenza(String seme) {
        super(seme);
    }

    /**
     * Applica l'effetto della carta Diligenza
     * @param gioco il gioco Bang
     * @param giocatore il giocatore che ha usato la carta
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
        for (int i = 0; i < 2; i++) {
            giocatore.carte.add(gioco.pescaDalMazzo());
        }
    }

    /**
     * Ritorna la descrizione della carta Diligenza
     * @return la descrizione della carta
     */
    @Override
    public String toString() {
        return " Diligenza " + getSeme() + " Pesca due carte dalla cima del mazzo";
    }
}
