package com.nst.domaci1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class SubjectSaveUpdateDTO implements Serializable {

    @NotBlank(message = "Subject name must be entered!")
    private String name;

    @Min(value = 2, message = "Value must be greater than or equal to 2")
    @Max(value = 6, message = "Value must be less than or equal to 6")
    private int espb;

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

}
