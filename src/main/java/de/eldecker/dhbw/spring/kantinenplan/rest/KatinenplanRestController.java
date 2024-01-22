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



@RestController
@RequestMapping("/kantinenplan/v1")
public class KatinenplanRestController {

    /** Bean mit "Datenbank" der Gerichte. */
    private KantinenplanDatenbank _datenbank;
    
    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public KatinenplanRestController(KantinenplanDatenbank db) {
        
        _datenbank = db;
    }
    
       
    /**
     * REST-Endpunkt, um Gerichte für {@code datum} abzurufen.
     * 
     * @param datum Datum im Format {@code yyyy-MM-dd}
     * 
     * @return Liste der Gerichte, oder Fehlermeldung
     */
    @GetMapping("/abrufen/{datum}")
    public ResponseEntity<String> gerichteAbrufen(@PathVariable String datum) {
    
        String datumNormalized = datum.trim();
        
        try {
        
            List<String> gerichteList = _datenbank.getGerichteFuerDatum(datumNormalized);

            return ResponseEntity.status(OK)
                                 .body( gerichteList.toString() );                                                          
        }
        catch (KantinenException ex) {
            
            return ResponseEntity.status(BAD_REQUEST)
                                 .body("Ungültiges Datum: " + ex.getMessage());                                                          
        }         
    }
    

    /**
     * REST-Endpunkt, um neues {@code gericht} für {@code datum} einzuplanen.
     *
     * @param datum Datum im Format {@code yyyy-MM-dd}, an dem das Gericht in der
     *              Kantine angeboten werden soll
     *              
     * @param gericht Name des Gerichts, z.B. "Rumpsteak"
     * 
     * @return Erfolgs- oder Fehlermeldung.
     */
    @PostMapping("/einplanen")
    public ResponseEntity<String> gerichtEinplanen( @RequestParam String datum,
                                                    @RequestParam String gericht ) {

        String datumNormalized   = datum.trim();
        String gerichtNormalized = gericht.trim();
        
        try {
        
            int anzahl = _datenbank.addGericht(datumNormalized, gerichtNormalized);
            
            String antwort = "Es gibt jetzt " + anzahl + " Gericht(e) am \"" + datum + ".";

            return ResponseEntity.status(OK).body(antwort);            
        }
        catch (KantinenException ex) {
         
            return ResponseEntity.status(BAD_REQUEST).body("Fehler: " + ex.getMessage());                                                                               
        }                        
    }
    
}