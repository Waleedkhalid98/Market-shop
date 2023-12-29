package com.xantrix.webapp.services;

import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Carta;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartaService {

    Carta findbyNumero(int numero);

    List<Carta> trovaTutte();
}
