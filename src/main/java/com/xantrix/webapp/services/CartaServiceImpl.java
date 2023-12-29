package com.xantrix.webapp.services;

import com.xantrix.webapp.entities.Carta;
import com.xantrix.webapp.repository.CartaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartaServiceImpl implements CartaService {

    @Autowired
    CartaRepository cartaRepository;

    @Override
    public Carta findbyNumero(int numero) {
        return cartaRepository.findByNumero(numero);
    }

    @Override
    public List<Carta> trovaTutte() {

        List<Carta> carte = (List<Carta>) cartaRepository.findAll();
       return carte;
    }


}
