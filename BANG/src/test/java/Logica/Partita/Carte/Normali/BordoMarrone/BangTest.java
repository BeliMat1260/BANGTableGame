package Logica.Partita.Carte.Normali.BordoMarrone;

import Logica.Partita.Carte.Normali.BordoBlu.Revolver;
import Logica.Partita.Carte.Normali.BordoBlu.Schofield;
import Logica.Partita.Carte.Normali.BordoBlu.Volcanic;
import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Tabellone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BangTest {

    @Test
    void applicaEffetto() {
        /*if (spara.tabellone.getArma().getSeme().equals("10P") || spara.tabellone.getArma().getSeme().equals("10F") || spara.tabellone.getPersonaggio().equals(Personaggio.WILLY_THE_KID)) {
            spara.setLock(false);
        }
        return !spara.isLock();*/
        Giocatore spara = new Giocatore();
        Giocatore bersaglio = new Giocatore();
        Bang bang = new Bang("0");
        spara.tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.BART_CASSIDY);
        assertFalse(spara.haSparato());
        boolean flag = bang.ApplicaEffetto(spara, bersaglio);
        assertTrue(flag);
        spara.setHaSparato(true);
        flag = bang.ApplicaEffetto(spara, bersaglio);
        assertFalse(flag);
        spara.tabellone.setArma(new Volcanic("10P"));
        flag = bang.ApplicaEffetto(spara, bersaglio);
        assertTrue(flag);
        spara.setHaSparato(true);
        spara.tabellone.setArma(new Volcanic("10F"));
        flag = bang.ApplicaEffetto(spara, bersaglio);
        assertTrue(flag);
        spara.tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.WILLY_THE_KID);
        spara.setHaSparato(true);
        spara.tabellone.setArma(new Volcanic("10F"));
        flag = bang.ApplicaEffetto(spara, bersaglio);
        assertTrue(flag);
    }
}