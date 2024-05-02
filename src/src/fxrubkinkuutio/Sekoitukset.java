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
    
    private ArrayList<Sekoitus> alkiot = new ArrayList<Sekoitus>();
    
    /**
     * lisää sekoituksen listaan
     * @param sek sekoitus
     */
    public void lisaa(Sekoitus sek) {
        alkiot.add(sek);
        muutettu = true;
    }
    
    /**
     * lisää sekoituksen listaan
     * @param sekoitus sekoitus stringinä
     */
    public void lisaaSekoitus(String sekoitus) {
        Sekoitus uusi = new Sekoitus(sekoitus);
        uusi.rekisteroi();
        alkiot.add(uusi);
        muutettu = true;
    }
    
    /**
     * Korvaa sekoituksen tietorakenteessa.  Ottaa sekoituksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva sekoitus.  Jos ei löydy,
     * niin lisätään uutena sekoituksena.
     * @param sekoitus lisättävän sekoituksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Sekoitukset sekoitukset = new Sekoitukset();
     * Sekoitus sek1 = new Sekoitus(), sek2 = new Sekoitus();
     * sek1.rekisteroi(); sek2.rekisteroi();
     * sekoitukset.getLkm() === 0;
     * sekoitukset.korvaaTaiLisaa(sek1); sekoitukset.getLkm() === 1;
     * sekoitukset.korvaaTaiLisaa(sek2); sekoitukset.getLkm() === 2;
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
     * palauttaa viitten sekoitukseen jos samanlainen on jo olemassa, luo uuden jos ei 
     * @param sekoitus sekoitus stringinä
     * @return viite sekoitukseen jos sekoitus löytyy, null jos ei
     */
    public Sekoitus etsiTaiLuoSekoitus(String sekoitus) {
        for (int i = 0; i < alkiot.size(); i++) {
            if (alkiot.get(i).getSekoitus().equals(sekoitus)) {
                return alkiot.get(i);
            }
        }
        return null;
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
     *  Sekoitus sek1 = new Sekoitus(); sek1.testiArvot(1);
     *  Sekoitus sek2 = new Sekoitus(); sek2.testiArvot(2);
     *  sekoitukset.lisaa(sek1);
     *  sekoitukset.lisaa(sek2);
     *  sekoitukset.poista(sek2) === true;   sekoitukset.getLkm() === 1;
     *  List<Sekoitus> h = sekoitukset.annaSekoitukset(1);
     *  h.size() === 1;
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
     */
    public int poistaSekoitukset(int tunnusNro) {
        int n = 0;
        for (Iterator<Sekoitus> it = alkiot.iterator(); it.hasNext();) {
            Sekoitus sek = it.next();
            if ( sek.getId() == tunnusNro ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }

    
    
    /**
     * lukee tiedot tiedostosta
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
     * lukee tiedot tiedostosta
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
     * palauttaa viitteen sekoitukseen indeksissä i
     * @param i indeksi
     * @return viite sekoitukseen
     */
    public Sekoitus getSekoitus(int i) {
        return alkiot.get(i);
    }
    
    /**
     * palauttaa indeksissä i olevan sekoituksen stringinä
     * @param i indeksi
     * @return sekoituksen stringinä
     */
    public String getSekoitusString(int i) {
        return getSekoitus(i).getSekoitus();
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
     *  Sekoitus sek1 = new Sekoitus(); sek1.rekisteroi() ; sekoitukset.lisaa(sek1); 
     *  Sekoitus sek2 = new Sekoitus(); sek2.rekisteroi() ; sekoitukset.lisaa(sek2);
     *  Sekoitus sek3 = new Sekoitus(); sek3.rekisteroi() ; sekoitukset.lisaa(sek3);
     *  Sekoitus sek4 = new Sekoitus(); sek4.rekisteroi() ; sekoitukset.lisaa(sek4);
     *  
     *  List<Sekoitus> loytyneet;
     *  loytyneet = sekoitukset.annaSekoitukset(1);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == sek1 === true;
     *  loytyneet = sekoitukset.annaSekoitukset(2);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == sek2 === true;
     * </pre> 
     */
    public List<Sekoitus> annaSekoitukset(int tunnusnro) {
        List<Sekoitus> loydetyt = new ArrayList<Sekoitus>();
        for (Sekoitus sek : alkiot) {
            if (sek.getId() == tunnusnro) loydetyt.add(sek);
        }
        if (loydetyt.isEmpty()) {
            Sekoitus sekoitus = new Sekoitus("");
            sekoitus.rekisteroi();
            lisaa(sekoitus);
            loydetyt.add(sekoitus);
        }
        return loydetyt;
    }
}
