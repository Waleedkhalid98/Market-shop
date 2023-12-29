package com.xantrix.webapp.services;

import com.xantrix.webapp.entities.Eta;
import com.xantrix.webapp.entities.Nome;

public interface Stampa {

    String stampaNome(Nome nome);

    int stampaNumero(Eta numero);
}
