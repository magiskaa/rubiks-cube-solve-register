package fxrubkinkuutio;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Rekisteri
 * @author Valtteri
 * @version 18.3.2024
 *
 *
 ** Testien alustus
 * @example
 * <pre name="testJAVA">
 *  private Rekisteri rekisteri;
 *  private Ratkaisu rat1;
 *  private Ratkaisu rat2;
 *  private int rid1;
 *  private int rid2;
 *  private Sekoitus sek1;
 *  private Sekoitus sek2;
 *  
 *  public void alustaRekisteri() {
 *    rekisteri = new Rekisteri();
 *    rat1 = new Ratkaisu(); rat1.testiArvot(); rat1.rekisteroi();
 *    rat2 = new Ratkaisu(); rat2.testiArvot(); rat2.rekisteroi();
 *    rid1 = rat1.getId();
 *    rid2 = rat2.getId();
 *    sek1 = new Sekoitus(rid1); sek1.testiArvot(rid1); sek1.rekisteroi();
 *    sek2 = new Sekoitus(rid2); sek2.testiArvot(rid2); sek2.rekisteroi();
 *    try {
 *    rekisteri.lisaa(rat1);
 *    rekisteri.lisaa(rat2);
 *    rekisteri.lisaa(sek1);
 *    rekisteri.lisaa(sek2);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Rekisteri {
    private Ratkaisut ratkaisut = new Ratkaisut();
    private Sekoitukset sekoitukset = new Sekoitukset();
    
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
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaRekisteri();
     *   rekisteri.getRatkaisuja() === 2;
     *   rekisteri.poista(rat1) === 1;
     *   rekisteri.getRatkaisuja() === 1;
     * </pre>
     */
    public int poista(Ratkaisu ratkaisu) {
        if (ratkaisu == null) return 0;
        int ret = ratkaisut.poista(ratkaisu.getId());
        sekoitukset.poistaSekoitukset(ratkaisu.getSekoitusId());
        return ret;
    }
    
    /**
     * lisaa ratkaisun
     * @param ratkaisu ratkaisu
     * @throws SailoException virhe jos lisääminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaRekisteri();
     *  rekisteri.getRatkaisuja() === 2;
     *  rekisteri.lisaa(rat1);
     *  rekisteri.getRatkaisuja() === 3;
     * </pre>
     */
    public void lisaa(Ratkaisu ratkaisu) throws SailoException {
        ratkaisut.lisaa(ratkaisu);
    }
    
    /**
     * lisää sekoituksiin uuden sekoituksen
     * @param sek sekoitus
     * @throws SailoException virhe
     */
    public void lisaa(Sekoitus sek) throws SailoException {
        sekoitukset.lisaa(sek);
    }
    
    /**
     * lisää sekoituksiin uuden sekoituksen
     * @param sek sekoitus stringinä
     */
    public void lisaa(String sek) {
        sekoitukset.lisaaSekoitus(sek);
    }
    
    /** 
     * Korvaa ratkaisun tietorakenteessa.  Ottaa ratkaisun omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva ratkaisu.  Jos ei löydy, 
     * niin lisätään uutena ratkaisuna. 
     * @param ratkaisu lisättävän ratkaisun viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaRekisteri();
     *  rekisteri.getRatkaisuja() === 2;
     *  rekisteri.korvaaTaiLisaa(rat1);
     *  rekisteri.getRatkaisuja() === 2;
     * </pre>
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
     * palautta löydetyn sekoituksen tai uuden sekoituksen jos mitään ei löytynyt
     * @param sekoitus sekoitus stringinä
     * @return löydetty sekoitus tai uusi sekoitus
     */
    public Sekoitus etsiTaiLuoSekoitus(String sekoitus) {
        Sekoitus sek = sekoitukset.etsiTaiLuoSekoitus(sekoitus);
        if (sek == null) {
            sekoitukset.lisaaSekoitus(sekoitus);
            return sekoitukset.getSekoitus(sekoitukset.getLkm()-1);
        }
        return sek;
    }

    
    /**
     * etsii kaikki ratkaisut jotka sopivat hakuehtoon
     * @param hakuehto hakuehto
     * @return ratkaisut jotka sopivat hakuehtoon
     * @throws SailoException virhe
     * @example 
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   alustaRekisteri();
     *   Ratkaisu ratkaisu3 = new Ratkaisu(); ratkaisu3.rekisteroi();
     *   ratkaisu3.setAika("00;34,242");
     *   rekisteri.lisaa(ratkaisu3);
     *   Collection<Ratkaisu> loytyneet = rekisteri.etsi("*34*");
     *   loytyneet.size() === 1;
     *   Iterator<Ratkaisu> it = loytyneet.iterator();
     *   it.next() == ratkaisu3 === true; 
     * </pre>
     */
    public Collection<Ratkaisu> etsi(String hakuehto) throws SailoException {
        return ratkaisut.etsi(hakuehto);
    }
    
    
    /**
     * palauttaa collectionin jossa ratkaisut ovat halutussa järjestyksessä
     * @param k minkä mukaan järjestetään
     * @return ratkaisut järjestettynä
     */
    public Collection<Ratkaisu> jarjesta(int k) {
        return ratkaisut.jarjesta(k);
    }
    
    
    /**
     * palauttaa viitteen ratkaisuun indeksissä i
     * @param i indeksi
     * @return viite ratkaisuun
     * @throws IndexOutOfBoundsException virhe jos indeksi on yli rajojen
     */
    public Ratkaisu annaRatkaisu(int i) throws IndexOutOfBoundsException {
        return ratkaisut.anna(i);
    }
    
    /**
     * palauttaa sekoituksen stringinä
     * @param i indeksi
     * @return sekoitus stringinä
     */
    public String annaSekoitus(int i) {
        Ratkaisu ratkaisu = annaRatkaisu(i);
        return sekoitukset.getSekoitusString(ratkaisu.getSekoitusId());
    }
    
    /**
     * palauttaa viitteen sekoitukseen
     * @param ratkaisu minkä ratkaisun sekoitus
     * @return viitteen sekoitukseen
     */
    public Sekoitus annaSekoitus(Ratkaisu ratkaisu) {
        return sekoitukset.getSekoitus(ratkaisu.getSekoitusId());
    }
    
    /**
     * palauttaa viitteen ratkaisuun liittyvään sekoitukseen
     * @param ratkaisu ratkaisu
     * @return viite sekoitukseen
     * @throws SailoException virhe
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  alustaRekisteri();
     *  List<Sekoitus> loytyneet;
     *  loytyneet = rekisteri.annaSekoitukset(rat1);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == sek1 === true;
     * </pre> 
     */
    public List<Sekoitus> annaSekoitukset(Ratkaisu ratkaisu) throws SailoException {
        return sekoitukset.annaSekoitukset(ratkaisu.getSekoitusId());   
    }
    
    /**
     * palauttaa sekoitukset
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
     * Lukee tiedot tiedostosta
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
     * @param args ei käytössä
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
