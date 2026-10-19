package Logica.Partita.Carte.Normali;

import java.io.Serializable;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe inizializza le carte
 */
public abstract class Carta implements Serializable {

    protected String seme;//Esempio 1C(cuori),2Q(quadri),5P(picche),QF(fiori)

    /**
     * Il costruttore delle Carte
     * @param seme il seme delle carte
     */
    public Carta(String seme) {
        this.seme = seme;
    }

    public abstract void ApplicaEffetto(Gioco gioco, Giocatore giocatore);

    public boolean ApplicaEffetto(Giocatore spara, Giocatore bersaglio) {
        return false;
    }
    public boolean ApplicaEffetto(Giocatore spara, Giocatore bersaglio, Gioco gioco) {
        return false;
    }


    public String getSeme() {
        return seme;
    }

    /** L'ultima lettera del seme è il seme vero: C cuori, Q quadri, P picche, F fiori. */
    public boolean eCuori() {
        return seme != null && !seme.isEmpty() && seme.charAt(seme.length() - 1) == 'C';
    }

    public boolean eRosso() {
        if (seme == null || seme.isEmpty()) {
            return false;
        }
        char suit = seme.charAt(seme.length() - 1);
        return suit == 'C' || suit == 'Q';
    }

    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " mettere descrizione";
    }

}
