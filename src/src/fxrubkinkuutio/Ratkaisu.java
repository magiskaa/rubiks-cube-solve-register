package fxrubkinkuutio;

import java.io.*;
import java.util.Comparator;
import java.util.List;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ratkaisu
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisu implements Cloneable {
    private int id = 1;
    private String aika = " ";
    private String pvm = " ";
    private String kellonaika = " ";
    private String dnf = "F";
    private String kaksiS = "F";
    private int sekoitusId = 1;
    private static int seuraavaId = 1;
    
    
    public int getKenttia() {
        return 7;
    }
    
    public int ekaKentta() {
        return 1;
    }
    
    public Ratkaisu() {
        //
    }
    
    /** 
     * Jäsenten vertailija 
     */ 
    public static class Vertailija implements Comparator<Ratkaisu> { 
        private int k;  
         
        @SuppressWarnings("javadoc") 
        public Vertailija(int k) { 
            this.k = k; 
        } 
         
        @Override 
        public int compare(Ratkaisu rat1, Ratkaisu rat2) { 
            return rat1.anna(k).compareToIgnoreCase(rat2.anna(k)); 
        } 
    } 

    
    /**
     * antaa testiarvot ratkaisulle
     */
    public void testiArvot() {
        aika = "00;24,284";
        pvm = "18.3.2024";
        kellonaika = "15:54";
        dnf = "F";
        kaksiS = "T";
        sekoitusId = 1;
    }
    
    
    /**
     * tulostaa ratkaisun tiedot
     * @param out ps
     */
    public void tulosta(PrintStream out) {
        out.println("Nro: " + id);
        out.println("Aika: " + aika);
        out.println("Päivämäärä: " + pvm);
        out.println("Kellonaika: " + kellonaika);
        out.println("DNF: " + dnf);
        out.println("+2s: " + kaksiS);
    }
    
    /**
     * tulostaa ratkaisun tiedot
     * @param os os
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * rekisteröi ratkaisun
     * @return ratkaisun id:n
     * @example
     * <pre name="test">
     *   Ratkaisu eka = new Ratkaisu();
     *   eka.getId() === 0;
     *   eka.rekisteroi();
     *   Ratkaisu toka = new Ratkaisu();
     *   toka.rekisteroi();
     *   int n1 = eka.getId();
     *   int n2 = toka.getId();
     *   n1 === n2-1;
     * </pre>

     */
    public int rekisteroi() {
        id = seuraavaId;
        seuraavaId++;
        return id;
    }
    
    /**
     * palauttaa ratkaisun id:n
     * @return id
     */
    public int getId() {
        return id;
    }
    
    public String getIdS() {
        return "" + id;
    }
    
    /**
     * asettaa ratkaisun id:n
     * @param nr numero
     */
    public void setId(int nr) {
        id = nr;
        if (id >= seuraavaId) seuraavaId = id + 1;
    }
    
    /**
     * palauttaa sekoituksen id:n
     * @return sekoitus id
     */
    public int getSekoitusId() {
        return sekoitusId;
    }
    
    /**
     * palauttaa ratkaisun ajan
     * @return aika
     * @example
     * <pre name="test">
     *   Ratkaisu eka = new Ratkaisu();
     *   eka.testiArvot();
     *   aku.getAika() === "00;24,284";
     * </pre>
     */
    public String getAika() {
        return aika;
    }
    
    /**
     * palauttaa ratkaisun päivämäärän
     * @return pvm
     */
    public String getPvm() {
        return pvm;
    }
    
    public String getKellonaika() {
        return kellonaika;
    }
    
    public String getDnf() {
        return dnf;
    }
    
    public String get2s() {
        return kaksiS;
    }
    
    public List<Sekoitus> getSekoitus() {
        return sekoitukset.annaSekoitukset(getSekoitusId());
    }
    
    Sekoitukset sekoitukset = Rekisteri.sekoitukset;
    
    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona 
     */ 
    public String anna(int k) {
        switch (k) {
        case 0: return "" + id;
        case 1: return "" + aika;
        case 2: return "" + pvm;
        case 3: return "" + kellonaika;
        case 4: return "" + dnf;
        case 5: return "" + kaksiS;
        case 6: return "" + naytaSekoitus(sekoitukset.annaSekoitukset(getSekoitusId()));
        default: return "";
        }
    }
    
    
    private String naytaSekoitus(List<Sekoitus> sek) {
        if (sek == null || sek.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(sek.getFirst().toString());
        Mjonot.erota(sb, '|');
        String sekoitus = Mjonot.erota(sb, '|');
        return sekoitus;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String erotin = "";
        for (int k = 0; k < getKenttia()-1; k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        sb.append(erotin);
        sb.append(sekoitusId);
        return sb.toString();
    }
    
    
    /**
     * @param k mikä kenttä
     * @param jono kentän syöte
     * @return virhe tai null
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
//        StringBuilder sb = new StringBuilder(tjono);
        switch (k) {
        case 0: 
            if (tjono.matches("")) return "Ratkaisun id:tä ei ole mahdollista muokata";
            return null;
        case 1:
            if (!tjono.matches("\\d{2};\\d{2},\\d{3}")) return "Ratkaisun ajan tulee olla muotoa '00;00,000'";
            aika = tjono;
            return null;
        case 2: 
            String virhe = "Päivämäärän tulee olla muotoa '00.00.0000'";
            if (!tjono.matches("\\d{2}.\\d{2}.\\d{4}")) return virhe;
            String eka = tjono.substring(0,2);
            String toka = tjono.substring(3,5);
            int n = Integer.valueOf(eka);
            if (n>31 || n<1) return virhe;
            n = Integer.valueOf(toka);
            if (n>12 || n<1) return virhe;
            pvm = tjono;
            return null;
        case 3:
            if (!tjono.matches("\\d{2}.\\d{2}")) return "Kellonajan tulee olla muotoa '00.00'";
            kellonaika = tjono;
            return null;
        case 4:
            if (tjono.matches("") || tjono.isEmpty()) return "DNF tulee olla muotoa 'T' tai 'F'";
            dnf = tjono;
            return null;
        case 5:
            if (tjono.matches("") || tjono.isEmpty()) return "+2s tulee olla muotoa 'T' tai 'F'";
            kaksiS = tjono;
            return null;
        case 6: 
            List<Sekoitus> sek = getSekoitus();
            return sek.getFirst().aseta(tjono);
        default:
            return null;
        }
    }
    
    
    public String setId(String s) {
        if (s.matches("")) return "Ratkaisun id:tä ei ole mahdollista muokata";
        return null;
    }
    
    public String setAika(String s) {
        if (!s.matches("\\d{2};\\d{2},\\d{3}")) return "Ratkaisun ajan tulee olla muotoa '00;00,000'";
        aika = s;
        return null;
    }   
    
    public String setPvm(String s) {
        String virhe = "Päivämäärän tulee olla muotoa '00.00.0000'";
        if (!s.matches("\\d{2}.\\d{2}.\\d{4}")) return virhe;
        String eka = s.substring(0,2);
        String toka = s.substring(3,5);
        int n = Integer.valueOf(eka);
        if (n>31 || n<1) return virhe;
        n = Integer.valueOf(toka);
        if (n>12 || n<1) return virhe;
        pvm = s;
        return null;
    }   
    
    public String setKellonaika(String s) {
        if (!s.matches("\\d{2}.\\d{2}")) return "Kellonajan tulee olla muotoa '00.00'";
        kellonaika = s;
        return null;
    }   
    
    public String setDnf(String s) {
        if (s.matches("") || s.isEmpty()) return "DNF tulee olla muotoa 'T' tai 'F'";
        dnf = s;
        return null;
    }   
    
    public String set2s(String s) {
        if (s.matches("") || s.isEmpty()) return "+2s tulee olla muotoa 'T' tai 'F'";
        kaksiS = s;
        return null;
    }   
    
    public String setSekoitus() {
        return null;
    }   
    
    
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Ratkaisun id";
        case 1: return "Aika:";
        case 2: return "Päivämäärä:";
        case 3: return "Kellonaika:";
        case 4: return "DNF:";
        case 5: return "+2s:";
        case 6: return "Sekoitus:";
        default: return "Eipä ollu";
        }
    }
    
    
    /**
     * erottaa tiedoston rivistä kaikki kohdat omiin muuttujiin
     * @param rivi tiedostosta luettu rivi
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setId(Mjonot.erota(sb, '|', getId()));
        aika = Mjonot.erota(sb, '|', aika);
        pvm = Mjonot.erota(sb, '|', pvm);
        kellonaika = Mjonot.erota(sb, '|', kellonaika);
        dnf = Mjonot.erota(sb, '|', dnf);
        kaksiS = Mjonot.erota(sb, '|', kaksiS);
        sekoitusId = Mjonot.erota(sb, '|', sekoitusId);
    }
    
    /**
     * Tehdään identtinen klooni ratkaisusta
     * @return Object kloonattu ratkaisu
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Ratkaisu ratkaisu = new Ratkaisu();
     *   ratkaisu.parse("   1  |  00;34,455  ");
     *   Ratkaisu kopio = ratkaisu.clone();
     *   kopio.toString() === ratkaisu.toString();
     *   ratkaisu.parse("   2  |  00;23,134  ");
     *   kopio.toString().equals(ratkaisu.toString()) === false;
     * </pre>
     */
    @Override
    public Ratkaisu clone() throws CloneNotSupportedException {
        Ratkaisu uusi;
        uusi = (Ratkaisu) super.clone();
        return uusi;
    }

    
    /**
     * Tutkii onko ratkaisun tiedot samat kuin parametrina tuodun ratkaisun tiedot
     * @param ratkaisu ratkaisu johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Ratkaisu ratkaisu1 = new Ratkaisu();
     *   ratkaisu1.parse("   3  |  00;34,345   | 12.03.2023");
     *   Ratkaisu ratkaisu2 = new Ratkaisu();
     *   ratkaisu2.parse("   3  |  00;34,345   | 12.03.2023");
     *   Ratkaisu ratkaisu3 = new Ratkaisu();
     *   ratkaisu3.parse("   3  |  00;34,345   | 11.03.2023");
     *   
     *   ratkaisu1.equals(ratkaisu2) === true;
     *   ratkaisu2.equals(ratkaisu1) === true;
     *   ratkaisu1.equals(ratkaisu3) === false;
     *   ratkaisu3.equals(ratkaisu2) === false;
     * </pre>
     */
    public boolean equals(Ratkaisu ratkaisu) {
        if ( ratkaisu == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(ratkaisu.anna(k)) ) return false;
        return true;
    }

    
    @Override
    public boolean equals(Object ratkaisu) {
        if ( ratkaisu instanceof Ratkaisu ) return equals((Ratkaisu)ratkaisu);
        return false;
    }


    @Override
    public int hashCode() {
        return id;
    }

    
    /**
     * @param args ei
     */
    public static void main(String[] args) {
        Ratkaisu eka = new Ratkaisu();
        Ratkaisu toka = new Ratkaisu();
        eka.rekisteroi();
        toka.rekisteroi();
        eka.tulosta(System.out);
        eka.testiArvot();
        eka.tulosta(System.out);
        
        toka.testiArvot();
        toka.tulosta(System.out);
    }
    
    
    

}