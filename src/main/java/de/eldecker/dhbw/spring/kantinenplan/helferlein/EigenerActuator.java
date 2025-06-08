package de.eldecker.dhbw.spring.kantinenplan.helferlein;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import de.eldecker.dhbw.spring.kantinenplan.datenbank.KantinenplanDatenbank;
import de.eldecker.dhbw.spring.kantinenplan.model.KantinenMetriken;
import de.eldecker.dhbw.spring.kantinenplan.rest.KantinenplanRestController;


/**
 * Eigene Actuator-Klasse für die Spring-Anwendung.
 */
@Component
@Endpoint( id = "kantinenmetriken" )
public class EigenerActuator {

    @Autowired
    private KantinenplanRestController _kantinenplanRestController;
    
    @Autowired
    private KantinenplanDatenbank _datenbank;
 
    
    /**
     * Konstruktor für Dependency Injection.
     */
    public EigenerActuator( KantinenplanRestController kantinenplanRestController,
                            KantinenplanDatenbank      datenbank ) {
        
        _kantinenplanRestController = kantinenplanRestController;
        _datenbank                  = datenbank;
    }
    
    
    /**
     * Gibt die Metriken der Kantinenanwendung für Bereitstellung
     * über Actuator-Endpunkt zurück.
     * 
     * @return Objekt mit Metrikwerten, wird nach JSON serialisiert
     */
    @ReadOperation
    public KantinenMetriken getKantinenMetriken() {

        final int[] _kantinenMetriken = _kantinenplanRestController.getZaehlerWerte();
        
        final int tageMitGerichten = _datenbank.getAnzahlTageMitGerichten();
        
        final KantinenMetriken metrikObjekt = 
                                    new KantinenMetriken( tageMitGerichten,
                                                          _kantinenMetriken[0], 
                                                          _kantinenMetriken[1]
                                                        );
        return metrikObjekt;
    }
    
}
