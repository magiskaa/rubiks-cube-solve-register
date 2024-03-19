package fxrubkinkuutio;


/**
 * @author Valtteri
 * @version 19.3.2024
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * käytettävä viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
