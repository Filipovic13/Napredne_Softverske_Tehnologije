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
public class MemberDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Firstname must be entered!")
    private String firstName;

    @NotBlank(message = "Lastname must be entered!")
    private String lastName;

    @NotBlank(message = "Academic title must be entered!")
    private String academicTitle;

    @NotBlank(message = "Education title must be entered!")
    private String educationTitle;

    @NotBlank(message = "Scientific field  must be entered!")
    private String scientificField;

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

}
