import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 1134;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connesso al server. Inserisci un comando (GET_ROW, GET_COL, GET_ALL, QUIT):");

            while (true) {
                System.out.print("> ");
                String comando = scanner.nextLine();
                if (comando.equalsIgnoreCase("QUIT")) break;

                output.println(comando);
                String risposta;
                while ((risposta = input.readLine()) != null && !risposta.isEmpty()) {
                    System.out.println(risposta);
                    if (!input.ready()) break;
                }
            }

        } catch (IOException e) {
            System.err.println("Errore di connessione al server: " + e.getMessage());
        }
    }
}
