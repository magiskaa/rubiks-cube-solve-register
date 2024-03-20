package fxrubkinkuutio;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Valtteri
 * @version 20.3.2024
 *
 */
public class Sekoitus {
    private int id;
    private int ratkaisuId;
    private String sekoitus;
    private static int seuraavaNro = 1;
    
    /**
     * 
     */
    public Sekoitus() {
        // ei vielä
    }
    
    /**
     * @param ratkaisuId ratkaisu id
     */
    public Sekoitus(int ratkaisuId) {
        this.ratkaisuId = ratkaisuId;
    }
    
    /**
     * @param nro number
     */
    public void testiArvot(int nro) {
        ratkaisuId = nro;
        sekoitus = "L' D F2 D2 R F R' U2 L B2 U L F2 B' R U' R2 D' U2 B";        
    }
    
    /**
     * @param out mikä virta
     */
    public void tulosta(PrintStream out) {
        out.println(sekoitus);
    }
    
    /**
     * @param os mikä virta
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @return id
     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    /**
     * @return sekoituksen id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return ratkaisun id
     */
    public int getRatkaisuId() {
        return ratkaisuId;
    }
    
    /**
     * @return sekoitus
     */
    public String getSekoitus() {
        return sekoitus;
    }
    
    /**
     * @param args ei
     */
    public static void main(String[] args) {
        Sekoitus sek = new Sekoitus();
        sek.testiArvot(seuraavaNro);
        sek.tulosta(System.out);
    }
    
}
