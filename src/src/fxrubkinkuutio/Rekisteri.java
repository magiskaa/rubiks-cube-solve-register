package fxrubkinkuutio;

import java.util.List;

/**
 * Rekisteri
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Rekisteri {
    private final Ratkaisut ratkaisut = new Ratkaisut();
    private final Sekoitukset sekoitukset = new Sekoitukset();
    
    /**
     * palauttaa montako ratkaisua on
     * @return lkm
     */
    public int getRatkaisuja() {
        return ratkaisut.getLkm();
    }
    
    /**
     * poistaa ratkaisun
     * @param nro ratkaisun id
     * @return poistettujen määrä
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
    
    /**
     * lisaa ratkaisun
     * @param ratkaisu ratkaisu
     * @throws SailoException virhe jos lisääminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Rekisteri rekisteri = new Rekisteri();
     * Ratkaisu eka = new Ratkaisu(), toka = new Ratkaisu();
     * eka.rekisteroi(); toka.rekisteroi();
     * rekisteri.getRatkaisuja() === 0;
     * rekisteri.lisaa(eka); rekisteri.getRatkaisuja() === 1;
     * rekisteri.lisaa(toka); rekisteri.getRatkaisuja() === 2;
     * rekisteri.lisaa(eka); rekisteri.getRatkaisuja() === 3;
     * rekisteri.getRatkaisuja() === 3;
     * rekisteri.annaRatkaisu(0) === eka;
     * rekisteri.annaRatkaisu(1) === toka;
     * rekisteri.annaRatkaisu(2) === eka;
     * rekisteri.annaRatkaisu(3) === eka; #THROWS IndexOutOfBoundsException 
     * rekisteri.lisaa(eka); rekisteri.getRatkaisuja() === 4;
     * rekisteri.lisaa(eka); rekisteri.getRatkaisuja() === 5;
     * rekisteri.lisaa(eka);            #THROWS SailoException
     * </pre>

     */
    public void lisaa(Ratkaisu ratkaisu) throws SailoException {
        ratkaisut.lisaa(ratkaisu);
    }
    
    /**
     * lisää sekoituksen
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        sekoitukset.lisaa(sek);
    }
    
    /**
     * palauttaa viitteen ratkaisuun indeksissä i
     * @param i indeksi
     * @return viite
     * @throws IndexOutOfBoundsException virhe jos indeksi on yli rajojen
     */
    public Ratkaisu annaRatkaisu(int i) throws IndexOutOfBoundsException {
        return ratkaisut.anna(i);
    }
    
    /**
     * palauttaa viitteen ratkaisuun liittyvään sekoitukseen
     * @param ratkaisu ratkaisu
     * @return viite sekoitukseen
     */
    public List<Sekoitus> annaSekoitukset(Ratkaisu ratkaisu) {
        return sekoitukset.annaSekoitukset(ratkaisu.getId());   
    }
    
    /**
     * Lukee tiedot
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        ratkaisut.lueTiedostosta(nimi);
    }

    /**
     * Tallettaa tiedot
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        ratkaisut.talleta();
    }

    /**
     * @param args ei
     */
    public static void main(String[] args) {
        Rekisteri rekisteri = new Rekisteri();
        
        try {
            Ratkaisu eka = new Ratkaisu();
            Ratkaisu toka = new Ratkaisu();
            
            eka.rekisteroi();
            eka.testiArvot();
            toka.rekisteroi();
            toka.testiArvot();
            
            rekisteri.lisaa(eka);
            rekisteri.lisaa(toka);
            
            System.out.println("======= REKISTERIN TESTI =======");
            
            for (int i = 0; i < rekisteri.getRatkaisuja(); i++) {
                Ratkaisu ratkaisu = rekisteri.annaRatkaisu(i);
                System.out.println("Ratkaisu paikassa: " + i);
                ratkaisu.tulosta(System.out);
            }
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
