package com.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@NamedStoredProcedureQuery(
        name = "GetMoviesPerHallByLocation",
        procedureName = "GetMoviesPerHallByLocation",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "locId", type = Integer.class)
        },
        resultClasses = PieVisualizeEntity.class
)
public class PieVisualizeEntity {
    @Id
    private Long id;

    private String hall;

    private Integer movies;
}
