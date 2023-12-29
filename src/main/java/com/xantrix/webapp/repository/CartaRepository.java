package com.xantrix.webapp.repository;

import antlr.collections.List;
import com.xantrix.webapp.entities.Carta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CartaRepository extends PagingAndSortingRepository<Carta,String> {


    Carta findByNumero(int numero);



}
