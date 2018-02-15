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

@ApiModel(value="GovernmentOrganization", description="Организация Управления")
@Data
@NoArgsConstructor
@ToString
@Entity
public class GovernmentOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Упраление", required = true)
    @ManyToOne
    private Government government;

    @ApiModelProperty(value = "Организация", required = true)
    @ManyToOne
    private Organization organization;
}
