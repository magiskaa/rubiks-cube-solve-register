package fxrubkinkuutio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Valtteri
 * @version 20.3.2024
 *
 */
public class Sekoitukset implements Iterable<Sekoitus> {
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
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy,
     * niin lisätään uutena harrastuksena.
     * @param sekoitus lisättävän harrastuksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Harrastukset harrastukset = new Harrastukset();
     * Harrastus har1 = new Harrastus(), har2 = new Harrastus();
     * har1.rekisteroi(); har2.rekisteroi();
     * harrastukset.getLkm() === 0;
     * harrastukset.korvaaTaiLisaa(har1); harrastukset.getLkm() === 1;
     * harrastukset.korvaaTaiLisaa(har2); harrastukset.getLkm() === 2;
     * Harrastus har3 = har1.clone();
     * har3.aseta(2,"kkk");
     * Iterator<Sekoitus> i2=harrastukset.iterator();
     * i2.next() === har1;
     * harrastukset.korvaaTaiLisaa(har3); harrastukset.getLkm() === 2;
     * i2=harrastukset.iterator();
     * Harrastus h = i2.next();
     * h === har3;
     * h == har3 === true;
     * h == har1 === false;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Sekoitus sekoitus) throws SailoException {
        int id = sekoitus.getId();
        for (int i = 0; i < getLkm(); i++) {
            if (alkiot.get(i).getId() == id) {
                alkiot.set(i, sekoitus);
                muutettu = true;
                return;
            }
        }
        lisaa(sekoitus);
    }

    
    /**
     * Poistaa valitun sekoituksen
     * @param sekoitus poistettava sekoitus
     * @return tosi jos löytyi poistettava tietue 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Sekoitukset sekoitukset = new Sekoitukset();
     *  Sekoitus sek21 = new Sekoitus(); sek21.testiArvot(2);
     *  Sekoitus sek11 = new Sekoitus(); sek11.testiArvot(1);
     *  sekoitukset.lisaa(sek23);
     *  sekoitukset.lisaa(sek11);
     *  sekoitukset.poista(sek23) === false ; sekoitukset.getLkm() === 2;
     *  sekoitukset.poista(sek11) === true;   sekoitukset.getLkm() === 1;
     *  List<Sekoitus> h = sekoitukset.annaSekoitukset(1);
     *  h.size() === 1; 
     *  h.get(0) === sek21;
     * </pre>
     */
    public boolean poista(Sekoitus sekoitus) {
        boolean ret = alkiot.remove(sekoitus);
        if (ret) muutettu = true;
        return ret;
    }

    
    /**
     * Poistaa tietyn ratkaisun sekoitukset
     * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
     * @return montako poistettiin 
     * @example
     * <pre name="test">
     *  Sekoitukset sekoitukset = new Sekoitukset();
     *  Sekoitus sek21 = new Sekoitus(); sek21.testiArvot(2);
     *  Sekoitus sek11 = new Sekoitus(); sek11.testiArvot(1);
     *  sekoitukset.lisaa(sek21);
     *  sekoitukset.lisaa(sek11);
     *  sekoitukset.poistaSekoitukset(1) === 1;  sekoitukset.getLkm() === 2;
     *  sekoitukset.poistaSekoitukset(1) === 0;  sekoitukset.getLkm() === 2;
     *  List<Sekoitus> h = sekoitukset.annaSekoitukset(2);
     *  h.size() === 0; 
     *  h = sekoitukset.annaSekoitukset(1);
     *  h.get(0) === sek21;
     *  h.get(1) === sek11;
     * </pre>
     */
    public int poistaSekoitukset(int tunnusNro) {
        int n = 0;
        for (Iterator<Sekoitus> it = alkiot.iterator(); it.hasNext();) {
            Sekoitus sek = it.next();
            if ( sek.getRatkaisuId() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }

    
    
    /**
     * lukee tiedostosta
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
     * lukee tiedostosta
     * @throws SailoException virhe
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * tallentaa tiedostoon muutokset
     * @throws SailoException virhe
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) { 
            for (Sekoitus sek : this) {
                fo.println(sek.toString());
            }
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    /**
     * palauttaa tiedoston nimen
     * @return tiedoston nimi + .dat
     */
    public String getTiedostonNimi() {
        return tiedostonNimi + ".dat";
    }
    
    /**
     * palauttaa tiedoston nimen
     * @return tiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
    
    /**
     * asettaa tiedoston nimen
     * @param tied tiedoston nimi
     */
    public void setTiedostonNimi(String tied) {
        tiedostonNimi = tied;
    }
    
    /**
     * palauttaa tiedoston bak nimen
     * @return tiedoston nimi + .bak
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }
    
    /**
     * Palauttaa rekisterin sekoitusten lukumäärän
     * @return sekoitusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }

    
    
    @Override
    public Iterator<Sekoitus> iterator() {
        return alkiot.iterator();
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
