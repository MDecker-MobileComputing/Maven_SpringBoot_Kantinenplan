package de.eldecker.dhbw.spring.kantinenplan.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.eldecker.dhbw.spring.kantinenplan.datenbank.KantinenplanDatenbank;
import de.eldecker.dhbw.spring.kantinenplan.model.KantinenException;


/**
 * Basis-URL des REST-Controllers: 
 * <pre>
 * http://localhost:8080//kantinenplan/v1
 * </pre>
 */
@RestController
@RequestMapping( "/kantinenplan/v1" )
public class KatinenplanRestController {

    /** Bean mit "Datenbank" der Gerichte. */
    private KantinenplanDatenbank _datenbank;
    
    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KatinenplanRestController( KantinenplanDatenbank db ) {
        
        _datenbank = db;
    }
    
       
    /**
     * REST-Endpunkt, um Gerichte für {@code datum} abzurufen.
     * 
     * URL bei lokaler Ausführung:
     * <pre>
     * http://localhost:8080/kantinenplan/v1/abrufen/2030-01-23
     * </pre> 
     * 
     * @param datum Datum im Format {@code yyyy-MM-dd} als Pfadparameter
     * 
     * @return Liste der Gerichte, oder Fehlermeldung
     */
    @GetMapping( "/abrufen/{datum}" )
    public ResponseEntity<String> gerichteAbrufen( @PathVariable String datum ) {
    
        final String datumNormalized = datum.trim();
        
        try {
        
            final List<String> gerichteList = _datenbank.getGerichteFuerDatum( datumNormalized );

            return ResponseEntity.status( OK )
                                 .body( gerichteList.toString() );                                                          
        }
        catch (KantinenException ex) {
            
            return ResponseEntity.status( BAD_REQUEST )
                                 .body( "Ungültiges Datum: " + ex.getMessage() );                                                          
        }         
    }
    

    /**
     * REST-Endpunkt, um neues {@code gericht} für {@code datum} einzuplanen.
     * <br><br>
     * 
     * URL bei lokaler Ausführung:
     * <pre>
     * http://localhost:8080/kantinenplan/v1/einplanen
     * </pre>
     *
     * @param datum Datum im Format {@code yyyy-MM-dd}, an dem das Gericht in 
     *              der Kantine angeboten werden soll
     *              
     * @param gericht Name des Gerichts, z.B. "Rumpsteak"
     * 
     * @return Erfolgs- oder Fehlermeldung.
     */
    @PostMapping( "/einplanen" )
    public ResponseEntity<String> gerichtEinplanen( @RequestParam String datum,
                                                    @RequestParam String gericht ) {

        final String datumNormalized   = datum.trim();
        final String gerichtNormalized = gericht.trim();
        
        try {
        
            final int anzahl = _datenbank.addGericht( datumNormalized, gerichtNormalized );
            
            final String antwort = "Es gibt jetzt " + anzahl + " Gericht(e) am " + datum + ".";

            return ResponseEntity.status( OK ).body( antwort );            
        }
        catch ( KantinenException ex ) {
         
            return ResponseEntity.status( BAD_REQUEST )
            		             .body( "Fehler: " + ex.getMessage() );                                                                               
        }                        
    }
    
}