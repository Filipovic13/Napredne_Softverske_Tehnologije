package com.nst.domaci1.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentSetManagerDTO implements Serializable {

    @NotBlank(message = "Manager role must be entered!")
    private String managerRole;

    @Schema(nullable = true)
    private Long memberId;
}
