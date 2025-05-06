public class Struttura {
    private String codice;
    private String denominazione;
    private String tipologia;
    private String comune;
    private String provincia;

    public Struttura(String codice, String denominazione, String tipologia, String comune, String provincia) {
        this.codice = codice;
        this.denominazione = denominazione;
        this.tipologia = tipologia;
        this.comune = comune;
        this.provincia = provincia;
    }

    public String getCodice() { return codice; }
    public String getDenominazione() { return denominazione; }
    public String getTipologia() { return tipologia; }
    public String getComune() { return comune; }
    public String getProvincia() { return provincia; }

    @Override
    public String toString() {
        return "Struttura: " + denominazione + " (" + tipologia + "), " + comune + " - " + provincia;
    }
}

