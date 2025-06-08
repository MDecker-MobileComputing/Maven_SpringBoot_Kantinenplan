package de.eldecker.dhbw.spring.kantinenplan.model;


/**
 * Metriken der Kantinenanwendung.
 * 
 * @param anzahlTageMitGerichten Anzahl der Tage, für die Gerichte definiert sind
 *                               
 * @param anzahlGetAnfragen      Anzahl der GET-Anfragen
 * 
 * @param anzahlPostAnfragen     Anzahl der POST-Anfragen
 */
public record KantinenMetriken( int anzahlTageMitGerichten,
                                int anzahlGetAnfragen,
                                int anzahlPostAnfragen 
                              ) {

}
