package com.nst.domaci1.dto;


import com.nst.domaci1.domain.Member;
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

    private Long supervisorId;

    private Long secretaryId;


}
