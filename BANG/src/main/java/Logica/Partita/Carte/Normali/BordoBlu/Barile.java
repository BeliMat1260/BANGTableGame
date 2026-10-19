package Logica.Partita.Carte.Normali.BordoBlu;

import Logica.Partita.Carte.Normali.Carta;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * La carta Barile con il bordo blu
 */
public class   Barile extends Estrazione {
    /**
     * Inizializza il barile
     * @param seme il seme del barile
     */
    public Barile(String seme) {
        super(seme);
    }

    /**
     * Applica l'effetto del Barile
     * @param gioco Il gioco
     * @param giocatore Il giocatore che perde vita
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
        Carta pescata = gioco.pescaDalMazzo();
        gioco.scarti.add(pescata);
        if (!pescata.eCuori()) {
            giocatore.setVita(giocatore.getVita() - 1);
        }
    }

    /**
     * Ritorna il barire
     * @return Il barile con la descrizione
     */
    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " Il Barile vi permette di pescare quando siete il bersaglio di un BANG!: se pescate una carta Cuore, siete Missati altrimenti non succede nulla.";
    }
}
