package Logica.Partita.Carte.Normali.BordoBlu;

import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;
import Logica.Partita.Gestione.Tabellone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MustangTest {

    @Test
    void applicaEffetto() {
        Gioco gioco = new Gioco();
        for (int i = 0; i < 4; i++) {
            gioco.giocatori[i] = new Giocatore();
            gioco.giocatori[i].tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.BART_CASSIDY);
            gioco.giocatori[i].setVita(4);
        }
        Mustang mustang = new Mustang("0");
        mustang.ApplicaEffetto(gioco,gioco.giocatori[0]);
        assertEquals(1, gioco.giocatori[0].tabellone.carteT.size());
        assertEquals(mustang,gioco.giocatori[0].tabellone.carteT.getLast());
        assertEquals(1, gioco.giocatori[0].tabellone.getArma().getDistanza());
        for (int i = 1; i < 4; i++) {
            assertEquals(1, gioco.giocatori[i].tabellone.getArma().getDistanza());
        }
    }
}