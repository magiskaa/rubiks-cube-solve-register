package fxrubkinkuutio;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valtteri
 * @version 20.3.2024
 *
 */
public class Sekoitukset {
    private final List<Sekoitus> alkiot = new ArrayList<Sekoitus>();
    
    /**
     * lisää sekoituksen listaan
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        alkiot.add(sek);
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
            if (sek.getRatkaisuId() == tunnusnro) loydetyt.add(sek);
        }
        return loydetyt;
    }
}
