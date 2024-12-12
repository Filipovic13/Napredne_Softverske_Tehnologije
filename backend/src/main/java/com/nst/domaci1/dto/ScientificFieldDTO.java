package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScientificFieldDTO implements Serializable {

    @NotBlank(message = "Scientific field code must be entered!")
    private String code;

    @NotBlank(message = "Scientific field name must be entered!")
    private String name;

}
