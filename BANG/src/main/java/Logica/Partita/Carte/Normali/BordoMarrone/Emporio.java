package Logica.Partita.Carte.Normali.BordoMarrone;

import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe crea la carta Emporio per il gioco
 */
public class Emporio extends BordoMarrone{
    /**
     * Il costruttore della carta Emporio
     * @param seme il seme della carta
     */
    public Emporio(String seme) {
        super(seme);
    }

    /**
     * Applica l'effetto della carta Emporio
     * @param gioco il gioco Bang
     * @param giocatore il giocatore che ha usato la carta
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
        for (int i = 0; i < gioco.giocatori.length; i++) {
            if (gioco.giocatori[i] != null && gioco.giocatori[i].getVita() > 0) {
                gioco.giocatori[i].carte.add(gioco.pescaDalMazzo());
            }
        }
    }

    /**
     * Ritorna la descrizione della carta Emporio
     * @return la descrizione della carta
     */
    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " Girare a faccia in su tante carte del mazzo quanti sono i giocatori ancora in gioco e procedendo in senso orario, ogni giocatore sceglie una di queste carte e la mette in mano.";
    }
}
