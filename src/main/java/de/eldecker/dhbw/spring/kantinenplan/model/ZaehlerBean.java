package de.eldecker.dhbw.spring.kantinenplan.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Bean für einen einfachen Zähler. Wegen scope="prototype" wird für jede
 * Instanz der Bean ein eigener Zählerstand geführt (auch wenn eine andere
 * Bean mehrere Instanzen dieser Bean verwendet).
 */
@Component
@Scope( "prototype" )
public class ZaehlerBean {

    /** Zählerstand, den diese Bean verwaltet. */
    private int _zaehlerstand = 0;

    
    /**
     * Inkrementiert den Zählerstand um 1 und gibt den neuen
     * Zählerstand zurück.
     * 
     * @return Neuer Zählerstand
     */
    public int inkrement() {
        
        return ++_zaehlerstand;
    }

    
    /**
     * Zählerstand auf 0 zurücksetzen.
     */
    public int zuruecksetzen() {
        
        final int alterZaehlerstand = _zaehlerstand;
        
        _zaehlerstand = 0;
        
        return alterZaehlerstand;
    }

    
    /**
     * Gibt den aktuellen Zählerstand zurück.
     * 
     * @return Aktueller Zählerstand
     */
    public int getZaehlerstand() {
        
        return _zaehlerstand;
    }
    
 
    /**
     * String-Repräsentation des Zählerstands.
     * 
     * @return String mit dem aktuellen Zählerstand
     */
    @Override
    public String toString() {

        return "Zaehlerstand: " + _zaehlerstand;
    }
    
}