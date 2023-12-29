package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.UtentiDto;
import com.xantrix.webapp.entities.Carta;
import com.xantrix.webapp.entities.Utenti;

import java.util.List;

public interface UtentiService {


        Utenti findByName(String name);

        UtentiDto findByNomeContainingIgnoreCase(String name);


}
