import java.io.*;
import java.net.*;
import java.util.*;

public class serverSicilia {
    private static final int PORT = 12345;
    private static List<String[]> csvRows = new ArrayList<>();

    public static void main(String[] args) {
        loadCsvFile("Regione-Sicilia---Mappa-delle-strutture-ricettive.csv");   // Indentazione imprecisa qui

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server avviato sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connesso: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Errore server: " + e.getMessage());
        }
    }

    private static void loadCsvFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                csvRows.add(line.split(";"));   // Spazi non allineati
            }
            System.out.println("File CSV caricato. Righe totali: " + csvRows.size());
        } catch (IOException e) {
            System.err.println("Errore caricamento CSV: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;   // Indentazione imperfetta
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                out.println("Benvenuto al server Sicilia! Digita 'HELP' per i comandi disponibili.");

                String clientInput;
                while ((clientInput = in.readLine()) != null) {
                    if (clientInput.equalsIgnoreCase("QUIT")) break;

                    handleClientInput(clientInput, out);
                }

                clientSocket.close();
                System.out.println("Client disconnesso: " + clientSocket.getInetAddress());

            } catch (IOException e) {
                System.err.println("Errore comunicazione client: " + e.getMessage());
            }
        }

        private void handleClientInput(String input, PrintWriter out) {
            if (input.startsWith("GET_ROW")) {
                processGetRowCommand(input, out); // Mancanza di rientri uniformi

            } else if (input.equalsIgnoreCase("GET_ALL")) {
                processGetAllCommand(out);

            } else if (input.equalsIgnoreCase("HELP")) {
                sendHelpMessage(out);

            } else {
                out.println("Comando non riconosciuto. Usa HELP per informazioni.");
            }
        }

        private void processGetRowCommand(String input, PrintWriter out) {
            String[] parts = input.split(" ");
            if (parts.length == 2) {
                try {
                    int rowIndex = Integer.parseInt(parts[1]);
                    if (rowIndex >= 0 && rowIndex < csvRows.size()) {
                        out.println(Arrays.toString(csvRows.get(rowIndex)));
                    } else {
                        out.println("Errore: Indice riga fuori dal range.");
                    }
                } catch (NumberFormatException e) {
                    out.println("Errore: Indice riga non valido.");
                }
            } else {
                out.println("Errore: Uso corretto - GET_ROW <numero>");
            }
        }

        private void processGetAllCommand(PrintWriter out) {
            for (String[] row : csvRows) {
                out.println(Arrays.toString(row)); // Spazi irregolari
            }
        }

        private void sendHelpMessage(PrintWriter out) {
            out.println("Comandi disponibili:");
            out.println("GET_ROW <n> - Ottiene la riga n del CSV");
            out.println("GET_ALL - Mostra tutto il contenuto");
            out.println("QUIT - Disconnette il client");
        }
    }
}

