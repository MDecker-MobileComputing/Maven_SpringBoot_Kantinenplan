package de.eldecker.dhbw.spring.kantinenplan.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/kantinenplan/v1")
public class KatinenplanRestController {

    /**
     * Neuen Eintrag für Kantinenplan dazu: ein Gericht für einen bestimmten Tag.
     *
     * @param datum Datum im Format {@code yyyy-MM-dd}, an dem das Gericht in der
     *              Kantine angeboten werden soll
     * @param gericht Name des Gerichts, z.B. "Rumpsteak"
     */
    @PostMapping("/kantinenplan")
    public ResponseEntity<String> postEintrag( @RequestParam String datum,
                                               @RequestParam String gericht ) {

        return ResponseEntity.ok("Eintrag wurde erstellt");
    }

}