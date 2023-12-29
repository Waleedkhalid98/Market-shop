package com.xantrix.webapp.entities;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;

@Data
public class Numeri {

    @Min(value = (int) 1, message = "{Min.Numeri.array.Validation}")
    ArrayList<Integer> a;
}
