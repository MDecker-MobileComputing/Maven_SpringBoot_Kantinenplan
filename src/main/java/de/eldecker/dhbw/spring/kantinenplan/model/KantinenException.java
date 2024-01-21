package de.eldecker.dhbw.spring.kantinenplan.model;


@SuppressWarnings("serial")
public class KantinenException extends Exception {

    public KantinenException(String fehlerbeschreibung) {
        
        super(fehlerbeschreibung);
    }
    
}
