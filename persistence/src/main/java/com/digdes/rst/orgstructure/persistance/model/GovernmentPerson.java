package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="GovernmentPerson", description="Начальник управления")
@Data
@NoArgsConstructor
@Entity
public class GovernmentPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("Участник")
    @ManyToOne
    private Person person;

    @Transient
    private Government government;

    @ApiModelProperty("Должность")
    private String position;
}
