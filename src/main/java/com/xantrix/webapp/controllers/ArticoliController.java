package com.xantrix.webapp.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.xantrix.webapp.dtos.UtentiDto;
import com.xantrix.webapp.entities.*;
import com.xantrix.webapp.exceptions.ArgException;
import com.xantrix.webapp.services.CartaService;
import com.xantrix.webapp.services.Stampa;
import com.xantrix.webapp.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.dtos.InfoMsg;
import com.xantrix.webapp.exceptions.BindingException;
import com.xantrix.webapp.exceptions.DuplicateException;
import com.xantrix.webapp.exceptions.NotFoundException;
import com.xantrix.webapp.services.ArticoliService;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@RestController
@RequestMapping("api/articoli")
@Log
@CrossOrigin("http://localhost:4200")
public class ArticoliController
{

	private final ArticoliService articoliService;
	private final UtentiService utentiService;
	private final CartaService cartaService;

	private final ResourceBundleMessageSource errMessage;

	private final Stampa stampa;

	@Autowired
	public ArticoliController(ArticoliService articoliService,UtentiService utentiService, CartaService cartaService , ResourceBundleMessageSource errMessage, Stampa stampa) {
		this.articoliService = articoliService;
		this.errMessage = errMessage;
		this.stampa = stampa;
		this.utentiService = utentiService ;
		this.cartaService = cartaService ;
	}

	@PostMapping(value= "eta")
	public int numero(@Valid @RequestBody Eta eta, BindingResult bindingResult) throws ArgException{

		if(bindingResult.hasErrors())
		{
			String ms = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			throw new ArgException(ms);
		}

		return stampa.stampaNumero(eta);
	}



	@PostMapping (value = "ciao")
	public String saluta(@Valid @RequestBody Nome nome, BindingResult bindingResult) throws BindingException {

		if (bindingResult.hasErrors())
		{
			System.out.println("+++" + bindingResult.getFieldError()+ "+++" );
			System.out.println(errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale()));
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());

			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}

		return stampa.stampaNome(nome);
	}




	@PostMapping(value ="mlp")
	public ResponseEntity<Numeri> somma(@RequestBody Numeri numeri){

		ArrayList<Integer> b =numeri.getA();
		for (int i = 0; i < b.size(); i++) {
			b.set(i, b.get(i) + 1);
		}

		return new ResponseEntity<Numeri>(numeri, HttpStatus.OK);
		}








	@GetMapping(value = "/cerca/barcode/{ean}", produces = "application/json")
	public ResponseEntity<ArticoliDto> listArtByEan(@PathVariable("ean") String Ean)
			throws NotFoundException
	{
		log.info("****** Otteniamo l'articolo con barcode " + Ean + " *******");

		ArticoliDto articolo = articoliService.SelByBarcode(Ean);

		if (articolo == null)
		{
			String ErrMsg = String.format("Il barcode %s non è stato trovato!", Ean);

			log.warning(ErrMsg);

			throw new NotFoundException(ErrMsg);

			//return new ResponseEntity<Articoli>(HttpStatus.NOT_FOUND);
		}
		 
		return new ResponseEntity<ArticoliDto>(articolo, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
	public ResponseEntity<ArticoliDto> listArtByCodArt(@PathVariable("codart") String codArt)
			throws NotFoundException
	{
		log.info("trovati articoliii");
		ArticoliDto articolo = articoliService.SelByCodArt(codArt);

		if(articolo == null ){
			String ErrMsg = String.format("l'articolo con %s non è stato trovato!", codArt);
			log.warning(ErrMsg);
			throw new NotFoundException(ErrMsg);
		}

		ResponseEntity<ArticoliDto> articolos = new ResponseEntity<ArticoliDto>(articolo, HttpStatus.OK);
		return articolos;
	}
	
	@GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
	public ResponseEntity<List<ArticoliDto>> listArtByDesc(@PathVariable("filter") String Filter)
			throws NotFoundException
	{
		log.info("****** Otteniamo gli articoli con Descrizione: " + Filter + " *******");
		
		List<ArticoliDto> articoli = articoliService.SelByDescrizione(Filter);
		
		if (articoli.isEmpty())
		{
			String ErrMsg = String.format("Non è stato trovato alcun articolo avente descrizione %s", Filter);
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
			
		}
		
		return new ResponseEntity<List<ArticoliDto>>(articoli, HttpStatus.OK);
	}
	
	// -------------------------- INSERIMENTO ARTICOLO ---------------------------------
	@PostMapping(value = "/inserisci")
	@SneakyThrows
	public ResponseEntity<InfoMsg> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
	{
		log.info("Salviamo l'articolo con codice " + articolo.getCodArt());
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
		}
		
		//Disabilitare se si vuole gestire anche la modifica 
		ArticoliDto checkArt =  articoliService.SelByCodArt(articolo.getCodArt());
		
		if (checkArt != null)
		{
			String MsgErr = String.format("Articolo %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", articolo.getCodArt());
			
			log.warning(MsgErr);
			
			throw new DuplicateException(MsgErr);
		}
		
		articoliService.InsArticolo(articolo);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),
				"Inserimento Articolo Eseguita con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- MODIFICA ARTICOLO ------------------------------------
	@RequestMapping(value = "/modifica", method = RequestMethod.PUT)
	@SneakyThrows
	public ResponseEntity<InfoMsg> updateArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
	{
		log.info("Modifichiamo l'articolo con codice " + articolo.getCodArt());
		
		if (bindingResult.hasErrors())
		{
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			
			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}
		
		ArticoliDto checkArt =  articoliService.SelByCodArt(articolo.getCodArt());

		if (checkArt == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", articolo.getCodArt());
			
			log.warning(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}
		
		articoliService.InsArticolo(articolo);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(),"E' ANDATA A BUON FINE"),HttpStatus.CREATED);
	}
	
		
		// ------------------- ELIMINAZIONE ARTICOLO ------------------------------------
		@DeleteMapping(value = "/elimina/{codart}", produces = "application/json" )
		@SneakyThrows
		public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
		{
			log.info("Eliminiamo l'articolo con codice " + CodArt);
			
			Articoli articolo = articoliService.SelByCodArt2(CodArt);
			
			if (articolo == null)
			{
				String MsgErr = String.format("Articolo %s non presente in anagrafica! ",CodArt);
				
				log.warning(MsgErr);
				
				throw new NotFoundException(MsgErr);
			}
			
			articoliService.DelArticolo(articolo);
			
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode responseNode = mapper.createObjectNode();
			
			responseNode.put("code", HttpStatus.OK.toString());
			responseNode.put("message", "Eliminazione Articolo " + CodArt + " Eseguita Con Successo");
			
			return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
					
		}
}
