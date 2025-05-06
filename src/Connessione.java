import java.io.*;
import java.net.*;

/**
 * La classe Connection estende Thread, il server potrà gestire più connessioni
 */
public class Connessione extends Thread {
    private Socket clientSocket;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private GestoreDati dati;

    /**
     * Costruttore: inizializza il socket client e i flussi I/O
     */
    public Connessione(Socket client, GestoreDati dati) {
        this.dati = dati;
        this.clientSocket = client;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gestione della richiesta da parte del client
     */
    @Override
    public void run() {
        try {
            int scelta = Integer.parseInt(in.readLine());
            System.out.println("Richiesta ricevuta: " + scelta);

            switch (scelta) {
                case 0:
                    out.println("Connessione terminata dal client.");
                    break;

                case 1: // Richiedi riga specifica
                    int nRiga = Integer.parseInt(in.readLine());
                    out.println(dati.getRiga(nRiga));
                    break;

                case 2: // Richiedi tutte le strutture (potresti limitare per prestazioni reali)
                    out.println(dati.getTutte());
                    break;

                default:
                    out.println("Scelta non valida");
            }
        } catch (IOException | NumberFormatException e) {
            out.println("Errore nella comunicazione o nell'elaborazione della richiesta: " + e.getMessage());
        } finally {
            chiudi();
        }
    }

    /**
     * Chiude la connessione e i flussi
     */
    public void chiudi() {
        System.out.println("Connessione chiusa");
        try {
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
