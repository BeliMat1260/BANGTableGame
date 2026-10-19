package Logica.Partita.Carte.Normali.BordoMarrone;

import Logica.Partita.Gestione.Gioco;
import Logica.Partita.Gestione.Giocatore;

/**
 * Questa classe crea la carta Weelsfargo per il gioco
 */
public class Weelsfargo extends BordoMarrone {
    /**
     * Il costruttore della carta Weelsfargo
     * @param seme il seme della carta
     */
    public Weelsfargo(String seme) {
        super(seme);
    }

    /**
     * Applica l'effetto della carta Weelsfargo
     * @param gioco il gioco Bang
     * @param giocatore Il giocatore che ha usato la carta
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
        for (int i = 0; i < 3; i++) {
            giocatore.carte.add(gioco.pescaDalMazzo());
        }
    }

    /**
     * Ritorna la descrizione della carta Weelsfargo
     * @return la descrizione della carta
     */
    @Override
    public String toString() {
        return " Weels Fargo " + getSeme() + " Pesca tre carte dalla cima del mazzo.";
    }
}
