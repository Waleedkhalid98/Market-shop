package com.xantrix.webapp.dtos;

import com.xantrix.webapp.entities.Carta;
import lombok.Data;

import javax.persistence.*;

@Data
public class UtentiDto {

    private int id_cliente;

    private String nome;

    private String cognome;

    private int eta;

    private String indirizzo;

}
