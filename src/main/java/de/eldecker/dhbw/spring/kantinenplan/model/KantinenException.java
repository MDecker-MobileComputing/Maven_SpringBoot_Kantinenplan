package de.eldecker.dhbw.spring.kantinenplan.model;


/**
 * Eigene Exception-Klasse.
 */
@SuppressWarnings("serial")
public class KantinenException extends Exception {

    
    /**
     * Konstruktor der Klasse.
     * 
     * @param fehlerbeschreibung Beschreibung des Fehlers
     */
    public KantinenException( String fehlerbeschreibung ) {
        
        super( fehlerbeschreibung );
    }
    
}
