package com.xantrix.webapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Carta {

    @Id
    @Column(name = "NUMERO")
    private int numero;


    @Column(name = "PUNTI")
    private int punti;

    @MapsId
    @OneToOne
    @JoinColumn(name = "ID_CLIENTE")
    @JsonIgnore
    private Utenti utenti;
}
