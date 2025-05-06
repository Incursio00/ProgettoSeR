import java.io.*;
import java.util.*;

public class GestoreDati {

    private List<String[]> datiCSV;

    public GestoreDati(String pathCSV) {
        datiCSV = new ArrayList<>();
        caricaCSV(pathCSV);
    }

    private void caricaCSV(String path) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String linea;
            boolean primaRiga = true;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                if (primaRiga) {
                    primaRiga = false; // Salta intestazione
                    continue;
                }
                String[] campi = linea.split(";", -1);
                datiCSV.add(campi);
            }
            System.out.println("Caricate " + datiCSV.size() + " righe dal file CSV.");
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file CSV: " + e.getMessage());
        }
    }

    public String getRiga(int n) {
        if (n < 0 || n >= datiCSV.size()) {
            return "ERROR: Riga non valida";
        }
        return String.join(" | ", datiCSV.get(n));
    }

    public String getTutte() {
        StringBuilder sb = new StringBuilder();
        for (String[] riga : datiCSV) {
            sb.append(String.join(" | ", riga)).append("\n");
        }
        return sb.toString();
    }

    public int numeroRighe() {
        return datiCSV.size();
    }
}
