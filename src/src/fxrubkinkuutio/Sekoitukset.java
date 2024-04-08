    package fxrubkinkuutio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Valtteri
 * @version 20.3.2024
 *
 */
public class Sekoitukset {
    private boolean muutettu = false;
    private String tiedostonNimi = "";
    
    private final List<Sekoitus> alkiot = new ArrayList<Sekoitus>();
    
    /**
     * lisää sekoituksen listaan
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        alkiot.add(sek);
        muutettu = true;
    }
    
    
    /**
     * @param tied tiedosto josta luetaan
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonNimi(tied);
        try (BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            String rivi;
            while ((rivi = fi.readLine()) != null) {
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Sekoitus sek = new Sekoitus();
                sek.parse(rivi);
                lisaa(sek);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
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
     * @param tied tiedoston nimi
     */
    public void setTiedostonNimi(String tied) {
        tiedostonNimi = tied;
    }
    
    /**
     * palauttaa ratkaisuun liittyvän sekoituksen
     * @param tunnusnro ratkaisun id
     * @return sekoitus
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Sekoitukset sekoitukset = new Sekoitukset();
     *  Sekoitus sek11 = new Sekoitus(); sekoitukset.lisaa(sek11);
     *  Sekoitus sek21 = new Sekoitus(); sekoitukset.lisaa(sek21);
     *  Sekoitus sek22 = new Sekoitus(); sekoitukset.lisaa(sek22);
     *  Sekoitus sek12 = new Sekoitus(); sekoitukset.lisaa(sek12);
     *  
     *  List<Sekoitus> loytyneet;
     *  loytyneet = sekoitukset.annaSekoitukset(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = sekoitukset.annaSekoitukset(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == sek11 === true;
     *  loytyneet.get(1) == sek21 === true;
     *  loytyneet = sekoitukset.annaHarrastukset(2);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == sek22 === true;
     * </pre> 

     */
    public List<Sekoitus> annaSekoitukset(int tunnusnro) {
        List<Sekoitus> loydetyt = new ArrayList<Sekoitus>();
        for (Sekoitus sek : alkiot) {
            if (sek.getId() == tunnusnro) loydetyt.add(sek);
        }
        return loydetyt;
    }
}
