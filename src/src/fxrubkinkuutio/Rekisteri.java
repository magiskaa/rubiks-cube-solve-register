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
     * @return lkm
     */
    public int getRatkaisuja() {
        return ratkaisut.getLkm();
    }
    
    /**
     * @param nro numero
     * @return poistettujen määrä
     */
    public int poista(@SuppressWarnings("unused") int nro) {
        return 0;
    }
    
    /**
     * @param ratkaisu ratkaisu
     * @throws SailoException ni
     */
    public void lisaa(Ratkaisu ratkaisu) throws SailoException {
        ratkaisut.lisaa(ratkaisu);
    }
    
    /**
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        sekoitukset.lisaa(sek);
    }
    
    /**
     * @param i indeksi
     * @return viite
     * @throws IndexOutOfBoundsException virhe
     */
    public Ratkaisu annaRatkaisu(int i) throws IndexOutOfBoundsException {
        return ratkaisut.anna(i);
    }
    
    /**
     * @param ratkaisu ratkaisu
     * @return ni
     */
    public List<Sekoitus> annaSekoitukset(Ratkaisu ratkaisu) {
        return sekoitukset.annaSekoitukset(ratkaisu.getId());   
    }
    
    /**
     * Lukee tiedot
     * @param nimi jota käyteään lukemisessa
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
