package com.nst.domaci1.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO implements Serializable {

    private Long id;

    private String name;

    private int espb;

    private String department;

}
