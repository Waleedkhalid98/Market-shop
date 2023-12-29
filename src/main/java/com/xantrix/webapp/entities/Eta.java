package com.xantrix.webapp.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
public class Eta {

    @Min(value = 1, message = "Il valore di eta deve essere almeno 1")
    @Max(value = Integer.MAX_VALUE, message = "Il valore di eta deve essere al massimo " + Integer.MAX_VALUE)
    private int eta ;
    @Id
    private Long id;



}
