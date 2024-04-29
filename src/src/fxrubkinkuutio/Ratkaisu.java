package fxrubkinkuutio;

import java.io.*;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Ratkaisu
 * @author Valtteri
 * @version 18.3.2024
 *
 */
public class Ratkaisu implements Cloneable {
    private int id = 1;
    private String aika = "";
    private String pvm = "";
    private String kellonaika = "";
    private String dnf = "F";
    private String kaksiS = "F";
    private int sekoitusId;
    
    private static int seuraavaId = 1;
    
    
    /**
     * montako kenttaa ratkaisusta näytetään
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 7;
    }
    
    /**
     * palauttaa ensimmäisen näytettävän kentän
     * @return ensimmäinen näytettävä kenttä
     */
    public int ekaKentta() {
        return 1;
    }
    
    /**
     * 
     */
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
            return rat1.annappa(k).compareToIgnoreCase(rat2.annappa(k)); 
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
     *   eka.getId() === 1;
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
    
    /**
     * palauttaa ratkaisun id:n stringinä
     * @return id stringinä
     */
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
     *   eka.getAika() === "00;24,284";
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
    
    /**
     * palauttaa ratkaisun kellonajan
     * @return ratkaisun kellonaika
     */
    public String getKellonaika() {
        return kellonaika;
    }
    
    /**
     * palauttaa T jos ratkaisu on DNF tai F jos ei
     * @return T tai F
     */
    public String getDnf() {
        return dnf;
    }
    
    /**
     * palauttaa T jos ratkaisuun tulee +2s tai F jos ei
     * @return T tai F
     */
    public String get2s() {
        return kaksiS;
    }
    
    
    /** 
     * Antaa k:n kentän sisällön merkkijonona 
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona 
     */ 
    public String annappa(int k) {
        switch (k) {
        case 0: return "" + id;
        case 1: return "" + aika;
        case 2: return "" + pvm;
        case 3: return "" + kellonaika;
        case 4: return "" + dnf;
        case 5: return "" + kaksiS;
        case 6: return "" + Integer.toString(sekoitusId);
        default: return "";
        }
    }
    
    
    /**
     * Palauttaa ratkaisun tiedot merkkijonona jonka voi tallentaa tiedostoon
     * @return Ratkaisu tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Ratkaisu ratkaisu = new Ratkaisu();
     *   ratkaisu.parse("2|00;34,224|02.11.2023|00.03");
     *   ratkaisu.toString().startsWith("2|00;34,224|02.11.2023|00.03|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        String erotin = "";
        for (int k = 0; k < getKenttia()-1; k++) {
            sb.append(erotin);
            sb.append(annappa(k));
            erotin = "|";
        }
        sb.append(erotin);
        sb.append(sekoitusId);
        return sb.toString();
    }
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k mikä kenttä
     * @param jono kentän syöte
     * @param rekisteri rekisteri
     * @return virhe tai null
     * @example
     * <pre name="test">
     *   Ratkaisu ratkaisu = new Ratkaisu();
     *   Rekisteri rekisteri = null;
     *   ratkaisu.aseta(1,"00;24,443", rekisteri) === null;
     *   ratkaisu.aseta(2,"2.3.2023", rekisteri) =R= "Päivämäärän tulee olla muotoa '00.00.0000'"
     *   ratkaisu.aseta(2,"04.07.2024", rekisteri) === null; 
     *   ratkaisu.aseta(3,"02.24", rekisteri) === null; 
     *   ratkaisu.aseta(4,"", rekisteri) === "DNF tulee olla muotoa 'T' tai 'F'";
     *   ratkaisu.aseta(6,"4", rekisteri) =R= " ";
     * </pre>
     */
    public String aseta(int k, String jono, Rekisteri rekisteri) {
        String tjono = jono.trim();
        switch (k) {
        case 0: 
            if (tjono.matches("")) return "Ratkaisun id:tä ei ole mahdollista muokata";
            return null;
        case 1:
            if (!tjono.matches("\\d{2};\\d{2},\\d{3}")) return "Ratkaisun ajan tulee olla muotoa '00;00,000'";
            setAika(tjono);
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
            setPvm(tjono);
            return null;
        case 3:
            if (!tjono.matches("\\d{2}.\\d{2}")) return "Kellonajan tulee olla muotoa '00.00'";
            setKellonaika(tjono);
            return null;
        case 4:
            if (tjono.matches("") || tjono.isEmpty()) return "DNF tulee olla muotoa 'T' tai 'F'";
            setDnf(tjono);
            return null;
        case 5:
            if (tjono.matches("") || tjono.isEmpty()) return "+2s tulee olla muotoa 'T' tai 'F'";
            set2s(tjono);
            return null;
        case 6:
            if (tjono.matches("\\d{1}") || tjono.matches("\\d{2}") || tjono.matches("\\d{3}") || tjono.matches("\\d{4}")) {
                return " ";
            }
            setSekoitus(rekisteri.etsiTaiLuoSekoitus(tjono).getId());
            return null;
        default:
            return null;
        }
    }
    
    
    /**
     * muokkaa id:n
     * @param s vaihdettu id
     */
    public void setId(@SuppressWarnings("unused") String s) {
        // ei käytössä
        return;
    }
    
    /**
     * muokkaa ajan
     * @param s vaihdettu aika
     */
    public void setAika(String s) {
        this.aika = s;
    }   
    
    /**
     * muokkaa päivämäärän
     * @param s vaihdettu päivämäärä
     */
    public void setPvm(String s) {
        this.pvm = s;
    }   
    
    /**
     * muokkaa kellonajan
     * @param s vaihdettu kellonaika
     */
    public void setKellonaika(String s) {
        this.kellonaika = s;
    }   
    
    /**
     * muokkaa dnf:n
     * @param s vaihdettu dnf
     */
    public void setDnf(String s) {
        this.dnf = s;
    }   
    
    /**
     * muokkaa kaksiS:n
     * @param s vaihdettu kaksiS
     */
    public void set2s(String s) {
        this.kaksiS = s;
    }   
    
    /**
     * muokkaa sekoitus id:n
     * @param s vaihdettu sekoituksen id
     */
    public void setSekoitus(int s) {
        this.sekoitusId = s;
    }   
    
    
    /**
     * palauttaa kenttien nimet
     * @param k mikä kenttä
     * @return kentän nimi
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Ratkaisun ID:";
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
     * @example
     * <pre name="test">
     *   Ratkaisu ratkaisu = new Ratkaisu();
     *   ratkaisu.parse("2|00;34,224|02.11.2023|00.03");
     *   ratkaisu.getId() === 2;
     *   ratkaisu.toString().startsWith("2|00;34,224|02.11.2023|00.03|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   ratkaisu.rekisteroi();
     *   int n = ratkaisu.getId();
     *   ratkaisu.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   ratkaisu.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   ratkaisu.getId() === n+20+1;
     * </pre>
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
            if ( !annappa(k).equals(ratkaisu.annappa(k)) ) return false;
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
     * @param args ei käytössä
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