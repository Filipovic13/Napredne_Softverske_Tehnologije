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
public class MemberChangeDepartmentDTO implements Serializable {

    @NotBlank(message = "Department name must be entered!")
    private String departmentName;

}
