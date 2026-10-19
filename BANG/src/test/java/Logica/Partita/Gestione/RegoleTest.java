package Logica.Partita.Gestione;

import Logica.Controllore.Controllore;
import Logica.Partita.Carte.Normali.BordoBlu.Mustang;
import Logica.Partita.Carte.Normali.BordoBlu.Schofield;
import Logica.Partita.Carte.Normali.BordoMarrone.Bang;
import Logica.Partita.Carte.Normali.Carta;
import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegoleTest {

    private Gioco tavolo() {
        Gioco gioco = new Gioco();
        for (int i = 0; i < 4; i++) {
            gioco.giocatori[i] = new Giocatore();
            gioco.giocatori[i].setNickname("p" + i);
            gioco.giocatori[i].setVita(4);
            gioco.giocatori[i].tabellone = new Tabellone(Ruolo.FUORILEGGE, Personaggio.BART_CASSIDY);
        }
        gioco.giocatori[0].tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.ROSE_DOOLAN);
        return gioco;
    }

    @Test
    void ilTurnoSaltaChiEMorto() {
        Gioco gioco = tavolo();
        gioco.giocatori[1].setVita(0);
        Controllore controllore = new Controllore(gioco);
        controllore.incrementaTurno();
        assertEquals(2, controllore.getTurno());
        gioco.giocatori[2].setVita(0);
        gioco.giocatori[3].setVita(0);
        controllore.incrementaTurno();
        assertEquals(0, controllore.getTurno());
    }

    @Test
    void sidKetchumScartaLeDueCarteIndicate() {
        Gioco gioco = tavolo();
        Controllore controllore = new Controllore(gioco);
        Giocatore giocatore = gioco.giocatori[0];
        giocatore.setVita(3);
        Bang prima = new Bang("1P");
        Bang seconda = new Bang("2P");
        Bang terza = new Bang("3P");
        giocatore.carte.add(prima);
        giocatore.carte.add(seconda);
        giocatore.carte.add(terza);

        assertTrue(controllore.sidKetchum(giocatore, 0, 2));
        assertEquals(1, giocatore.carte.size());
        assertEquals(seconda, giocatore.carte.getFirst());
        assertEquals(4, giocatore.getVita());
        assertFalse(controllore.sidKetchum(giocatore, 0, 0));
    }

    @Test
    void laPescataDagliScartiNonSvuotaIlMazzo() {
        Gioco gioco = tavolo();
        Controllore controllore = new Controllore(gioco);
        Bang nelMazzo = new Bang("4P");
        Bang scartata = new Bang("5C");
        gioco.mazzo.add(nelMazzo);
        gioco.scarti.add(scartata);

        Carta pescata = controllore.pesca(gioco.giocatori[0], true, false);
        assertEquals(scartata, pescata);
        assertEquals(nelMazzo, gioco.mazzo.getFirst());
        assertTrue(gioco.scarti.isEmpty());
    }

    @Test
    void ilMazzoVuotoVieneRimescolatoDagliScarti() {
        Gioco gioco = tavolo();
        Bang scartata = new Bang("6F");
        gioco.scarti.add(scartata);
        assertEquals(scartata, gioco.pescaDalMazzo());
        assertTrue(gioco.mazzo.isEmpty());
        assertTrue(gioco.scarti.isEmpty());
    }

    @Test
    void vultureSamSpostaLeCarteSenzaDuplicarle() {
        Gioco gioco = tavolo();
        Controllore controllore = new Controllore(gioco);
        Bang carta = new Bang("7Q");
        gioco.giocatori[1].setVita(0);
        gioco.giocatori[1].carte.add(carta);
        assertTrue(controllore.vultureSam(0));
        assertEquals(carta, gioco.giocatori[0].carte.getFirst());
        assertTrue(gioco.giocatori[1].carte.isEmpty());
        assertFalse(controllore.vultureSam(0));
    }

    @Test
    void laDistanzaConsideraArmaMustangERose() {
        Gioco gioco = tavolo();
        gioco.giocatori[0].tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.BART_CASSIDY);
        Controllore controllore = new Controllore(gioco);
        assertEquals(2, controllore.distanzaGiocatori(0).size());

        gioco.giocatori[1].tabellone.carteT.add(new Mustang("8C"));
        assertFalse(controllore.distanzaGiocatori(0).contains(gioco.giocatori[1]));
        assertTrue(controllore.distanzaGiocatori(0).contains(gioco.giocatori[3]));

        gioco.giocatori[0].tabellone.setArma(new Schofield("JF"));
        assertTrue(controllore.distanzaGiocatori(0).contains(gioco.giocatori[1]));
        assertTrue(controllore.distanzaGiocatori(0).contains(gioco.giocatori[2]));

        gioco.giocatori[2].tabellone.carteT.add(new Mustang("9C"));
        assertFalse(controllore.distanzaGiocatori(0).contains(gioco.giocatori[2]));

        gioco.giocatori[0].tabellone = new Tabellone(Ruolo.SCERIFFO, Personaggio.ROSE_DOOLAN);
        gioco.giocatori[0].tabellone.setArma(new Schofield("JF"));
        assertTrue(controllore.distanzaGiocatori(0).contains(gioco.giocatori[2]));
    }

    @Test
    void unBangNonMetteInPrigione() {
        Gioco gioco = tavolo();
        Controllore controllore = new Controllore(gioco);
        Bang bang = new Bang("1Q");
        gioco.giocatori[0].carte.add(bang);
        assertTrue(controllore.usacarta(gioco.giocatori[0], gioco.giocatori[1], bang, true));
        assertTrue(gioco.giocatori[0].haSparato());
        assertFalse(gioco.giocatori[0].isLock());
    }
}
