package com.xantrix.webapp.services;

import com.xantrix.webapp.entities.Eta;
import com.xantrix.webapp.entities.Nome;
import org.springframework.stereotype.Service;

@Service
public class StampaImpl implements Stampa {


    @Override
    public String stampaNome(Nome nome) {

        return nome.getNome();
    }

    @Override
    public int stampaNumero(Eta eta) {
        return eta.getEta();
    }


}
