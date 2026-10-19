package Logica.Partita.Carte.Normali.BordoMarrone;


import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;

/**
 * Questa classe crea la carta Duello per il gioco
 */
public class Duello extends BordoMarrone{
    /**
     * Il costruttore della carta Duello
     * @param seme il seme della carta
     */
    public Duello(String seme) {
        super(seme);
    }

    /**
     * Applica l'effetto della carta Duello
     * @param gioco il gioco Bang
     * @param giocatore il giocatore che ha usato la carta
     */
    @Override
    public void ApplicaEffetto(Gioco gioco, Giocatore giocatore) {
    }
    public boolean ApplicaEffetto(Giocatore spara, Giocatore bersaglio, Gioco gioco) {
        boolean flag = false, flag2 = false;
        do {
            flag = false;
            flag2 = false;
            for (int i = 0; i < bersaglio.carte.size(); i++) {
                if (bersaglio.carte.get(i).getClass().getSimpleName().equals("Bang")) {
                    gioco.scarti.add(bersaglio.carte.remove(i));
                    flag = true;
                    break;
                }
            }
            for (int i = 0; i < spara.carte.size(); i++) {
                if (spara.carte.get(i).getClass().getSimpleName().equals("Bang")) {
                    gioco.scarti.add(spara.carte.remove(i));
                    flag2 = true;
                    break;
                }
            }
        } while (flag && flag2);
        if (!flag) {
            if (bersaglio.tabellone != null && bersaglio.tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                bersaglio.carte.add(gioco.pescaDalMazzo());
            }
            bersaglio.setVita(bersaglio.getVita()-1);
        } else {
            if (spara.tabellone != null && spara.tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                spara.carte.add(gioco.pescaDalMazzo());
            }
            spara.setVita(spara.getVita()-1);
        }
        return flag;
    }

    /**
     * Ritorna la descrizione della carta Duello
     * @return la descrizione della carta
     */
    public String toString() {
        return " " +  getClass().getSimpleName() + " " + getSeme() + " Il giocatore sfidato può usare una carta BANG. Se lo fa, voi potete scartare una carta BANG! e così via: il primo giocatore che non scarta una carta BANG! perde un punto vita";
    }
}
