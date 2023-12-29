package com.xantrix.webapp.controllers;

import com.xantrix.webapp.dtos.UtentiDto;
import com.xantrix.webapp.entities.Carta;
import com.xantrix.webapp.entities.Utenti;
import com.xantrix.webapp.exceptions.NotFoundException;
import com.xantrix.webapp.services.CartaService;
import com.xantrix.webapp.services.UtentiService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/utenti")
@Log
@CrossOrigin("http://localhost:4200")
public class UtentiController {


    private final UtentiService utentiService;
    private final CartaService cartaService;

    @Autowired
    public UtentiController(UtentiService utentiService, CartaService cartaService) {
        this.utentiService = utentiService;
        this.cartaService = cartaService;
    }

    @GetMapping(value = "/cerca/nome/{nome}", produces = "application/json")
    public ResponseEntity<Utenti> findByname(@PathVariable("nome") String nome)
            throws NotFoundException
    {
        log.info("****** Otteniamo l'utente con barcode " + nome + " *******");

        Utenti utente = utentiService.findByName(nome);

        if (utente == null)
        {
            String ErrMsg = String.format("utente %s non è stato trovato!", nome);

            log.warning(ErrMsg);

            throw new NotFoundException(ErrMsg);

        }

        return new ResponseEntity<Utenti>(utente, HttpStatus.OK);

    }

    @GetMapping(value = "/cerca/nomeErr/{nome}", produces = "application/json")
    public ResponseEntity<UtentiDto> findByNomeContainingIgnoreCase(@PathVariable("nome") String nome)
            throws NotFoundException
    {
        log.info("****** Otteniamo l'articolo con barcode " + nome + " *******");

        UtentiDto utente = utentiService.findByNomeContainingIgnoreCase(nome);

        if (utente == null)
        {
            String ErrMsg = String.format("utente %s non è stato trovato!", nome);

            log.warning(ErrMsg);

            throw new NotFoundException(ErrMsg);

        }


        return new ResponseEntity<UtentiDto>(utente, HttpStatus.OK);

    }

    @GetMapping(value = "/cerca/numero/{numero}", produces = "application/json")
    public ResponseEntity<Carta> findByNumero(@PathVariable("numero") int numero)
            throws NotFoundException
    {
        log.info("****** Otteniamo l'utente con barcode " + numero + " *******");

        Carta carta = cartaService.findbyNumero(numero);

        if (carta == null)
        {
            String ErrMsg = String.format("utente %s non è stato trovato!", numero);

            log.warning(ErrMsg);

            throw new NotFoundException(ErrMsg);

        }

        return new ResponseEntity<Carta>(carta, HttpStatus.OK);

    }

    @GetMapping(value = "/cerca/tuttecarte", produces = "application/json")
    public ResponseEntity<List<Carta>> findByAll()
            throws NotFoundException
    {
        log.info("****** Otteniamo tutte le carte *******");

        List<Carta> carta = cartaService.trovaTutte();

        if (carta.isEmpty())
        {
            String ErrMsg = String.format("Non ci sono carte!");

            log.warning(ErrMsg);

            throw new NotFoundException(ErrMsg);

        }

        return new ResponseEntity<List<Carta>>(carta, HttpStatus.OK);

    }

}
