package com.nst.domaci1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    private String name;

    private String shortName;

    @OneToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private Member supervisor;

    @OneToOne
    @JoinColumn(name = "secretary_id", referencedColumnName = "id")
    private Member secretary;

}
