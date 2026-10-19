package Logica.Partita.Gestione;

import Logica.Partita.Carte.Normali.Carta;
import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Gioco implements Serializable {

    public Giocatore[] giocatori = new Giocatore[4];
    public ArrayList<Carta> scarti = new ArrayList<>();
    public ArrayList<Carta> mazzo = new ArrayList<>();
    public ArrayList<Ruolo> ruoli = new ArrayList<>();
    public ArrayList<Personaggio> personaggi = new ArrayList<>();
    public int turno = 0;

    /**
     * Pesca la prima carta del mazzo. Se il mazzo è vuoto, rimescola gli scarti.
     */
    public Carta pescaDalMazzo() {
        if (mazzo.isEmpty()) {
            if (scarti.isEmpty()) {
                throw new NoSuchElementException("Il mazzo e gli scarti sono vuoti");
            }
            mazzo.addAll(scarti);
            scarti.clear();
            Collections.shuffle(mazzo);
        }
        return mazzo.removeFirst();
    }

}
