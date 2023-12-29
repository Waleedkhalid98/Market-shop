package com.xantrix.webapp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xantrix.webapp.entities.Utenti;
import lombok.Data;

import javax.persistence.*;

@Data
public class CartaDto {


    private int numero;

    private int punti;

    private Utenti utenti;
}