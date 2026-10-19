package Logica.Controllore;

import CLI.GiocoCLI;
import Logica.Partita.Carte.Normali.BordoBlu.*;
import Logica.Partita.Carte.Normali.BordoMarrone.*;
import Logica.Partita.Carte.Normali.Carta;
import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;
import Logica.Partita.Gestione.Giocatore;
import Logica.Partita.Gestione.Gioco;
import Logica.Partita.Gestione.Tabellone;
import com.example.bang.Scenes.MenuScene;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Questa classe serve per controllare se il gioco funziona correttamente
 */
public class Controllore {

    private Gioco gioco;
    private MenuScene menuScene;
    private final File file = new File("Bang.ser");

    public Controllore(Gioco gioco) {
        this.gioco = gioco;
    }

    public Controllore() {

        //Thread per startare la grafica

        new Thread(() -> Application.launch(MenuScene.class)).start();

        menuScene = MenuScene.waitMenuScene();
        menuScene = MenuScene.waitButtonActions();

        if (!menuScene.getSceltaP()) {
            loadGame();
        } else {
            gioco = new Gioco();
            newGame();
        }

        if (menuScene.getSceltaI()) {
            menuScene.setControllore(this);
            Platform.runLater(() -> menuScene.changeScene());
            //Gui
        } else {
            //Cli
            new GiocoCLI(this);
        }
    }

    /**
     * Serve a inizializzare le carte nel mazzo, i ruoli e i personaggi
     */
    private void newGame() {

        gioco.mazzo.add(new Barile("QP"));
        gioco.mazzo.add(new Barile("KP"));
        gioco.mazzo.add(new Mirino("1P"));
        gioco.mazzo.add(new Mustang("8C"));
        gioco.mazzo.add(new Mustang("9C"));
        gioco.mazzo.add(new Prigione("JP"));
        gioco.mazzo.add(new Prigione("4C"));
        gioco.mazzo.add(new Prigione("10P"));
        gioco.mazzo.add(new Remington("KF"));
        gioco.mazzo.add(new RevCarabine("1F"));
        gioco.mazzo.add(new Schofield("JF"));
        gioco.mazzo.add(new Schofield("QF"));
        gioco.mazzo.add(new Schofield("KP"));
        gioco.mazzo.add(new Volcanic("10P"));
        gioco.mazzo.add(new Volcanic("10F"));
        gioco.mazzo.add(new Whinchester("8P"));
        gioco.mazzo.add(new Bang("1P"));
        gioco.mazzo.add(new Bang("1Q"));
        gioco.mazzo.add(new Bang("2Q"));
        gioco.mazzo.add(new Bang("3Q"));
        gioco.mazzo.add(new Bang("4Q"));
        gioco.mazzo.add(new Bang("5Q"));
        gioco.mazzo.add(new Bang("6Q"));
        gioco.mazzo.add(new Bang("7Q"));
        gioco.mazzo.add(new Bang("8Q"));
        gioco.mazzo.add(new Bang("9Q"));
        gioco.mazzo.add(new Bang("10Q"));
        gioco.mazzo.add(new Bang("JQ"));
        gioco.mazzo.add(new Bang("QQ"));
        gioco.mazzo.add(new Bang("KQ"));
        gioco.mazzo.add(new Bang("2F"));
        gioco.mazzo.add(new Bang("3F"));
        gioco.mazzo.add(new Bang("4F"));
        gioco.mazzo.add(new Bang("5F"));
        gioco.mazzo.add(new Bang("6F"));
        gioco.mazzo.add(new Bang("7F"));
        gioco.mazzo.add(new Bang("8F"));
        gioco.mazzo.add(new Bang("9F"));
        gioco.mazzo.add(new Bang("QC"));
        gioco.mazzo.add(new Bang("KC"));
        gioco.mazzo.add(new Bang("1C"));
        gioco.mazzo.add(new Birra("6C"));
        gioco.mazzo.add(new Birra("7C"));
        gioco.mazzo.add(new Birra("8C"));
        gioco.mazzo.add(new Birra("9C"));
        gioco.mazzo.add(new Birra("10C"));
        gioco.mazzo.add(new Birra("JC"));
        gioco.mazzo.add(new Catbalou("KC"));
        gioco.mazzo.add(new Catbalou("9Q"));
        gioco.mazzo.add(new Catbalou("10Q"));
        gioco.mazzo.add(new Catbalou("JQ"));
        gioco.mazzo.add(new Diligenza("9P"));
        gioco.mazzo.add(new Diligenza("9P"));
        gioco.mazzo.add(new Duello("QQ"));
        gioco.mazzo.add(new Duello("JP"));
        gioco.mazzo.add(new Duello("8F"));
        gioco.mazzo.add(new Emporio("9F"));
        gioco.mazzo.add(new Emporio("QP"));
        gioco.mazzo.add(new Gatling("10C"));
        gioco.mazzo.add(new Indiani("KQ"));
        gioco.mazzo.add(new Indiani("1Q"));
        gioco.mazzo.add(new Saloon("5Q"));
        gioco.mazzo.add(new Weelsfargo("3C"));
        gioco.mazzo.add(new Mancato("10F"));
        gioco.mazzo.add(new Mancato("JF"));
        gioco.mazzo.add(new Mancato("QF"));
        gioco.mazzo.add(new Mancato("KF"));
        gioco.mazzo.add(new Mancato("2P"));
        gioco.mazzo.add(new Mancato("3P"));
        gioco.mazzo.add(new Mancato("4P"));
        gioco.mazzo.add(new Mancato("5P"));
        gioco.mazzo.add(new Mancato("6P"));
        gioco.mazzo.add(new Panico("7P"));
        gioco.mazzo.add(new Panico("JC"));
        gioco.mazzo.add(new Panico("1C"));
        gioco.mazzo.add(new Panico("8Q"));
        Collections.shuffle(gioco.mazzo);

        gioco.ruoli.add(Ruolo.FUORILEGGE);
        gioco.ruoli.add(Ruolo.RINNEGATO);
        gioco.ruoli.add(Ruolo.FUORILEGGE);
        gioco.ruoli.add(Ruolo.SCERIFFO);
        Collections.shuffle(gioco.ruoli);

        gioco.personaggi.add(Personaggio.PEDRO_RAMIREZ);
        gioco.personaggi.add(Personaggio.LUCKY_DUKE);
        gioco.personaggi.add(Personaggio.SUZY_LAFAYETTE);
        gioco.personaggi.add(Personaggio.BART_CASSIDY);
        gioco.personaggi.add(Personaggio.BLACK_JACK);
        gioco.personaggi.add(Personaggio.EL_GRINGO);
        gioco.personaggi.add(Personaggio.JESSE_JONES);
        gioco.personaggi.add(Personaggio.JOURDONNAIS);
        gioco.personaggi.add(Personaggio.KIT_CARLSON);
        gioco.personaggi.add(Personaggio.PAUL_REGRET);
        gioco.personaggi.add(Personaggio.ROSE_DOOLAN);
        gioco.personaggi.add(Personaggio.SID_KETCHUM);
        gioco.personaggi.add(Personaggio.VULTURE_SAM);
        gioco.personaggi.add(Personaggio.WILLY_THE_KID);
        Collections.shuffle(gioco.personaggi);

    }


    /**
     * Questa funzione controlla da che mazzo il giocatore può pescare
     *
     * @param Giocatore Il giocatore che pesca
     * @param Mazzo     Un boolean per capire se pescare dal mazzo degli scarti (true) o da quello normale (false)
     * @param Seconda   Serve per gestire BLACK JACK
     * @return La carta che viene pescata
     */
    public Carta pesca(Giocatore Giocatore, boolean Mazzo, boolean Seconda) {
        Carta carta;
        if (Mazzo) {
            if (gioco.scarti.isEmpty()) {
                throw new java.util.NoSuchElementException("Scarti vuoti");
            }
            carta = gioco.scarti.removeLast();
        } else {
            carta = gioco.pescaDalMazzo();
            if (Seconda && carta.eRosso() && Giocatore.tabellone.getPersonaggio().equals(Personaggio.BLACK_JACK)) {
                Giocatore.carte.add(gioco.pescaDalMazzo());
            }
        }
        Giocatore.carte.add(carta);
        return carta;
    }

    /**
     * Serve per gestire il personaggio Jesse Jones, il quale può pescare da un altro giocatore
     *
     * @param prende il giocatore che ruba la carta
     * @param preso  il giocaotre a cui viene rubata la carta
     * @return la carta che viene rubata
     */
    public Carta jesseJones(Giocatore prende, Giocatore preso) {
        Carta carta;
        carta = preso.carte.getFirst();
        preso.carte.removeFirst();
        prende.carte.add(carta);
        return carta;
    }

    /**
     * Serve per ritornare le tre carte per gestire il personaggio kit carlson
     *
     * @return le prime tre carte del mazzo
     */
    public ArrayList<Carta> kitCarlson() {
        ArrayList<Carta> carte = new ArrayList<>();
        Carta temp;
        for (int i = 0; i < 3; i++) {
            temp = gioco.mazzo.getFirst();
            carte.add(temp);
            gioco.mazzo.removeFirst();
        }
        return carte;
    }


    /**
     * Serve per gestireil personaggio vulture sam
     * @param index giocatore che ha il personaggio
     */
    public boolean vultureSam(int index) {
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            if (i != index && gioco.giocatori[i].getVita() <= 0 && !gioco.giocatori[i].carte.isEmpty()) {
                flag = true;
                gioco.giocatori[index].carte.addAll(gioco.giocatori[i].carte);
                gioco.giocatori[i].carte.clear();
            }
        }
        return flag;
    }

    /**
     * Serve per gestire il personaggio Jesse Jones, il quale può scartare due carte per recuperare un punto vita
     *
     * @param giocatore giocatore corrente che scarte le due carte e recupera un èunto vita
     * @param carta1    index della prima carta da scartare
     * @param carta2    index della seconda carta da scartare
     * @return true se va tutto a buon fine
     */
    public boolean sidKetchum(Giocatore giocatore, int carta1, int carta2) {
        if (carta1 == carta2 || carta1 < 0 || carta2 < 0 || carta1 >= giocatore.carte.size() || carta2 >= giocatore.carte.size()) {
            return false;
        }
        int prima = Math.max(carta1, carta2);
        int seconda = Math.min(carta1, carta2);
        gioco.scarti.add(giocatore.carte.remove(prima));
        gioco.scarti.add(giocatore.carte.remove(seconda));
        giocatore.setVita(giocatore.getVita() + 1);
        return true;
    }

    /**
     * Serve per controllare se un giocatore può scartare
     *
     * @param giocatore il giocatore corrente che deve scartare le carte
     */
    public void scarta(Giocatore giocatore) {
        while (giocatore.carte.size() > giocatore.getVita()) {
            int index = (int) (Math.random() * giocatore.carte.size());
            gioco.scarti.add(giocatore.carte.remove(index));
        }
    }

    /**
     * Serve per caricare il gioco su un file
     */
    public void loadGame() {

        try {
            ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
            try {
                gioco = (Gioco) o.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serve per salvare il gioco dopo ogni turno
     */
    public void saveGame() {
        try {
               ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
               file.createNewFile();
               o.writeObject(gioco);
               o.flush();
               o.close();
        } catch (IOException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * Serve per ritornare il turno
     *
     * @return il turno
     */
    public int getTurno() {
        return gioco.turno;
    }

    /**
     * Serve per incrementare il turno e controlla e gestisce il personaggio Suzy LaFayette
     */
    public void incrementaTurno() {
        Giocatore attuale = gioco.giocatori[getTurno()];
        if (attuale.getVita() > 0 && attuale.tabellone.getPersonaggio().equals(Personaggio.SUZY_LAFAYETTE) && attuale.carte.isEmpty()) {
            attuale.carte.add(gioco.pescaDalMazzo());
        }
        for (int passo = 1; passo <= 4; passo++) {
            int prossimo = (gioco.turno + passo) % 4;
            if (gioco.giocatori[prossimo].getVita() > 0) {
                gioco.turno = prossimo;
                return;
            }
        }
    }

    /**
     * Funzione per applicare l'effetto che da la carta
     * @param giocatore giocatore che ha la carta
     * @param carta     carta corrente, che applica l'effetto
     */
    public void usacarta(Giocatore giocatore, Carta carta) {
        carta.ApplicaEffetto(gioco, giocatore);
        if (carta.getClass().getSimpleName().equals("Mirino") || carta.getClass().getSimpleName().equals("Mustang") || carta.getClass().getSimpleName().equals("Remington") || carta.getClass().getSimpleName().equals("RevCarabine") || carta.getClass().getSimpleName().equals("Schofield") || carta.getClass().getSimpleName().equals("Volcanic") || carta.getClass().getSimpleName().equals("Whinchester")) {
            giocatore.carte.remove(carta);
        } else {
            gioco.scarti.add(carta);
            giocatore.carte.remove(carta);
        }
    }

    /**
     * Funzione per applicare il panico
     * @param carta carta corrente, che applica l'effetto
     */
    public void usacartaP(Giocatore spara, Giocatore bersaglio, Carta carta) {
        carta.ApplicaEffetto(spara, bersaglio);
        gioco.scarti.add(carta);
        spara.carte.remove(carta);
    }


    /**
     * Funzione per applicare la prigione e il catbalou
     * @param carta carta corrente, che applica l'effetto
     */
    public void usacartaPC(Giocatore spara, Giocatore bersaglio, Carta carta) {
        carta.ApplicaEffetto(gioco, bersaglio);
        gioco.scarti.add(carta);
        spara.carte.remove(carta);
    }

    /**
     * Serve per usare il bang e il duello
     * @param spara     Giocatore che spara
     * @param carta     La carta con cui spara
     * @param bersaglio Giocatore bersagliato
     * @param si        un boolean che controlla che carta viene usata Bang(true),Duello(false)
     * @return True se puoi sparare o il giocatore che ha giocato la carta ha perso un punto vita,false se l'ha persa il bersaglio o non puoi sparare
     */
    public boolean usacarta(Giocatore spara, Giocatore bersaglio, Carta carta, boolean si) {
        if (si) { //bang
            boolean temp = carta.ApplicaEffetto(spara, bersaglio);
            if (!temp) {
                return false;
            }
            gioco.scarti.add(carta);
            spara.carte.remove(carta);
            spara.setHaSparato(true);
            return true;
        } else { // duello
            gioco.scarti.add(carta);
            spara.carte.remove(carta);
            return carta.ApplicaEffetto(spara, bersaglio, gioco);
        }
    }

    /**
     * Serve per difendersi da un altro giocatore
     * @param giocatore che deve difendersi
     * @return le carte con cui può difendersi
     */
    public ArrayList<Carta> difesa(Giocatore giocatore) {
        ArrayList<Carta> carte = new ArrayList<>();
        for (int i = 0; i < giocatore.carte.size(); i++) {
            if (giocatore.carte.get(i).getClass().getSimpleName().equals("Mancato") || giocatore.carte.get(i).getClass().getSimpleName().equals("Barile") || giocatore.carte.get(i).getClass().getSimpleName().equals("Birra")) {
                carte.add(giocatore.carte.get(i));
            }
        }
        if (carte.isEmpty() || giocatore.getVita()<=0) {
            return null;
        }
        return carte;
    }

    /**
     * Ritorna il giocatore in base all'index passato
     * @param index per passare un giocatore in base all'index
     * @return il giocatore, se non esiste ne crea uno
     */
    public Giocatore getGiocatore(int index) {
        if (gioco.giocatori[index] == null) {
            gioco.giocatori[index] = new Giocatore();
        }
        return gioco.giocatori[index];
    }


    /**
     * Serve per trovare e salvare in un array i giocatore a cui un giocatore può sparare in base alla distanza dell'arma
     * @param index la posizione nel tabellone del giocatore che ha usato la carta
     * @return l'array di giocatori a cui può sparare
     */
    public ArrayList<Giocatore> distanzaGiocatori(int index) {
        ArrayList<Giocatore> giocatori = new ArrayList<>();
        Giocatore da = gioco.giocatori[index];
        int portata = da.tabellone.getArma().getDistanza();
        if (da.tabellone.getPersonaggio().equals(Personaggio.ROSE_DOOLAN)) {
            portata++;
        }
        for (int i = 0; i < 4; i++) {
            if (i == index || gioco.giocatori[i].getVita() <= 0) {
                continue;
            }
            int posti = Math.min(Math.abs(index - i), 4 - Math.abs(index - i));
            if (haMustang(gioco.giocatori[i])) {
                posti++;
            }
            if (gioco.giocatori[i].tabellone.getPersonaggio().equals(Personaggio.PAUL_REGRET)) {
                posti++;
            }
            if (posti <= portata) {
                giocatori.add(gioco.giocatori[i]);
            }
        }
        return giocatori;
    }

    private boolean haMustang(Giocatore giocatore) {
        for (Carta carta : giocatore.tabellone.carteT) {
            if (carta instanceof Mustang) {
                return true;
            }
        }
        return false;
    }

    /**
     * Serve per controllare la vittoria, controllando i giocatori che sono morti e quelli ancora in vita
     *
     * @return Il giocatore che ha vinto altrimeni ritorna null
     */
    public Giocatore getVittoria() {
        for (int i = 0; i < 4; i++) {
            if (gioco.giocatori[i].tabellone.getRuolo().equals(Ruolo.SCERIFFO)) {
                if (gioco.giocatori[i].getVita() <= 0) {
                    for (int j = 0; j < 4; j++) {
                        if (gioco.giocatori[j].tabellone.getRuolo().equals(Ruolo.FUORILEGGE) && gioco.giocatori[j].getVita() > 0) {
                            return gioco.giocatori[j];
                        } else if (gioco.giocatori[j].tabellone.getRuolo().equals(Ruolo.RINNEGATO) && gioco.giocatori[j].getVita() > 0 && getVivo() == 1) {
                            return gioco.giocatori[j];
                        }
                    }
                } else if (getVivo() == 1) {
                    return gioco.giocatori[i];
                }
            }
        }
        return null;
    }

    /**
     * Serve per controllare se ci sono ancora giocatori vivi
     *
     * @return Il numero di giocatori vivi
     */
    public int getVivo() {
        int flag = 0;
        for (int i = 0; i < 4; i++) {
            if (gioco.giocatori[i].getVita() > 0) {
                flag++;
            }
        }
        return flag;
    }


    /**
     * Serve per ritornare le carte che ha un giocatore in mano
     *
     * @param giocatore gicoatore corrente che ha in mano le carte
     * @return ritorna le carte che il giocatore ha in mano
     */
    public ArrayList<Carta> ritornaCarte(Giocatore giocatore) {
        return giocatore.carte;
    }

    /**
     * Serve per vedere e scartare la prima carta del mazzo normale
     *
     * @return la carta estratta e messa nel mazzo degli scart
     */
    public Carta estrai() {
        Carta carta = gioco.pescaDalMazzo();
        gioco.scarti.add(carta);
        return carta;
    }

    /**
     * Gestisce il personaggio El Gringo
     *
     * @param ladro  Giocatore che prende la carta
     * @param rubato Giocatore che perde la carta
     */

    public void rubaCarta(Giocatore ladro, Giocatore rubato) {
        ladro.carte.add(rubato.carte.remove((int) (Math.random() * rubato.carte.size())));
    }

    /**
     * Serve per passare il mazzo degli scarti
     *
     * @return La prima carta del mazzo degli scarti
     */
    public ArrayList<Carta> ritornaScarti() {
        return gioco.scarti;
    }


    /**
     * Assegna i ruoli e i personaggi ai giocatori e gestisce il personaggio Rose Doolan
     *
     * @param i Index del giocatore
     */
    public void setPR(int i) {
        if (gioco.personaggi.getFirst().equals(Personaggio.EL_GRINGO) || gioco.personaggi.getFirst().equals(Personaggio.PAUL_REGRET)) {
            gioco.giocatori[i].setVita(3);
        } else {
            gioco.giocatori[i].setVita(4);
        }
        if (gioco.ruoli.getFirst().equals(Ruolo.SCERIFFO)) {
            gioco.giocatori[i].setVita(gioco.giocatori[i].getVita() + 1);
        }
        for (int y = 0; y < gioco.giocatori[i].getVita(); y++) {
            gioco.giocatori[i].carte.add(gioco.mazzo.removeFirst());
        }
        gioco.giocatori[i].tabellone = new Tabellone(gioco.ruoli.removeFirst(), gioco.personaggi.removeFirst());
    }

    /**
     * Serve per controllare se posso usare il barile come mancato
     *
     * @return true se non è un barile e se è un barile e la carta scartata precedentemente a esso e cuori, false se non è nulla
     */
    public boolean controllaBarile() {
        if (gioco.scarti.getLast().getClass().getSimpleName().equals("Mancato") || gioco.scarti.getLast().getClass().getSimpleName().equals("Birra")) {
            return true;
        }
        return gioco.scarti.getLast().getClass().getSimpleName().equals("Barile") && gioco.scarti.get(gioco.scarti.size() - 2).eCuori();
    }

    /**
     * Serve per far scartare la carta scelta dal giocatore
     *
     * @param giocatore il giocatore che scarta
     * @param carta     la carta scartata
     */
    public void scarta(Giocatore giocatore, Carta carta) {
        giocatore.carte.remove(carta);
        gioco.scarti.add(carta);
    }

    /**
     * Serve per togliere le carte dal mazzo
     *
     * @param index carta 1
     */
    public void rimettiInCima(ArrayList<Carta> carte) {
        gioco.mazzo.addAll(0, carte);
    }

    /**
     * Serve per applicare al gioco l'abilita del personaggio
     *
     * @param giocatore Il giocatore che ha questo personaggio
     */
    public void Jourdonnais(Giocatore giocatore) {
        new Barile("0").ApplicaEffetto(gioco, giocatore);
    }

}