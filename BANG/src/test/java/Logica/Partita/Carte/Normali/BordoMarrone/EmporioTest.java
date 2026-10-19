package Logica.Partita.Carte.Normali.BordoMarrone;

import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmporioTest {

    @Test
    void applicaEffetto() {
        Gioco gioco = new Gioco();
        for (int i = 0; i < 4; i++) {
            gioco.giocatori[i] = new Giocatore();
            gioco.giocatori[i].setVita(4);
        }
        Bang bang = new Bang("1");
        Bang bang1 = new Bang("2");
        Bang bang2 = new Bang("3");
        Bang bang3 = new Bang("4");
        gioco.mazzo.add(bang);
        gioco.mazzo.add(bang1);
        gioco.mazzo.add(bang2);
        gioco.mazzo.add(bang3);
        new Emporio("0").ApplicaEffetto(gioco,gioco.giocatori[0]);
        assertEquals(bang,gioco.giocatori[0].carte.getLast());
        assertEquals(bang1,gioco.giocatori[1].carte.getLast());
        assertEquals(bang2,gioco.giocatori[2].carte.getLast());
        assertEquals(bang3,gioco.giocatori[3].carte.getLast());
        assertTrue(gioco.mazzo.isEmpty());
    }
}