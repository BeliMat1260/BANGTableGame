# BANG

Partita a quattro giocatori di Bang!, con menu JavaFX e partita in console.

## Avvio

Dalla cartella `BANG`, con JDK 21 e Maven:

```text
mvn javafx:run
```

I test della logica:

```text
mvn test
```

In menu si sceglie una partita nuova o un salvataggio (`Bang.ser` nella cartella di avvio), poi la console. I nomi accettano lettere e numeri.

## Regole sistemate

- Il turno passa al prossimo giocatore ancora in vita.
- Un Bang segna solo il colpo del turno. La prigione resta un controllo a parte, sui cuori.
- La distanza usa i posti in tavola, la gittata dell'arma, Rose Doolan, il Mustang e Paul Regret. Il Mustang non accorcia più la propria arma.
- Se il mazzo finisce si rimescolano gli scarti. Pescare dagli scarti non li mescola nel mazzo prima del tempo.
- Sid Ketchum scarta le due carte scelte. Vulture Sam sposta le carte del giocatore eliminato, senza copiarle.
- Kit Carlson rimette sul mazzo la carta che non pesca.
