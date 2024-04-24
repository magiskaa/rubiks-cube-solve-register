package fxrubkinkuutio;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Rekisteri
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Rekisteri {
    private Ratkaisut ratkaisut = new Ratkaisut();
    static Sekoitukset sekoitukset = new Sekoitukset();
    
    /**
     * palauttaa montako ratkaisua on
     * @return lkm
     */
    public int getRatkaisuja() {
        return ratkaisut.getLkm();
    }
    
    /**
     * poistaa ratkaisun
     * @param ratkaisu poistettava ratkaisu
     * @return poistettujen määrä
     */
    public int poista(Ratkaisu ratkaisu) {
        if (ratkaisu == null) return 0;
        int ret = ratkaisut.poista(ratkaisu.getId());
        sekoitukset.poistaSekoitukset(ratkaisu.getId());
        return ret;
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
     * @throws SailoException virhe
     */
    public void lisaa(Sekoitus sek) throws SailoException {
        sekoitukset.lisaa(sek);
    }
    
    /** 
     * Korvaa jäsenen tietorakenteessa.  Ottaa jäsenen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy, 
     * niin lisätään uutena jäsenenä. 
     * @param ratkaisu lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Ratkaisu ratkaisu) throws SailoException { 
        ratkaisut.korvaaTaiLisaa(ratkaisu); 
    }

    /** 
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy, 
     * niin lisätään uutena harrastuksena. 
     * @param sekoitus lisärtävän harrastuksen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Sekoitus sekoitus) throws SailoException { 
        sekoitukset.korvaaTaiLisaa(sekoitus); 
    }

    
    /**
     * @param hakuehto ni
     * @return ni
     * @throws SailoException virhe
     */
    public Collection<Ratkaisu> etsi(String hakuehto) throws SailoException {
        return ratkaisut.etsi(hakuehto);
    }
    
    
    public Collection<Ratkaisu> jarjesta(int k) {
        return ratkaisut.jarjesta(k);
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
     * @throws SailoException virhe
     */
    public List<Sekoitus> annaSekoitukset(Ratkaisu ratkaisu) throws SailoException {
        return sekoitukset.annaSekoitukset(ratkaisu.getSekoitusId());   
    }
    
    /**
     * @return sekoitukset
     */
    public Sekoitukset annaSek() {
        return sekoitukset;
    }
    
    /**
     * asettaa tiedostojen nimet
     * @param nimi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if (!nimi.isEmpty()) hakemistonNimi = nimi + "/";
        ratkaisut.setTiedostonNimi(hakemistonNimi + "ratkaisut");
        sekoitukset.setTiedostonNimi(hakemistonNimi + "sekoitukset");
    }
    
    /**
     * Lukee tiedot
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        ratkaisut = new Ratkaisut();
        sekoitukset = new Sekoitukset();
        
        setTiedosto(nimi); 
        ratkaisut.lueTiedostosta();
        sekoitukset.lueTiedostosta();
    }

    /**
     * välittää tallennukset ratkaisuihin ja sekoituksiin
     * @throws SailoException virhe
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            ratkaisut.tallenna();
        } catch (SailoException e) {
            virhe = e.getMessage();
        }
        
        try {
            sekoitukset.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
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
