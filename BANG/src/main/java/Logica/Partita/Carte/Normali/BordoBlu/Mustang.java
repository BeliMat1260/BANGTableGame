package Logica.Partita.Carte.Normali.BordoBlu;

import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe crea la carta Mustang per il gioco
 */
public class Mustang extends Permanenti{
    /**
     * Il costruttore di Mustanf
     * @param seme il seme della carta
     */
    public Mustang(String seme) {
        super(seme);
    }

    /**
     * Apllica l'effetto della carta
     * @param gioco il gioco Bang
     * @param giocatore il giocatore che ha usato la carta
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
        if (!giocatore.tabellone.carteT.contains(this)) {
            giocatore.tabellone.carteT.add(this);
        }
    }

    /**
     * Ritorna la descrizione della carta
     * @return la descrizione della carta
     */
    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " Quando si usa laMustang, la distanza tra gli altri giocatori e l'utente aumenta di 1. Tuttavia, l'utente continua a vedere gli altri giocatori alla distanza normale.";
    }
}
