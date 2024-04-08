package fxrubkinkuutio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ratkaisut
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisut {
    private static final int MAX_RATKAISUJA = 1000;
    private int lkm = 0;
    private String tiedostonNimi = "";
    private Ratkaisu alkiot[] = new Ratkaisu[MAX_RATKAISUJA];
    private boolean muutettu = false;
    
    /**
     * oletusmuodostaja
     */
    public Ratkaisut() {
        // ei vielä
    }
    
    /**
     * lisää ratkaisun listaan
     * @param ratkaisu ratkaisu
     * @throws SailoException virhe jos liikaa alkioita (>1000)
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Ratkaisut ratkaisut = new Ratkaisut();
     * Ratkaisu eka = new Ratkaisu(), toka = new Ratkaisu();
     * ratkaisut.getLkm() === 0;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 1;
     * ratkaisut.lisaa(toka); ratkaisut.getLkm() === 2;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 3;
     * ratkaisut.anna(0) === eka;
     * ratkaisut.anna(1) === toka;
     * ratkaisut.anna(2) === eka;
     * ratkaisut.anna(1) == eka === false;
     * ratkaisut.anna(1) == toka === true;
     * ratkaisut.anna(3) === eka; #THROWS IndexOutOfBoundsException 
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 4;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 5;
     * ratkaisut.lisaa(eka);  #THROWS SailoException
     * </pre>

     */
    public void lisaa(Ratkaisu ratkaisu) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = ratkaisu;
        lkm++;
        muutettu = true;
    }
    
    /**
     * palauttaa indeksissä olevan ratkaisun
     * @param i indeksi
     * @return ratkaisu
     * @throws IndexOutOfBoundsException indeksi yli rajojen
     */
    public Ratkaisu anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * lukee tiedostosta 
     * @param tied tiedosto
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            String rivi = fi.readLine();
            while ((rivi = fi.readLine()) != null) {
                rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Ratkaisu ratkaisu = new Ratkaisu();
                ratkaisu.parse(rivi);
                lisaa(ratkaisu);
            }
            muutettu = false;
        } catch ( FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * @throws SailoException virhe
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Tallentaa tiedostoon
     * @throws SailoException ei vielä osata tallettaa tiedostoa
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }

    
    /**
     * @return tiedoston nimi + .dat
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }
    
    /**
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
    
    /**
     * @param tied tiedosto
     */
    public void setTiedostonNimi(String tied) {
        tiedostonNimi = tied;
    }
    
    /**
     * palauttaa ratkaisujen lukumäärän
     * @return lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param args ei
     */
    public static void main(String[] args) {
        Ratkaisut ratkaisut = new Ratkaisut();
        Ratkaisu eka = new Ratkaisu();
        Ratkaisu toka = new Ratkaisu();
        eka.rekisteroi();
        eka.testiArvot();
        toka.rekisteroi();
        toka.testiArvot();
        
        try {
            ratkaisut.lisaa(eka);
            ratkaisut.lisaa(toka);
            
            System.out.println("======= RATKAISUT TESTI =======");
            
            for (int i = 0; i < ratkaisut.getLkm(); i++) {
                Ratkaisu ratkaisu = ratkaisut.anna(i);
                System.out.println("Ratkaisu nro: " + i);
                ratkaisu.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
