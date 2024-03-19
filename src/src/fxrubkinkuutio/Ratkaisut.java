package fxrubkinkuutio;

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
    
    /**
     * oletusmuodostaja
     */
    public Ratkaisut() {
        // ni
    }
    
    /**
     * @param ratkaisu ratkaisu
     * @throws SailoException virhe
     */
    public void lisaa(Ratkaisu ratkaisu) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = ratkaisu;
        lkm++;
    }
    
    /**
     * @param i indeksi
     * @return ratkaisu
     * @throws IndexOutOfBoundsException indeksi yli rajojen
     */
    public Ratkaisu anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * 
     * @param hakemisto hakemisto
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/ratkaisut.dat";
        throw new SailoException("Ei vielä osata lukea tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Tallentaa tiedostoon
     * @throws SailoException virhe
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }

    /**
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
