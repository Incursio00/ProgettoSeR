import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class serverSicilia {

    public static final int PORT = 1134;

    /**
     * Il main inizializza la classe per gestire i dati letti dal file
     * e attende che avvenga una connessione tra client e server in modo da poter creare il Socket.
     */
    public static void main(String[] args) {
        // Percorso del file CSV
        String pathFile = "file.csv";

        // Inizializza il gestore dati
        GestoreDati dati = new GestoreDati(pathFile);

        // Avvia il server
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT + "...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione accettata da " + clientSocket.getInetAddress());
                Connessione connessione = new Connessione(clientSocket, dati);
                connessione.start(); // nuovo thread
            }
        } catch (IOException e) {
            System.err.println("Errore durante l'avvio del server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
