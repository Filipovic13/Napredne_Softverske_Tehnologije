package com.nst.domaci1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectChangeEspbDTO implements Serializable {

    @NotNull(message = "ESPB points must be entered!")
    @Min(value = 2, message = "Minimum value for espb is 2")
    @Max(value = 6, message = "Maximum value for espb is 6")
    private int espb;

}
