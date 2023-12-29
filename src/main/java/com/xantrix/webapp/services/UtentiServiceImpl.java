package com.xantrix.webapp.services;

import com.xantrix.webapp.dtos.ArticoliDto;
import com.xantrix.webapp.dtos.UtentiDto;
import com.xantrix.webapp.entities.Articoli;
import com.xantrix.webapp.entities.Carta;
import com.xantrix.webapp.entities.Utenti;
import com.xantrix.webapp.repository.UtentiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService  {

     private final UtentiRepository utentiRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UtentiServiceImpl( UtentiRepository utentiRepository){
        this.utentiRepository = utentiRepository ;
    }

    private UtentiDto ConvertToDto(Utenti utenti)
    {
        UtentiDto utentiDto = null;


        if (utenti != null)
        {
            utentiDto =  modelMapper.map(utenti, UtentiDto.class);
        }

        return utentiDto;
    }

    @Override
    public Utenti findByName(String name){


        return  utentiRepository.findByNome(name);
    }

    @Override
    public UtentiDto findByNomeContainingIgnoreCase(String name) {

            Utenti utenti = utentiRepository.findByNomeContainingIgnoreCase(name);
        return ConvertToDto(utenti);
    }




}
