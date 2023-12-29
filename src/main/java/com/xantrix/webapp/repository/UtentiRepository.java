package com.xantrix.webapp.repository;

import com.xantrix.webapp.entities.Carta;
import com.xantrix.webapp.entities.Utenti;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UtentiRepository extends PagingAndSortingRepository<Utenti, String> {

    Utenti findByNome(String nome);

    Utenti findByNomeContainingIgnoreCase(String nome);




}
