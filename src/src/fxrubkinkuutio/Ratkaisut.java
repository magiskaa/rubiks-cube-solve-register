package fxrubkinkuutio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Ratkaisut
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisut implements Iterable<Ratkaisu> {
    private int lkm = 0;
    private String tiedostonNimi = "ratkaisut";
    private Ratkaisu alkiot[] = new Ratkaisu[0];
    static boolean muutettu = false;
    
    /**
     * oletusmuodostaja
     */
    public Ratkaisut() {
        // ei vielä
    }
    
    /**
     * lisää ratkaisun listaan
     * @param ratkaisu ratkaisu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.util.*;
     * Ratkaisut ratkaisut = new Ratkaisut();
     * Ratkaisu rat1 = new Ratkaisu(), rat2 = new Ratkaisu();
     * ratkaisut.getLkm() === 0;
     * ratkaisut.lisaa(rat1); ratkaisut.getLkm() === 1;
     * ratkaisut.lisaa(rat2); ratkaisut.getLkm() === 2;
     * ratkaisut.lisaa(rat1); ratkaisut.getLkm() === 3;
     * Iterator<Ratkaisu> it = ratkaisut.iterator(); 
     * it.next() === rat1;
     * it.next() === rat2; 
     * it.next() === rat1;  
     * ratkaisut.lisaa(rat1); ratkaisut.getLkm() === 4;
     * ratkaisut.lisaa(rat1); ratkaisut.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Ratkaisu ratkaisu) {
        if (lkm >= alkiot.length) {
            Ratkaisu[] uudet = new Ratkaisu[lkm+1];
            for (int i = 0; i < alkiot.length; i++) {
                uudet[i] = alkiot[i];
            }
            uudet[lkm] = ratkaisu;
            alkiot = uudet;
            lkm++;
            muutettu = true;
        }
    }
    
    /**
     * Korvaa ratkaisun tietorakenteessa.  Ottaa ratkaisun omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva ratkaisu.  Jos ei löydy,
     * niin lisätään uutena ratkaisuna.
     * @param ratkaisu lisätäävän ratkaisun viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Ratkaisut ratkaisut = new Ratkaisut();
     * Ratkaisu rat1 = new Ratkaisu(), rat2 = new Ratkaisu();
     * rat1.rekisteroi(); rat2.rekisteroi();
     * ratkaisut.getLkm() === 0;
     * ratkaisut.korvaaTaiLisaa(rat1); ratkaisut.getLkm() === 1;
     * ratkaisut.korvaaTaiLisaa(rat2); ratkaisut.getLkm() === 2;
     * Ratkaisu rat3 = rat1.clone();
     * rat3.setKellonaika("00.30");
     * Iterator<Ratkaisu> it = ratkaisut.iterator();
     * it.next() == rat1 === true;
     * ratkaisut.korvaaTaiLisaa(rat3); ratkaisut.getLkm() === 2;
     * it = ratkaisut.iterator();
     * Ratkaisu j0 = it.next();
     * j0 === rat3;
     * j0 == rat3 === true;
     * j0 == rat1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Ratkaisu ratkaisu) throws SailoException {
        int id = ratkaisu.getId();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getId() == id ) {
                alkiot[i] = ratkaisu;
                muutettu = true;
                return;
            }
        }
        lisaa(ratkaisu);
    }
    
    /** 
     * Poistaa ratkaisun jolla on valittu tunnusnumero  
     * @param id poistettavan ratkaisun tunnusnumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Ratkaisut ratkaisut = new Ratkaisut(); 
     * Ratkaisu rat1 = new Ratkaisu(), rat2 = new Ratkaisu(), rat3 = new Ratkaisu(); 
     * rat1.rekisteroi(); rat2.rekisteroi(); rat3.rekisteroi(); 
     * int id1 = rat1.getId(); 
     * ratkaisut.lisaa(rat1); ratkaisut.lisaa(rat2); ratkaisut.lisaa(rat3); 
     * ratkaisut.poista(id1+1) === 1; 
     * ratkaisut.annaId(id1+1) === null; ratkaisut.getLkm() === 2; 
     * ratkaisut.poista(id1) === 1; ratkaisut.getLkm() === 1; 
     * ratkaisut.poista(id1+3) === 0; ratkaisut.getLkm() === 1; 
     * </pre> 
     *  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    
    /** 
     * Etsii ratkaisun id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return ratkaisu jolla etsittävä id tai null 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Ratkaisut ratkaisut = new Ratkaisut(); 
     * Ratkaisu rat1 = new Ratkaisu(), rat2 = new Ratkaisu(), rat3 = new Ratkaisu(); 
     * rat1.rekisteroi(); rat2.rekisteroi(); rat3.rekisteroi(); 
     * int id1 = rat1.getId(); 
     * ratkaisut.lisaa(rat1); ratkaisut.lisaa(rat2); ratkaisut.lisaa(rat3); 
     * ratkaisut.annaId(id1  ) == rat1 === true; 
     * ratkaisut.annaId(id1+1) == rat2 === true; 
     * ratkaisut.annaId(id1+2) == rat3 === true; 
     * </pre> 
     */ 
    public Ratkaisu annaId(int id) { 
        for (Ratkaisu ratkaisu : this) { 
            if (id == ratkaisu.getId()) return ratkaisu; 
        } 
        return null; 
    } 

    
    /** 
     * Etsii ratkaisun id:n perusteella 
     * @param id tunnusnumero, jonka mukaan etsitään 
     * @return löytyneen ratkaisun indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Ratkaisut ratkaisut = new Ratkaisut(); 
     * Ratkaisu rat1 = new Ratkaisu(), rat2 = new Ratkaisu(), rat3 = new Ratkaisu(); 
     * rat1.rekisteroi(); rat2.rekisteroi(); rat3.rekisteroi(); 
     * int id1 = rat1.getId(); 
     * ratkaisut.lisaa(rat1); ratkaisut.lisaa(rat2); ratkaisut.lisaa(rat3); 
     * ratkaisut.etsiId(id1+1) === 1; 
     * ratkaisut.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getId()) return i; 
        return -1; 
    } 

    
    /**
     * etsii ratkaisuista ne ratkaisut jotka sopivat hakuehtoon
     * @param hakuehto hakuehto jolla haetaan
     * @return ratkaisut jotka toteuttavat hakuehdon
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Ratkaisut ratkaisut = new Ratkaisut(); 
     *   Ratkaisu ratkaisu1 = new Ratkaisu(); ratkaisu1.parse("1|00;43,348|03.04.2024|07.24|F|F|1"); 
     *   Ratkaisu ratkaisu2 = new Ratkaisu(); ratkaisu2.parse("2|00;36,345||13.01|F|F|2"); 
     *   Ratkaisu ratkaisu3 = new Ratkaisu(); ratkaisu3.parse("3|00;24,256|03.04.2024|21.35|F|F|3"); 
     *   Ratkaisu ratkaisu4 = new Ratkaisu(); ratkaisu4.parse("4|00;35,635|03.04.2024||F|T|4"); 
     *   Ratkaisu ratkaisu5 = new Ratkaisu(); ratkaisu5.parse("5|00;22,136|03.04.2024|18.32|T|F|5"); 
     *   ratkaisut.lisaa(ratkaisu1); ratkaisut.lisaa(ratkaisu2); ratkaisut.lisaa(ratkaisu3); ratkaisut.lisaa(ratkaisu4); ratkaisut.lisaa(ratkaisu5);
     *   List<Ratkaisu> loytyneet;  
     *   loytyneet = (List<Ratkaisu>)ratkaisut.etsi("*13*");  
     *   loytyneet.size() === 2;  
     *   loytyneet.get(0) == ratkaisu2 === true;
     *   loytyneet.get(1) == ratkaisu5 === true;
     *     
     *   loytyneet = (List<Ratkaisu>)ratkaisut.etsi("*256*");  
     *   loytyneet.size() === 1;  
     *   loytyneet.get(0) == ratkaisu3 === true;
     * </pre> 
     */
    public Collection<Ratkaisu> etsi(String hakuehto) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        List<Ratkaisu> loytyneet = new ArrayList<Ratkaisu>();
        for (Ratkaisu ratkaisu : this) {
            if (WildChars.onkoSamat(ratkaisu.annappa(0), ehto)) {
                loytyneet.add(ratkaisu);
            }
            else if (WildChars.onkoSamat(ratkaisu.annappa(1), ehto)) {
                loytyneet.add(ratkaisu);
            }
            else if (WildChars.onkoSamat(ratkaisu.annappa(2), ehto)) {
                loytyneet.add(ratkaisu);
            }
            else if (WildChars.onkoSamat(ratkaisu.annappa(3), ehto)) {
                loytyneet.add(ratkaisu);
            }
            else if (WildChars.onkoSamat(ratkaisu.annappa(4), ehto)) {
                loytyneet.add(ratkaisu);
            }
            else if (WildChars.onkoSamat(ratkaisu.annappa(5), ehto)) {
                loytyneet.add(ratkaisu);
            }
        }
        return loytyneet;
    }

    /**
     * järjestää ratkaisut tiettyyn järjestykseen ja palauttaa ne
     * @param k minkä mukaan järjestetään
     * @return palauttaa ratkaisut järjestettynä
     */
    public Collection<Ratkaisu> jarjesta(int k) {
        List<Ratkaisu> loytyneet = new ArrayList<Ratkaisu>();
        for (Ratkaisu ratkaisu : this) {
            loytyneet.add(ratkaisu);
        }
        switch (k) {
        case 0: Collections.sort(loytyneet, new Ratkaisu.Vertailija(0)); break;
        case 1: Collections.sort(loytyneet, new Ratkaisu.Vertailija(1)); break;
        case 2: Collections.sort(loytyneet, new Ratkaisu.Vertailija(2)); break;
        default: Collections.sort(loytyneet, new Ratkaisu.Vertailija(1)); break;
        }
        return loytyneet;
    }
    
    /**
     * palauttaa indeksissä olevan ratkaisun
     * @param i indeksi
     * @return ratkaisu
     * @throws IndexOutOfBoundsException indeksi yli rajojen
     */
    protected Ratkaisu anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * lukee tiedot tiedostosta 
     * @param tied tiedosto
     * @throws SailoException virhe
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))) {
            String rivi = fi.readLine();
            while ((rivi = fi.readLine()) != null) {
                rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Ratkaisu ratkaisu = new Ratkaisu();
                ratkaisu.parse(rivi);
                lisaa(ratkaisu);
            }
            muutettu = false;
        } catch ( FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e) {
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
     * Tallentaa muutokset tiedostoon
     * @throws SailoException ei vielä osata tallettaa tiedostoa
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try (PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()))) {
            fo.println(alkiot.length);
            for (Ratkaisu ratkaisu : this) {
                fo.println(ratkaisu.toString());
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
     * @param tied tiedosto
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
     * palauttaa ratkaisujen lukumäärän
     * @return lkm
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @author Valtteri
     * @version 9.4.2024
     *
     */
    public class RatkaisutIterator implements Iterator<Ratkaisu> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa ratkaisua
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä ratkaisuja
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava ratkaisu
         * @return seuraava ratkaisu
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Ratkaisu next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }

    
    @Override
    public Iterator<Ratkaisu> iterator() {
        return new RatkaisutIterator();
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Ratkaisut ratkaisut = new Ratkaisut();
        Ratkaisu eka = new Ratkaisu();
        Ratkaisu toka = new Ratkaisu();
        eka.rekisteroi();
        eka.testiArvot();
        toka.rekisteroi();
        toka.testiArvot();
        
        ratkaisut.lisaa(eka);
        ratkaisut.lisaa(toka);
        
        System.out.println("======= RATKAISUT TESTI =======");
        
        for (int i = 0; i < ratkaisut.getLkm(); i++) {
            Ratkaisu ratkaisu = ratkaisut.anna(i);
            System.out.println("Ratkaisu nro: " + i);
            ratkaisu.tulosta(System.out);
        }
    }
}
