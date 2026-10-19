package Logica.Partita.Gestione;

import Logica.Partita.Carte.Normali.Carta;

import java.io.Serializable;
import java.util.ArrayList;

public class Giocatore implements Serializable {

    private String nickname = null;
    private int vita;
    public ArrayList<Carta> carte = new ArrayList<>();
    public Tabellone tabellone;
    private boolean lock = false;
    private boolean haSparato = false;

    /**
     * Serve per sapere se il giocatore è lockato( è in prigione)
     * @return true se è in lockato. false se non lo è
     */
    public boolean isLock() {
        return lock;
    }

    /**
     * Serve per settare il giocatore lockato
     * @param lock true se è in prigione
     */
    public void setLock(boolean lock) {
        this.lock = lock;
    }

    /** True se in questo turno ha già giocato un Bang e non ha Volcanic o Willy the Kid. */
    public boolean haSparato() {
        return haSparato;
    }

    public void setHaSparato(boolean haSparato) {
        this.haSparato = haSparato;
    }

    /**
     * Serve per settare il nickname al giocatore
     * @param nickname nickname del giocatore corrente
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *Serve per ritornare il nickname del giocatore
     * @return il nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * serve per ritornare la vita
     * @return vita del giocatore corrente
     */
    public int getVita() {
        return vita;
    }

    /**
     * Serve per settare la vita del giocatore
     * @param vita vita del giocatore
     */
    public void setVita(int vita) {
        if (vita <= 5 && vita >= 0) {
            this.vita = vita;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
