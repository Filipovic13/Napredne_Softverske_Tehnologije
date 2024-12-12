package com.nst.domaci1.dto;

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
public class EducationTitleDTO implements Serializable {

    @NotNull(message = "Education title code must be entered!")
    private String code;

    @NotNull(message = "Education title name must be entered!")
    private String name;

}
