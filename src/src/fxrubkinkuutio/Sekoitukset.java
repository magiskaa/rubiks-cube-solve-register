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
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        alkiot.add(sek);
    }
    
    /**
     * @param tunnusnro ni
     * @return ni
     */
    public List<Sekoitus> annaSekoitukset(int tunnusnro) {
        List<Sekoitus> loydetyt = new ArrayList<Sekoitus>();
        for (Sekoitus sek : alkiot) {
            if (sek.getRatkaisuId() == tunnusnro) loydetyt.add(sek);
        }
        return loydetyt;
    }
}
