package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */

@ApiModel(value="GovernmentSubGroup", description="Подгруппа Управления")
@Data
@NoArgsConstructor
@ToString
@Entity
public class GovernmentSubGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Упраление", required = true)
    @ManyToOne
    private Government government;

    @ApiModelProperty(value = "Подгруппа", required = true)
    @ManyToOne
    private SubGroupOrganizations subGroupOrganizations;
}
