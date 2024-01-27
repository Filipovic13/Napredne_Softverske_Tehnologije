package com.nst.domaci1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class SubjectChangeEspbDTO implements Serializable {

    @Min(value = 2, message = "Minimum value for espb is 2")
    @Max(value = 6, message = "Maximum value for espb is 6")
    private int espb;

    public SubjectChangeEspbDTO() {
    }

    public SubjectChangeEspbDTO(int espb) {
        this.espb = espb;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }
}
