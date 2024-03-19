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
     * 
     */
    public void testiArvot() {
        aika = "00;24,284";
        pvm = "18.3.2024";
        kellonaika = "15:54";
        dnf = false;
        kaksiS = true;
    }
    
    /**
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
     * @param os os
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @return id
     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }
    
    /**
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return aika
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