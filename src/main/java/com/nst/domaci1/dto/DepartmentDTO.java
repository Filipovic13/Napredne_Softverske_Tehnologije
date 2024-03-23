package com.nst.domaci1.dto;


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
public class DepartmentDTO implements Serializable {

    private String name;

    private String shortName;

    @Schema(nullable = true)
    private Long supervisorId;

    @Schema(nullable = true)
    private Long secretaryId;


}
