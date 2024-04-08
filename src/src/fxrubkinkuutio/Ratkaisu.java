package fxrubkinkuutio;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ratkaisu
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisu {
    private int id = 1;
    private String aika = " ";
    private String pvm = " ";
    private String kellonaika = " ";
    private String dnf = "F";
    private String kaksiS = "F";
    private int sekoitusId = 1;
    private static int seuraavaId = 1;
    
    /**
     * antaa testiarvot ratkaisulle
     */
    public void testiArvot() {
        aika = "00;24,284";
        pvm = "18.3.2024";
        kellonaika = "15:54";
        dnf = "F";
        kaksiS = "T";
        sekoitusId = 1;
    }
    
    
    /**
     * antaa testiarvot ratkaisulle
     */
    public void testiArvot2() {
        aika = "00;17,478";
        pvm = "19.3.2024";
        kellonaika = "18:51";
        dnf = "F";
        kaksiS = "F";
        sekoitusId = 2;
    }
    
    /**
     * tulostaa ratkaisun tiedot
     * @param out ps
     */
    public void tulosta(PrintStream out) {
        out.println("Nro: " + id);
        out.println("Aika: " + aika);
        out.println("Päivämäärä: " + pvm);
        out.println("Kellonaika: " + kellonaika);
        out.println("DNF: " + dnf);
        out.println("+2s: " + kaksiS);
    }
    
    /**
     * tulostaa ratkaisun tiedot
     * @param os os
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * rekisteröi ratkaisun
     * @return ratkaisun id:n
     * @example
     * <pre name="test">
     *   Ratkaisu eka = new Ratkaisu();
     *   eka.getId() === 0;
     *   eka.rekisteroi();
     *   Ratkaisu toka = new Ratkaisu();
     *   toka.rekisteroi();
     *   int n1 = eka.getId();
     *   int n2 = toka.getId();
     *   n1 === n2-1;
     * </pre>

     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }
    
    /**
     * palauttaa ratkaisun id:n
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param nr numero
     */
    public void setId(int nr) {
        id = nr;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    /**
     * @return sekoitus id
     */
    public int getSekoitusId() {
        return sekoitusId;
    }
    
    /**
     * palauttaa ratkaisun ajan
     * @return aika
     * @example
     * <pre name="test">
     *   Ratkaisu eka = new Ratkaisu();
     *   eka.testiArvot();
     *   aku.getAika() === "15:54";
     * </pre>
     */
    public String getAika() {
        return aika;
    }
    
    /**
     * @return pvm
     */
    public String getPvm() {
        return pvm;
    }
    
    @Override
    public String toString() {
        return "" + getId() + "|" + aika + "|" + pvm + "|" + kellonaika + "|" + dnf + "|" + kaksiS + "|" + sekoitusId;
    }
    
    /**
     * @param rivi tiedostosta luettu rivi
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        aika = Mjonot.erota(sb, '|', aika);
        pvm = Mjonot.erota(sb, '|', pvm);
        kellonaika = Mjonot.erota(sb, '|', kellonaika);
        dnf = Mjonot.erota(sb, '|', dnf);
        kaksiS = Mjonot.erota(sb, '|', kaksiS);
        sekoitusId = Mjonot.erota(sb, '|', sekoitusId);
    }
    
    @Override
    public boolean equals(Object jasen) {
        if ( jasen == null ) return false;
        return this.toString().equals(jasen.toString());
    }


    @Override
    public int hashCode() {
        return id;
    }

    
    /**
     * @param args ei
     */
    public static void main(String[] args) {
        Ratkaisu eka = new Ratkaisu();
        Ratkaisu toka = new Ratkaisu();
        eka.rekisteroi();
        toka.rekisteroi();
        eka.tulosta(System.out);
        eka.testiArvot();
        eka.tulosta(System.out);
        
        toka.testiArvot();
        toka.tulosta(System.out);

        toka.testiArvot2();
        toka.tulosta(System.out);
    }
    
    
    

}