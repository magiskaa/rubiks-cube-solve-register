package fxrubkinkuutio;

import java.io.*;

/**
 * Ratkaisu
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisu {
    private int id;
    private String aika = " ";
    private String pvm = " ";
    private String kellonaika = " ";
    private boolean dnf = false;
    private boolean kaksiS = false;
    private int seuraavaId = 1;
    
    /**
     * antaa testiarvot ratkaisulle
     */
    public void testiArvot() {
        aika = "00;24,284";
        pvm = "18.3.2024";
        kellonaika = "15:54";
        dnf = false;
        kaksiS = true;
    }
    
    
    /**
     * antaa testiarvot ratkaisulle
     */
    public void testiArvot2() {
        aika = "00;17,478";
        pvm = "19.3.2024";
        kellonaika = "18:51";
        dnf = false;
        kaksiS = false;
    }
    
    /**
     * tulostaa ratkaisun tiedot
     * @param out ps
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", id, 3) + " | " + aika);
        out.println(pvm + " | " + kellonaika);
        out.println("DNF: " + dnf);
        out.println("+2s: " + kaksiS);
        out.println();
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

        toka.testiArvot();
        toka.tulosta(System.out);
    }
    
    
    

}