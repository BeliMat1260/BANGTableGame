package CLI;

import Logica.Controllore.Controllore;
import Logica.Partita.Carte.Normali.Carta;
import Logica.Partita.Carte.Personaggi.Personaggio;
import Logica.Partita.Carte.Ruolo;
import Logica.Partita.Gestione.Giocatore;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Serve per far partire il gioco in modalità cli
 */
public class GiocoCLI {

    private final Scanner scanner; //scanner per input
    private final Controllore controllore;//controllore
    private int turno;//turni

    public GiocoCLI(Controllore controllore) {
        int emp = 0; //Serve per mostrare le carte agli altri giocatori
        Giocatore mostra = null;//giocatore che mostra le carte
        scanner = new Scanner(System.in);
        this.controllore = controllore;
        for (int i = 0; i < 4; i++) {
            if (controllore.getGiocatore(i).getNickname() == null) {
                setName(controllore.getGiocatore(i));
                controllore.setPR(i);
            }
        }
        clearScreen();
        while (true) {

            turno = controllore.getTurno();
            Giocatore giocatore = controllore.getGiocatore(turno);
            clearScreen();
            if (giocatore.getVita() > 0) {
                turno(giocatore);
                if (emp != 0) {
                    showStart("Il giocatore " + mostra.getNickname() + " ha mostrato " + mostra.carte.get(0).getClass().getSimpleName() + mostra.carte.get(0).getSeme() + " " + mostra.carte.get(1).getClass().getSimpleName() + mostra.carte.get(1).getSeme() + " " + mostra.carte.get(2).getClass().getSimpleName() + mostra.carte.get(2).getSeme());
                    emp--;
                }
                if (giocatore.isLock() && giocatore.getVita() > 0) {

                    showStart("Sei in prigione");
                    Carta temporanea = controllore.estrai();
                    if (giocatore.tabellone.getPersonaggio().equals(Personaggio.LUCKY_DUKE)) {
                        Carta temp = controllore.estrai();
                        showStart("Le carte estratte sono \n1-" + temporanea.toString() + "2-" + temp.toString());
                        if (temporanea.eCuori() || temp.eCuori()) {
                            showGreen("Sei fuori dalla prigione continua il turno normalmete");
                            giocatore.setLock(false);
                        } else {
                            showError("Sei ancora in prigione devi saltare il turno");
                        }
                    } else {
                        showStart("La carta estratta è " + temporanea.toString());
                        if (temporanea.eCuori()) {
                            showGreen("Sei fuori dalla prigione continua il turno normalmete");
                            giocatore.setLock(false);
                        } else {
                            showError("Sei ancora in prigione devi saltare il turno");
                        }
                    }
                }

                if (!giocatore.isLock()) {
                    Carta carta;
                    ArrayList<Carta> cartekit = null;
                    if (giocatore.tabellone.getPersonaggio().equals(Personaggio.KIT_CARLSON)) {
                        cartekit = controllore.kitCarlson();
                    }
                    for (int i = 0; i < 2; i++) {
                        if (giocatore.tabellone.getPersonaggio().equals(Personaggio.PEDRO_RAMIREZ)) {
                            try {
                                while (true) {
                                    showStart("Da che mazzo vuoi pescare-> (normale o scarti(la prima carta degli scarti è " + controllore.ritornaScarti().getLast().toString() + "))");
                                    String scelta = scanner.nextLine();
                                    scelta = scelta.toLowerCase();
                                    scelta = scelta.trim();
                                    if (scelta.equals("normale")) {
                                        controllore.pesca(giocatore, false, false);
                                        break;
                                    } else if (scelta.equals("scarti")) {
                                        controllore.pesca(giocatore, true, false);
                                        break;
                                    } else {
                                        showError("INPUT NON VALIDO");
                                    }
                                }
                            } catch (Exception e) {
                                showError("Il mazzo degli scarti e' vuoto");
                                showGreen("Hai pescato dal mazzo normale");
                                try {
                                    controllore.pesca(giocatore, false, false);
                                } catch (NoSuchElementException ignored) {
                                }
                            }
                        } else if (giocatore.tabellone.getPersonaggio().equals(Personaggio.JESSE_JONES)) {
                            int check = 0;
                            do {
                                showStart("Vuoi pescare dal mazzo o da un giocatore?");
                                String scelta = scanner.nextLine();
                                scelta = scelta.toLowerCase();
                                scelta = scelta.trim();
                                if (scelta.equals("mazzo")) {
                                    carta = controllore.pesca(giocatore, false, false);
                                    showGreen("Hai pescato la carta" + carta);
                                    check++;
                                } else if (scelta.equals("giocatore")) {
                                    int nS = 0;
                                    ArrayList<Giocatore> giocatori;
                                    showStart("A che giocatore vuoi rubare una carta");
                                    giocatori = stampaPlayerD(giocatore);
                                    Carta temp;
                                    do {
                                        try {
                                            showStart("A che giocatore vuoi rubare una carta?");
                                            String co = scanner.nextLine();
                                            nS = Integer.parseInt(co);
                                        } catch (NumberFormatException e) {
                                            showError("PLAYER NON VALIDO");
                                        }
                                    } while (nS < 1 || nS > giocatori.size());
                                    temp = controllore.jesseJones(giocatore, giocatori.get(nS - 1));
                                    showGreen("Hai rubato la carta " + temp.getClass().getSimpleName() + " " + temp.getSeme() + " a " + giocatori.get(nS - 1).getNickname());
                                    check++;
                                } else {
                                    showError("INPUT NON VALIDO");
                                }
                            } while (check != 1);
                        } else if (giocatore.tabellone.getPersonaggio().equals(Personaggio.KIT_CARLSON)) {
                            int nS = 0;
                            showStart("Puoi pescare 2 carte, le prime tre carte del mazzo sono:");
                            for (int j = 0; j < cartekit.size(); j++) {
                                showStart((j + 1) + "- " + cartekit.get(j).getClass().getSimpleName() + " " + cartekit.get(j).getSeme());
                            }
                            do {
                                try {
                                    showStart("Scegli la carta che vuoi pescare");
                                    String co = scanner.nextLine();
                                    nS = Integer.parseInt(co);
                                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                                    showError("CARTA NON VALIDA");
                                }
                            } while (nS < 1 || nS > cartekit.size());
                            giocatore.carte.add(cartekit.get(nS - 1));
                            cartekit.remove(nS - 1);
                            showGreen("Hai pescato correttamente");
                        } else {
                            if (i == 1 && giocatore.tabellone.getPersonaggio().equals(Personaggio.BLACK_JACK)) {
                                carta = controllore.pesca(giocatore, false, true);
                                showGreen("Hai pescato la carta" + carta);
                            } else {
                                carta = controllore.pesca(giocatore, false, false);
                                showGreen("Hai pescato la carta" + carta);
                            }
                        }
                    }
                    if (cartekit != null && !cartekit.isEmpty()) {
                        controllore.rimettiInCima(cartekit);
                    }

                    String scelta;
                    int s = 0;
                    if (giocatore.tabellone.getPersonaggio().equals(Personaggio.SID_KETCHUM)) {
                        showStart("Cosa vuoi fare? \n1-> Usare una carta \n2-> Passa il turno (Scarterai le carte in eccesso) \n3-> Scartare due carte per recuperare un punto vita ");
                        scelta = scanner.nextLine();
                        scelta = scelta.trim();
                        s = Integer.parseInt(scelta);
                        while (s < 1 || s > 3) {
                            try {
                                showError("SCELTA NON VALIDA");
                                showStart("Cosa vuoi fare? \n1-> Usare una carta \n2-> Passa il turno (Scarterai le carte in eccesso) \n3-> Scartare due carte per recuperare un punto vita ");
                                scelta = scanner.nextLine();
                                s = Integer.parseInt(scelta);
                            } catch (NumberFormatException e) {
                                showError("INPUT NON VALIDO");
                            }
                        }
                    } else {
                        showStart("Cosa vuoi fare? \n1-> Usare una carta \n2-> Passa il turno (Scarterai le carte in eccesso)");
                        scelta = scanner.nextLine();
                        try {
                            s = Integer.parseInt(scelta);
                        } catch (NumberFormatException e) {
                            while (s < 1 || s > 2) {
                                try {
                                    showStart("Cosa vuoi fare? \n1-> Usare una carta \n2-> Passa il turno (Scarterai le carte in eccesso)");
                                    scelta = scanner.nextLine();
                                    s = Integer.parseInt(scelta);
                                } catch (NumberFormatException ex) {
                                    showError("INPUT NON VALIDO");
                                }
                            }
                        }
                    }
                    boolean flag = true;
                    while (flag) {

                        switch (s) {

                            case 1 -> {
                                while (true) {
                                    if (giocatore.carte.isEmpty()) {
                                        showError("NON HAI CARTE");
                                        break;
                                    } else {
                                        ArrayList<Carta> m = stampaMazzo(giocatore);
                                        try {
                                            int nC = 0;
                                            do {
                                                try {
                                                    showStart("Inserisci il numero della carta che vuoi utilizzare->");
                                                    String c = scanner.nextLine();
                                                    nC = Integer.parseInt(c);
                                                } catch (NumberFormatException e) {
                                                    showError("INPUT NON VALIDO");
                                                }
                                            } while (nC < 1 || nC > (m.size() + 1));
                                            int sc = giocatore.carte.indexOf(m.get(nC - 1));
                                            switch (giocatore.carte.get(sc).getClass().getSimpleName()) {

                                                case "Bang" -> {
                                                    while (true) {
                                                        ArrayList<Giocatore> stampati = stampaPlayerB();
                                                        if (stampati.isEmpty()) {
                                                            showError("Nessun bersaglio a portata");
                                                            break;
                                                        }
                                                        int nS = 0;
                                                        do {
                                                            try {
                                                                showStart("A che giocatore vuoi sparare?");
                                                                String co = scanner.nextLine();
                                                                nS = Integer.parseInt(co);
                                                            } catch (NumberFormatException e) {
                                                                showError("PLAYER NON VALIDO");
                                                            }
                                                        } while (nS < 1 || nS > stampati.size());

                                                        if (controllore.usacarta(giocatore, stampati.get(nS - 1), giocatore.carte.get(sc), true)) {
                                                                if (controllore.difesa(stampati.get(nS - 1)) != null) {
                                                                    showStart("Giocatore " + stampati.get(nS - 1).getNickname() + "\nScegli che carta giocare per difenderti dal bang");
                                                                    stampaMazzo(controllore.difesa(stampati.get(nS - 1)));
                                                                    System.out.println((controllore.difesa(stampati.get(nS - 1)).size() + 1) + "-> Non giocare nessuna carta");
                                                                    int difesa = 0;
                                                                    do {
                                                                        try {
                                                                            showStart("Inserisci il numero della carta che vuoi utilizzare\n->");
                                                                            String coa = scanner.nextLine();
                                                                            difesa = Integer.parseInt(coa);
                                                                        } catch (NumberFormatException e) {
                                                                            showError("INPUT NON VALIDO");
                                                                        }
                                                                    } while (difesa < 1 || difesa > (controllore.difesa(stampati.get(nS - 1)).size() + 1));
                                                                    if (difesa != (controllore.difesa(stampati.get(nS - 1)).size() + 1)) {
                                                                        int n = stampati.get(nS - 1).carte.indexOf(controllore.difesa(stampati.get(nS - 1)).get(difesa - 1));
                                                                        controllore.scarta(stampati.get(nS - 1), stampati.get(nS - 1).carte.get(n));
                                                                        if (stampati.get(nS - 1).tabellone.getPersonaggio().equals(Personaggio.JOURDONNAIS)) {
                                                                            controllore.Jourdonnais(stampati.get(nS - 1));

                                                                        }
                                                                        if (controllore.controllaBarile()) {
                                                                            showError("Hai mancato " + stampati.get(nS - 1).getNickname());
                                                                        } else {
                                                                            stampati.get(nS - 1).setVita(stampati.get(nS - 1).getVita() - 1);
                                                                            showGreen("Il giocatore " + stampati.get(nS - 1).getNickname() + " ha perso un punto vita");
                                                                        }
                                                                    } else {
                                                                        stampati.get(nS - 1).setVita(stampati.get(nS - 1).getVita() - 1);
                                                                        if (stampati.get(nS - 1).tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                                                                            controllore.pesca(stampati.get(nS - 1), false, false);
                                                                        }
                                                                        if (stampati.get(nS - 1).tabellone.getPersonaggio().equals(Personaggio.EL_GRINGO)) {
                                                                            controllore.rubaCarta(stampati.get(nS - 1), giocatore);
                                                                        }
                                                                        showGreen("Il giocatore " + stampati.get(nS - 1).getNickname() + " ha perso un punto vita");
                                                                    }
                                                                } else if (stampati.get(nS - 1).getVita() > 0){
                                                                    stampati.get(nS - 1).setVita(stampati.get(nS - 1).getVita() - 1);
                                                                    if (stampati.get(nS - 1).tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                                                                        controllore.pesca(stampati.get(nS - 1), false, false);
                                                                    }
                                                                    if (stampati.get(nS - 1).tabellone.getPersonaggio().equals(Personaggio.EL_GRINGO)) {
                                                                        controllore.rubaCarta(stampati.get(nS - 1), giocatore);
                                                                    }
                                                                    showGreen("Il giocatore " + stampati.get(nS - 1).getNickname() + " ha perso un punto vita");
                                                                }
                                                        } else {
                                                            showError("hai già giocato un bang in questo turno");
                                                        }
                                                        break;
                                                    }
                                                }

                                                case "Catbalou" -> {
                                                    ArrayList<Giocatore> giocatori = stampaPlayerD(giocatore);
                                                    int nS = 0;
                                                    do {
                                                        try {
                                                            showStart("A che giocatore vuoi far scartare una carta?");
                                                            String coaa = scanner.nextLine();
                                                            nS = Integer.parseInt(coaa);
                                                        } catch (NumberFormatException e) {
                                                            showError("INPUT NON VALIDO");
                                                        }
                                                    } while (nS < 1 || nS > (controllore.getVivo() - 1));
                                                    controllore.usacartaPC(giocatore, giocatori.get(nS - 1), giocatore.carte.get(sc));
                                                    showGreen("Hai fatto scartare una carta" + " a " + giocatori.get(nS - 1).getNickname());
                                                }

                                                case "Duello" -> {
                                                    ArrayList<Giocatore> giocatori = stampaPlayerD(giocatore);
                                                    int nS = 0;
                                                    do {
                                                        try {
                                                            showStart("Chi vuoi duellare?");
                                                            String coaa = scanner.nextLine();
                                                            nS = Integer.parseInt(coaa);
                                                        } catch (NumberFormatException e) {
                                                            showError("INPUT NON VALIDO");
                                                        }
                                                    } while (nS < 1 || nS > (controllore.getVivo() - 1));
                                                    boolean r = controllore.usacarta(giocatore, giocatori.get(nS - 1), giocatore.carte.get(sc), false);
                                                    if (r) {
                                                        showError("Hai perso un punto vita");
                                                    } else {
                                                        showGreen("Il giocatore " + giocatori.get(nS - 1).getNickname() + " ha perso un punto vita");
                                                    }
                                                }

                                                case "Emporio" -> {
                                                    controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                    showGreen("Hai giocato la carta correttamente");
                                                    emp = controllore.getVivo();
                                                    mostra = giocatore;
                                                }

                                                case "Gatling" -> {
                                                    for (int i = 0; i < 4; i++) {
                                                        if (controllore.getGiocatore(i) != giocatore) {
                                                            if (controllore.difesa(controllore.getGiocatore(i)) != null) {
                                                                showStart("Giocatore " + controllore.getGiocatore(i).getNickname() + "\nScegli che carta giocare per difenderti dal bang");
                                                                ArrayList<Carta> stampati = controllore.difesa(controllore.getGiocatore(i));
                                                                stampaMazzo(stampati);
                                                                System.out.println((stampati.size() + 1) + "-> Non giocare nessuna carta");
                                                                int difesa = 0;
                                                                do {
                                                                    try {
                                                                        showStart("Inserisci il numero della carta che vuoi utilizzare\n->");
                                                                        String coa = scanner.nextLine();
                                                                        difesa = Integer.parseInt(coa);
                                                                    } catch (NumberFormatException e) {
                                                                        showError("INPUT NON VALIDO");
                                                                    }
                                                                } while (difesa < 1 || difesa > stampati.size() + 1);


                                                                if (difesa != controllore.getGiocatore(i).carte.size() + 1) {
                                                                    controllore.usacarta(controllore.getGiocatore(i), controllore.getGiocatore(i).carte.get(controllore.getGiocatore(i).carte.indexOf(stampati.get(difesa-1))));
                                                                    if (controllore.controllaBarile()) {
                                                                        controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                                        showError("Hai mancato " + controllore.getGiocatore(i).getNickname());
                                                                    } else {
                                                                        controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                                        if (controllore.getGiocatore(i).tabellone.getPersonaggio().equals(Personaggio.EL_GRINGO)) {
                                                                            controllore.rubaCarta(controllore.getGiocatore(i), giocatore);
                                                                        }
                                                                        controllore.getGiocatore(i).setVita(controllore.getGiocatore(i).getVita() - 1);
                                                                        showGreen("Il giocatore " + controllore.getGiocatore(i).getNickname() + " ha perso un punto vita");
                                                                    }
                                                                } else {
                                                                    controllore.getGiocatore(i).setVita(controllore.getGiocatore(i).getVita() - 1);
                                                                    if (controllore.getGiocatore(i).tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                                                                        controllore.pesca(controllore.getGiocatore(i), false, false);
                                                                    }
                                                                    controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                                    if (controllore.getGiocatore(i).tabellone.getPersonaggio().equals(Personaggio.EL_GRINGO)) {
                                                                        controllore.rubaCarta(controllore.getGiocatore(i), giocatore);
                                                                    }
                                                                    showGreen("Il giocatore " + controllore.getGiocatore(i).getNickname() + " ha perso un punto vita");
                                                                }
                                                            } else if (controllore.getGiocatore(i).getVita() > 0) {
                                                                controllore.getGiocatore(i).setVita(controllore.getGiocatore(i).getVita() - 1);
                                                                if (controllore.getGiocatore(i).tabellone.getPersonaggio().equals(Personaggio.BART_CASSIDY)) {
                                                                    controllore.pesca(controllore.getGiocatore(i), false, false);
                                                                }
                                                                controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                                if (controllore.getGiocatore(i).tabellone.getPersonaggio().equals(Personaggio.EL_GRINGO)) {
                                                                    controllore.rubaCarta(controllore.getGiocatore(i), giocatore);
                                                                }
                                                                showGreen("Il giocatore " + controllore.getGiocatore(i).getNickname() + " ha perso un punto vita");
                                                            }
                                                        }
                                                    }
                                                }

                                                case "Prigione" -> {

                                                    ArrayList<Giocatore> giocatori = stampaPlayer(giocatore);
                                                    int nS = 0;
                                                    do {
                                                        try {
                                                            showStart("Che giocatore vuoi mettere in prigione?");
                                                            String coaa = scanner.nextLine();
                                                            nS = Integer.parseInt(coaa);
                                                        } catch (NumberFormatException e) {
                                                            showError("INPUT NON VALIDO");
                                                        }
                                                    } while (nS < 1 || nS > (controllore.getVivo() - 1));
                                                    controllore.usacartaPC(giocatore, giocatori.get(nS - 1), giocatore.carte.get(sc));
                                                    showGreen("Hai messo in prigione " + giocatori.get(nS - 1).getNickname());

                                                }

                                                case "Panico" -> {
                                                    ArrayList<Giocatore> giocatori = stampaPlayerP();
                                                    int nS = 0;
                                                    do {
                                                        try {
                                                            showStart("A che giocatore vuoi rubare una carta?");
                                                            String coaa = scanner.nextLine();
                                                            nS = Integer.parseInt(coaa);
                                                        } catch (NumberFormatException e) {
                                                            showError("INPUT NON VALIDO");
                                                        }
                                                    } while (nS < 1 || nS > (controllore.getVivo() - 1));
                                                    controllore.usacartaP(giocatore, giocatori.get(nS - 1), giocatore.carte.get(sc));
                                                    showGreen("Hai rubato una carta" + " a " + giocatori.get(nS - 1).getNickname());
                                                }

                                                default -> {
                                                    controllore.usacarta(giocatore, giocatore.carte.get(sc));
                                                    showGreen("Hai giocato la carta correttamente");
                                                }

                                            }
                                            break;
                                        } catch (IndexOutOfBoundsException e) {
                                            showError("La carta selezionata non esiste");
                                        }
                                    }

                                }
                            }

                            case 2 -> {
                                controllore.scarta(giocatore);
                                flag = false;
                            }

                            case 3 -> {
                                int nS = 0;
                                int nS1 = 0;
                                ArrayList<Carta> carte;
                                if (giocatore.tabellone.getPersonaggio().equals(Personaggio.SID_KETCHUM)) {
                                    showStart("Qual'e' la prima carta che vuoi scartare per recuperare un punto vita?");
                                    carte = stampaMazzo(giocatore);
                                    do {
                                        try {
                                            showStart("Qual'e' la prima carta che vuoi scartare per recuperare un punto vita?");
                                            String co = scanner.nextLine();
                                            nS = Integer.parseInt(co);
                                        } catch (Exception e) {
                                            showError("CARTA NON VALIDA");
                                        }
                                    } while (nS < 1 || nS > carte.size());

                                    do {
                                        try {
                                            showStart("Qual'e' la seconda carta che vuoi scartare per recuperare un punto vita?");
                                            String co = scanner.nextLine();
                                            nS1 = Integer.parseInt(co);
                                        } catch (Exception e) {
                                            showError("CARTA NON VALIDA");
                                        }
                                    } while (nS1 < 1 || nS1 > carte.size() || nS == nS1);
                                    if (controllore.sidKetchum(giocatore, nS - 1, nS1 - 1)) {
                                        showGreen("hai scartato correttamente e hai recuperato un punto vita");
                                    }
                                }
                            }
                            default -> showError("INPUT NON VALIDO");
                        }
                        if (flag) {
                            do {
                                try {
                                    showStart("Cosa vuoi fare? \n1)usare una carta \n2)Passa il turno(Scarterai le carte in eccesso)");
                                    String caao = scanner.nextLine();
                                    s = Integer.parseInt(caao);
                                } catch (NumberFormatException e) {
                                    showError("INPUT NON VALIDO");
                                }
                            } while (s < 1 || s > 2);
                        }

                        if (giocatore.tabellone.getPersonaggio().equals(Personaggio.VULTURE_SAM)) {
                            if (controllore.vultureSam(turno)) {
                                showGreen("Il giocatore " + giocatore.getNickname() + " ha preso le carte correttamente dal morto");
                            }
                        }

                    }

                    if (controllore.getVittoria() != null) {
                        finePartita(controllore.getVittoria());
                        break;
                    }
                }
                controllore.saveGame();
                controllore.incrementaTurno();
                giocatore.setHaSparato(false);
                giocatore.setLock(false);
            }
        }
    }


    /**
     * Serve per stampare il mazzo di carte che puo giocare all' inizio del turno
     *
     * @param giocatore Giocatore corrente che ha in mano in mazzo
     */
    public ArrayList<Carta> stampaMazzo(Giocatore giocatore) {
        ArrayList<Carta> temp = new ArrayList<>();
        for (int i = 0; i < controllore.ritornaCarte(giocatore).size(); i++) {
            if (!giocatore.carte.get(i).getClass().getSimpleName().equals("Mancato") && !giocatore.carte.get(i).getClass().getSimpleName().equals("Barile")) {
                temp.add(giocatore.carte.get(i));
            }
        }
        for (int i = 0; i < temp.size(); i++) {
            System.out.println("Carta " + (i + 1) + ":" + temp.get(i).toString() + "\n");
        }
        return temp;
    }

    /**
     * Serve per stampare il mazzo dato il mazzo di carte
     *
     * @param carte Carte da stampare
     */
    public void stampaMazzo(ArrayList<Carta> carte) {
        for (int i = 0; i < carte.size(); i++) {
            System.out.println("Carta " + (i + 1) + " : " + carte.get(i).toString() + "\n");
        }
    }

    /**
     * Serve per stampare i nomi dei giocatori che il gioatore puo imprigionare
     *
     * @return L'arraylist di giocatori che possono essere imprigionati
     */
    public ArrayList<Giocatore> stampaPlayer(Giocatore giocatore) {
        ArrayList<Giocatore> giocatori = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Giocatore altro = controllore.getGiocatore(i);
            if (altro == giocatore || altro.getVita() <= 0 || altro.tabellone.getRuolo().equals(Ruolo.SCERIFFO)) {
                continue;
            }
            giocatori.add(altro);
        }
        for (int i = 0; i < giocatori.size(); i++) {
            System.out.println("Giocatore " + (i + 1) + ": " + giocatori.get(i).getNickname() + " con " + giocatori.get(i).getVita() + " punti vita\n" + "Ruolo:Non identificato");
        }
        return giocatori;
    }

    /**
     * Serve per stampare i nomi dei giocatori a cui un giocatore può sparare
     *
     * @return L'arraylist di giocatori a cui il giocatore corrente può sparare
     */
    public ArrayList<Giocatore> stampaPlayerB() {
        ArrayList<Giocatore> giocatori = controllore.distanzaGiocatori(turno);
        for (int i = 0; i < giocatori.size(); i++) {
            if (giocatori.get(i).tabellone.getRuolo().equals(Ruolo.SCERIFFO)) {
                System.out.println("Giocatore " + (i + 1) + ": " + giocatori.get(i).getNickname() + " con " + giocatori.get(i).getVita() + " punti vita\n" + "Ruolo:SCERIFFO");
            } else {
                System.out.println("Giocatore " + (i + 1) + ": " + giocatori.get(i).getNickname() + " con " + giocatori.get(i).getVita() + " punti vita\n" + "Ruolo:Non identificato");
            }
        }
        return giocatori;
    }

    /**
     * Stampa i giocatori per la carta panico
     *
     * @return L'arraylist dei giocatori
     */
    public ArrayList<Giocatore> stampaPlayerP() {
        ArrayList<Giocatore> giocatori = new ArrayList<>();
        try {
            giocatori.add(controllore.getGiocatore(turno + 1));
        } catch (IndexOutOfBoundsException e) {
            giocatori.add(controllore.getGiocatore(0));
        }
        try {
            giocatori.add(controllore.getGiocatore(turno - 1));
        } catch (IndexOutOfBoundsException e) {
            giocatori.add(controllore.getGiocatore(3));
        }

        for (int i = 0; i < giocatori.size(); i++) {
            if (giocatori.get(i).tabellone.getRuolo().equals(Ruolo.SCERIFFO)) {
                System.out.println("Giocatore " + (i + 1) + ": " + giocatori.get(i).getNickname() + " con " + giocatori.get(i).getVita() + " punti vita\n" + "Ruolo:SCERIFFO");
            } else {
                System.out.println("Giocatore " + (i + 1) + ": " + giocatori.get(i).getNickname() + " con " + giocatori.get(i).getVita() + " punti vita\n" + "Ruolo:Non identificato");
            }
        }
        return giocatori;
    }

    /**
     * Serve per stampare i nomi dei giocatori
     *
     * @param giocatore corrente
     * @return l'arraylist di giocatori
     */
    public ArrayList<Giocatore> stampaPlayerD(Giocatore giocatore) {
        ArrayList<Giocatore> giocatori = new ArrayList<>();
        int y = 1;
        for (int i = 0; i < 4; i++) {
            if (controllore.getGiocatore(i) != giocatore) {
                if (controllore.getGiocatore(i).tabellone.getRuolo().equals(Ruolo.SCERIFFO)) {
                    System.out.println("Giocatore " + y + ": " + controllore.getGiocatore(i).getNickname() + " con " + controllore.getGiocatore(i).getVita() + " pnti vita\n" + "Ruolo:SCERIFFO");
                } else {
                    System.out.println("Giocatore " + y + ": " + controllore.getGiocatore(i).getNickname() + " con " + controllore.getGiocatore(i).getVita() + " punti vita\n" + "Ruolo:Non identificato");
                }
                giocatori.add(controllore.getGiocatore(i));
                y++;
            }
        }
        return giocatori;
    }


    /**
     * Questa funzione stampa il turno
     *
     * @param giocatore il giocatore corrrente
     */
    public void turno(Giocatore giocatore) {
        showStart("E' il turno del giocatore " + giocatore.getNickname());
        System.out.println("Ruolo: " + giocatore.tabellone.getRuolo() + " Personaggio: " + giocatore.tabellone.getPersonaggio() + "\nHai " + giocatore.getVita() + " punti vita " + "\nL'arma equipaggiata è " + giocatore.tabellone.getArma().toString());
    }

    /**
     * Questa funzione serve per inserire il nickname del giocatore
     *
     * @param giocatore il giocatore corrente a cui bisogna settare il nickname
     */
    public void setName(Giocatore giocatore) {
        String nickname;
        int conta = 0;
        System.out.print("Inserisci il nickname->");
        nickname = scanner.nextLine();
        for (int i = 0; i < 4; i++) {
            if (!nickname.equals(controllore.getGiocatore(i).getNickname())) {
                conta++;
            }
        }
        while (!nickname.matches("^[a-zA-Z0-9]*$") || conta != 4) {
            conta = 0;
            showError("NICKNAME NON VALIDO (Non puo contenere caratteri speciali ne essere ugale agli altri)");
            System.out.print("Inserisci il nickname->");
            nickname = scanner.nextLine();
            for (int i = 0; i < 4; i++) {
                if (!nickname.equals(controllore.getGiocatore(i).getNickname())) {
                    conta++;
                }
            }
        }
        giocatore.setNickname(nickname);
        showGreen("NICKNAME INSERITO CON SUCCESSO");
    }

    /**
     * Serve per stampare un errore
     *
     * @param msg Errore corrente
     */
    public void showError(String msg) {
        System.out.println("\u001B[31m" + msg.toUpperCase() + "\u001b[0m");
    }


    /**
     * Serve per stampare un azione fatta corettamente
     *
     * @param msg Azione corrente
     */
    public void showGreen(String msg) {
        System.out.println("\u001B[32m" + msg.toUpperCase() + "\u001b[0m");
    }

    /**
     * Serve per stampare un inizio di turno a altre opzioni
     *
     * @param msg Azione corrente
     */
    public void showStart(String msg) {
        System.out.println("\u001B[34m" + msg.toUpperCase() + "\u001b[0m");
    }

    /**
     * Questa funzione serve per stampare quale giocatore ha vinto
     *
     * @param giocatore il giocatore corrente
     */
    public void finePartita(Giocatore giocatore) {
        showStart("Giocatore: " + giocatore.getNickname() + " ha vinto");
        showStart("Grazie per aver giocato");
    }

    /**
     * Questa funzione serve pulire lo schermo
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}