package de.eldecker.dhbw.spring.kantinenplan.datenbank;

import static java.util.Collections.emptyList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import de.eldecker.dhbw.spring.kantinenplan.model.KantinenException;


/**
 * Die Bean dieser Klasse simuliert die Datenbank mit einer Liste von Gerichten
 * für bestimmte Datumswerte.
 */
@Component
public class KantinenplanDatenbank {

    /** Datumsformat für Parsen Strings mit Datumswerten. */
    private final static DateTimeFormatter DATUMS_FORMATIERER = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
    
    /**
     * HashMap mit Datenbestand: 
     * <ul>
     * <li>Schlüssel/String ist Datum im Format {@code YYYY-MM-DD}</li>
     * <li>Der vom Schlüssel referenzierte Wert ist eine Liste von Strings,
     *     wobei jeder String ein Gericht wie z.B. "Schnitzel" ist.</li>
     * </ul> 
     */
    private final HashMap<String,List<String>> _hashMap = new HashMap<>( 100 );
    
    
    /**
     * Gerichte für {@code datum} abrufen.
     * 
     * @param datum Datum, für das die Gerichte zurückgeliefert werden sollen.
     * 
     * @return List mit Gerichten; es wird eine leere Liste (aber nicht {@code null})
     *         zurückgegeben, wenn für das Datum noch keine Gerichte definiert wurden.
     *         
     * @throws KantinenException Ungültiges Datum als Argument übergeben
     */
    public List<String> getGerichteFuerDatum( String datum ) throws KantinenException {
        
        checkDatum( datum ); // throws KantinenException
        
        final List<String> gerichteListe = _hashMap.get( datum );
        if ( gerichteListe != null ) {
            
            return gerichteListe;
            
        } else {
                        
            return emptyList();
        }
    }
    
    
    /**
     * Ein {@code gericht} für {@code datum} hinzufügen.
     * 
     * @param datum Datum im Format {@code YYYY-MM-DD}.
     * 
     * @param gericht Gericht, dass u.a. an {@code datum} angeboten wird.
     * 
     * @return Anzahl der Gerichte, die nach Einfüge-Operation für {@code datum}
     *         eingeplant ist.
     * 
     * @throws KantinenException Wenn {@code gericht} leerer ist oder {@code datum}
     *         ungültig ist.
     */
    public int addGericht( String datum, String gericht ) throws KantinenException {
        
        if ( gericht.isBlank() ) {
            
            throw new KantinenException( "Leeres Gericht übergegeben" );
        }
        
        final boolean liegtInVergangenheit = checkDatum( datum ); // throws KantinenException
        if ( liegtInVergangenheit ) {
            
            throw new KantinenException( "Datum \"" + datum + "\" liegt in der Vergangenheit." );
        }
        
        List<String> gerichteListe = _hashMap.get( datum );
        if ( gerichteListe != null ) {
            
            gerichteListe.add( gericht );
                        
        } else {
            
            gerichteListe = new ArrayList<>( 10 );
            gerichteListe.add( gericht );
            _hashMap.put( datum, gerichteListe );
        }
        
        return gerichteListe.size();
    }
    
    
    /**
     * Prüft, ob {@code datum} das Format {@code YYYY-MM-Dd} hat und einen
     * korrekten Wert enthält (z.B. {@code 2024-13-01} wäre kein gültiges
     * Datum). Die Methode wirft eine Exception, wenn das übergebene Datum
     * nicht in Ordnung ist.
     *  
     * @param datum Datum, das überprüft werden soll.
     * 
     * @return {@code true} wenn {@code datum} in der Vergangenheit liegt.
     * 
     * @throws KantinenException Datum ist nicht in Ordnung
     */
    private boolean checkDatum( String datum ) throws KantinenException {

        try {
            
            final LocalDate localDate = LocalDate.parse( datum, DATUMS_FORMATIERER ); // throws DateTimeParseException
            
            final LocalDate heuteDate = LocalDate.now();
                        
            return localDate.isBefore( heuteDate );             
            
        } catch ( DateTimeParseException ex ) {
            
            throw new KantinenException( "Datum \"" + datum + "\" ist nicht gültig: " +
                                         ex.getMessage() );
        }        
    }
    
}
