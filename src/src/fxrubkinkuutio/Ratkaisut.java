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
import java.util.Iterator;
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
     * Ratkaisut ratkaisut = new Ratkaisut();
     * Ratkaisu eka = new Ratkaisu(), toka = new Ratkaisu();
     * ratkaisut.getLkm() === 0;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 1;
     * ratkaisut.lisaa(toka); ratkaisut.getLkm() === 2;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 3;
     * ratkaisut.anna(0) === eka;
     * ratkaisut.anna(1) === toka;
     * ratkaisut.anna(2) === eka;
     * ratkaisut.anna(1) == eka === false;
     * ratkaisut.anna(1) == toka === true;
     * ratkaisut.anna(3) === eka; #THROWS IndexOutOfBoundsException 
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 4;
     * ratkaisut.lisaa(eka); ratkaisut.getLkm() === 5;
     * ratkaisut.lisaa(eka);  #THROWS SailoException
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
     * Korvaa jäsenen tietorakenteessa.  Ottaa jäsenen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva jäsen.  Jos ei löydy,
     * niin lisätään uutena jäsenenä.
     * @param ratkaisu lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Jasenet jasenet = new Jasenet();
     * Jasen aku1 = new Jasen(), aku2 = new Jasen();
     * aku1.rekisteroi(); aku2.rekisteroi();
     * jasenet.getLkm() === 0;
     * jasenet.korvaaTaiLisaa(aku1); jasenet.getLkm() === 1;
     * jasenet.korvaaTaiLisaa(aku2); jasenet.getLkm() === 2;
     * Jasen aku3 = aku1.clone();
     * aku3.setPostinumero("00130");
     * Iterator<Jasen> it = jasenet.iterator();
     * it.next() == aku1 === true;
     * jasenet.korvaaTaiLisaa(aku3); jasenet.getLkm() === 2;
     * it = jasenet.iterator();
     * Jasen j0 = it.next();
     * j0 === aku3;
     * j0 == aku3 === true;
     * j0 == aku1 === false;
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
     * @param hakuehto hakuehto jolla haetaan
     * @return ratkaisut jotka toteuttavat hakuehdon
     */
    public Collection<Ratkaisu> etsi(String hakuehto) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        Collection<Ratkaisu> loytyneet = new ArrayList<Ratkaisu>();
        for (Ratkaisu ratkaisu : this) {
            if (WildChars.onkoSamat(ratkaisu.anna(0), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(1), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(2), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(3), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(4), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(5), ehto)) loytyneet.add(ratkaisu);
            else if (WildChars.onkoSamat(ratkaisu.anna(6), ehto)) loytyneet.add(ratkaisu);
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
     * lukee tiedostosta 
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
     * lukee tiedostosta
     * @throws SailoException virhe
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /**
     * Tallentaa tiedostoon
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
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
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
