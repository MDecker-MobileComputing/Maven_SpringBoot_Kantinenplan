package de.eldecker.dhbw.spring.kantinenplan.helferlein;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.dhbw.spring.kantinenplan.datenbank.KantinenplanDatenbank;


/**
 * Sobald die Spring-Boot-Anwendung gestartet ist, wird die {@code run()}-Methode
 * in dieser Bean-Klasse ausgeführt, mit der einige Demo-Datensätze in die Datenbank
 * geladen werden.
 */
@Component
public class DemoDatenImporter implements ApplicationRunner {

    private KantinenplanDatenbank _datenbank;
    
    
    /**
     * Konstruktor für Dependency Injection.
     */
    @Autowired
    public DemoDatenImporter( KantinenplanDatenbank db ) {
        
        _datenbank = db;
    }
    
    
    /**
     * Einzige Methode aus Interface {@code ApplicationRunner}, die beim Starten der
     * Spring-Boot-Anwendung aufgerufen wird und einige Demo-Datensätze in die
     * Datenbank lädt.
     */
    @Override
    public void run( ApplicationArguments args ) throws Exception {
   
        _datenbank.addGericht( "2030-05-02", "Schupfnudeln"  );
        _datenbank.addGericht( "2030-05-02", "Linseneintopf" );
        
        _datenbank.addGericht( "2030-05-03", "Hamburger" );
        _datenbank.addGericht( "2030-05-03", "Berliner"  );
    }
    
}
