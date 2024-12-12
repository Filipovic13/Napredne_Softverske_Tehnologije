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
public class MemberChangeAllScienceFieldsDTO implements Serializable {

    @NotBlank(message = "Academic title must be entered!")
    private String academicTitle;

    @NotBlank(message = "Education title must be entered!")
    private String educationTitle;

    @NotBlank(message = "Science field must be entered!")
    private String scienceField;

}
