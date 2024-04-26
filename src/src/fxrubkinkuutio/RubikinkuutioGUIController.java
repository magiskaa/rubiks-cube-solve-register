package fxrubkinkuutio;

import static fxrubkinkuutio.RubikinkuutioRatkaisuDialogController.getFieldId; 
import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.application.Platform;

/**
 * 
 * @author Valtteri
 * @version 18.1.2024
 *
 */
public class RubikinkuutioGUIController implements Initializable {
    
    @FXML private TextField editSekoitus;
    @FXML private ListChooser<Ratkaisu> chooserRatkaisut;
    @FXML private ScrollPane panelRatkaisu;
    @FXML private GridPane gridRatkaisu;
    @FXML private TextField hakuehto;
    
    /**
     * alustus
     * @param url url
     * @param bundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {    
        alusta();      
    }

    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML private void handleTallenna() {
        tallenna();
        Dialogs.showMessageDialog("Tallennettu!");
    }
    
    @FXML private void handleLisaa() {
        //ModalController.showModal(RubikinkuutioGUIController.class.getResource("RubikinkuutioLisaaView.fxml"), null,null,null);
        lisaaRatkaisu();
    }
    
    @FXML private void handleLisaaSekoitus() throws SailoException {
        //ModalController.showModal(RubikinkuutioGUIController.class.getResource("RubikinkuutioLisaaSekoitusView.fxml"), null,null,null);
        lisaaSekoitus();
    }
    
    @FXML private void handleHakuehto() {
        hae(0);
    }

    @FXML private void handlePoista() {
        poistaRatkaisu();
    }
    
    @FXML private void handleJarjestaId() {
        jarjesta(0);
    }
    
    @FXML private void handleJarjestaAikaPs() {
        jarjesta(1);
    }
    
    @FXML private void handleJarjestaPvm() {
        jarjesta(2);
    }
    
    @FXML private void handleApua() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    @FXML private void handleMuokkaa() throws SailoException {
        muokkaa(1);
    }
    
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("Ei vielä toimi");
    }
    
    
    //========= EI KÄYTTÖLIITTYMÄÄN LIITTYVÄÄ KOODIA =========
    
    private Rekisteri rekisteri;
    private Ratkaisu ratkaisuKohdalla;
    private TextField edits[];
    private int kentta = 0;
    
    
    /**
     * alustus
     */
    protected void alusta() {
        chooserRatkaisut.clear();
        chooserRatkaisut.addSelectionListener(e -> {
            try {
                naytaRatkaisu();
            } catch (SailoException e1) {
                e1.printStackTrace();
            }
        });

        edits = RubikinkuutioRatkaisuDialogController.luoKentat(gridRatkaisu);
        for (TextField edit : edits) {
            if (edit != null) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> {if (e.getClickCount() > 1)
                    try {
                        muokkaa(getFieldId(e.getSource(),0));
                    } catch (SailoException e1) {
                        e1.printStackTrace();
                    } });
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
            }
        }
    }
    
    
    /**
     * @param nimi tiedoston nimi
     * @return null tai virhe
     */
    public String lueTiedosto(String nimi) {
        try {
            rekisteri.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch ( SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if (virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    /**
     * välittää tallennuksen rekisterille
     * @return null tai virhe
     */
    public String tallenna() {
        try {
            rekisteri.tallenna();
            return null;
        } catch ( SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia " + e.getMessage());
            return e.getMessage();
        }
    }
    
    /**
     * näyttää valitun ratkaisun sekä mahdollisen sekoituksen tekstialueella
     * @throws SailoException virhe
     */
    protected void naytaRatkaisu() throws SailoException {
        ratkaisuKohdalla = chooserRatkaisut.getSelectedObject();
        if (ratkaisuKohdalla == null) return;
        
        RubikinkuutioRatkaisuDialogController.naytaRatkaisu(edits, ratkaisuKohdalla);
        naytaSekoitukset(ratkaisuKohdalla);
    }
    
    
    /**
     * @param ratkaisu ratkaisu
     */
    public void naytaSekoitukset(Ratkaisu ratkaisu) {
        editSekoitus.clear();
        if (ratkaisu == null) return;
        
        try {
            List<Sekoitus> sekoitukset = rekisteri.annaSekoitukset(ratkaisu);
            if (sekoitukset.size() == 0) return;
            editSekoitus.setText(naytaSekoitus(sekoitukset.getFirst().toString()));
        } catch (SailoException e) {
            System.out.println(e.getMessage());
        }
    }

    
    /**
     * @param rivi rivi
     * @return sekoitus
     */
    public String naytaSekoitus(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        Mjonot.erota(sb, '|', rivi);
        return Mjonot.erota(sb, '|', rivi);
    }
    
    
    /**
     * hakee ratkaisun ja lisää sen käyttöliittymän listaan
     * @param id id
     */
    protected void hae(int id) {
        
        int rnro = id;
        if (rnro <= 0) {
            Ratkaisu kohdalla = ratkaisuKohdalla;
            if (kohdalla != null) rnro = kohdalla.getId();
        }
        
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0) ehto = '*' + ehto + '*';
        
        chooserRatkaisut.clear();
        
        int index = 0;
        Collection<Ratkaisu> ratkaisut;
        try {
            ratkaisut = rekisteri.etsi(ehto);
            int i = 0;
            for (Ratkaisu ratkaisu : ratkaisut) {
                if (ratkaisu.getId() == rnro) index = i;
                String listassa = ratkaisu.getId() + "  |  " + ratkaisu.getAika() + "  |  " + ratkaisu.getPvm() + "  |  " + ratkaisu.getKellonaika();
                chooserRatkaisut.add(listassa, ratkaisu);
                i++;
            }
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ratkaisun hakemisessa ongelmia " + e.getMessage());
        }
        chooserRatkaisut.setSelectedIndex(index);
    }
    
    /**
     * @param k minkä mukaan järjestetään
     */
    public void jarjesta(int k) {
        int nro = 0;
        Ratkaisu kohdalla = ratkaisuKohdalla;
        if (kohdalla != null) nro = kohdalla.getId();
        
        chooserRatkaisut.clear();
        
        int index = 0;
        Collection<Ratkaisu> ratkaisut;
        ratkaisut = rekisteri.jarjesta(k);
        int i = 0;
        for (Ratkaisu ratkaisu : ratkaisut) {
            if (ratkaisu.getId() == nro) index = i;
            String listassa = ratkaisu.getId() + "  |  " + ratkaisu.getAika() + "  |  " + ratkaisu.getPvm() + "  |  " + ratkaisu.getKellonaika();
            chooserRatkaisut.add(listassa, ratkaisu);
        }
        chooserRatkaisut.setSelectedIndex(index);
    }
    
    /**
     * lisää ratkaisun rekisteriin ja kutsuu hae-aliohjelmaa
     */
    protected void lisaaRatkaisu() {
        try {
            Ratkaisu uusi = new Ratkaisu();
            uusi = RubikinkuutioRatkaisuDialogController.kysyRatkaisu(null, uusi, rekisteri);
            if (uusi == null) return;
            uusi.rekisteroi();
            rekisteri.lisaa(uusi);
            hae(uusi.getId());
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden lisäämisessä " + e.getMessage());
            return;
        }
    }
    
    /**
     * lisää sekoituksen rekisteriin
     * @throws SailoException virhe
     */
    public void lisaaSekoitus() throws SailoException {
        if (ratkaisuKohdalla == null) return;
        Sekoitus sek = new Sekoitus();
        sek.rekisteroi();
        sek.testiArvot(ratkaisuKohdalla.getId());
        rekisteri.lisaa(sek);
        hae(ratkaisuKohdalla.getId());
    }

    
    private void muokkaa(@SuppressWarnings("unused") int k) throws SailoException {
        if (ratkaisuKohdalla == null) return;
        try {
            Ratkaisu muokattu;
            muokattu = RubikinkuutioRatkaisuDialogController.kysyRatkaisu(null, ratkaisuKohdalla.clone(), rekisteri);
            if (muokattu == null) return;
            rekisteri.korvaaTaiLisaa(muokattu);
            hae(muokattu.getId());
        } catch (CloneNotSupportedException e) { 
            // 
        }
    }

    
    /**
     * poistaa valitun ratkaisun
     */
    public void poistaRatkaisu() {
        Ratkaisu ratkaisu = ratkaisuKohdalla;
        if (ratkaisu == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko ratkaisu: " + ratkaisu.getIdS() + " | " + ratkaisu.getAika() 
                                        + "  |  " + ratkaisu.getPvm() + "  |  " + ratkaisu.getKellonaika(), "Kyllä", "ei")) return;
        rekisteri.poista(ratkaisu);
        int index = chooserRatkaisut.getSelectedIndex();
        hae(0);
        chooserRatkaisut.setSelectedIndex(index);
    }
    
    /**
     * tulostaa tekstialueelle ratkaisun ja sekoituksen
     * @param os mikä tietovirta
     * @param ratkaisu ratkaisu
     * @throws SailoException virhe
     */
    public void tulosta(PrintStream os, final Ratkaisu ratkaisu) throws SailoException {
        os.println("-------------------------------------------");
        ratkaisu.tulosta(os);
        os.println("-------------------------------------------");
        List<Sekoitus> sekoitukset = rekisteri.annaSekoitukset(ratkaisu);
        for (Sekoitus sek : sekoitukset)
            sek.tulosta(os);
    }
   
    
    /**
     * tekee rekisterin
     * @param rekisteri rekisteri
     * @throws SailoException virhe
     */
    public void setRekisteri(Rekisteri rekisteri) throws SailoException {
        this.rekisteri = rekisteri;
        naytaRatkaisu();
    } 
}