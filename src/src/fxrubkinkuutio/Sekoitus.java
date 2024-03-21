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
     * muodostaja
     */
    public Sekoitus() {
        // ei vielä
    }
    
    /**
     * muodostaja
     * @param ratkaisuId ratkaisu id
     */
    public Sekoitus(int ratkaisuId) {
        this.ratkaisuId = ratkaisuId;
    }
    
    /**
     * antaa sekoitukselle testiarvot
     * @param nro ratkaisun numero
     */
    public void testiArvot(int nro) {
        ratkaisuId = nro;
        sekoitus = "L' D F2 D2 R F R' U2 L B2 U L F2 B' R U' R2 D' U2 B";        
    }
    
    /**
     * tulostaa sekoituksen
     * @param out mikä tietovirta
     */
    public void tulosta(PrintStream out) {
        out.println(sekoitus);
    }
    
    /**
     * tulostaa sekoituksen
     * @param os mikä tietovirta
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * rekisteröi sekoituksen ja palauttaa sen id:n
     * @return id
     * @example
     * <pre name="test">
     *   Sekoitus sek1 = new Sekoitus();
     *   sek1.getId() === 0;
     *   sek1.rekisteroi();
     *   Sekoitus sek2 = new Sekoitus();
     *   sek2.rekisteroi();
     *   int n1 = sek1.getId();
     *   int n2 = sek2.getId();
     *   n1 === n2-1;
     * </pre>

     */
    public int rekisteroi() {
        id = seuraavaNro;
        seuraavaNro++;
        return id;
    }
    
    /**
     * palauttaa sekoituksen id:n
     * @return sekoituksen id
     */
    public int getId() {
        return id;
    }
    
    /**
     * palauttaa sekoitukseen liittyvän ratkaisun id:n
     * @return ratkaisun id
     */
    public int getRatkaisuId() {
        return ratkaisuId;
    }
    
    /**
     * palauttaa sekoituksen
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
