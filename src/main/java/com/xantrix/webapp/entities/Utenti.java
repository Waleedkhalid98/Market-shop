package com.xantrix.webapp.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class Utenti {

    @Id
    @Column(name="ID_CLIENTE")
    private int id_cliente;

    @Column(name="NOME")
    private String nome;

    @Column(name="COGNOME")
    private String cognome;

    @Column(name="ETA")
    private int eta;

    @Column(name = "INDIRIZZO")
    private String indirizzo;

    @OneToOne(mappedBy = "utenti", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Carta carta;

}
