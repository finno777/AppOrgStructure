package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="OrganizationPerson", description="Сотрудник организации")
@Data
@NoArgsConstructor
@Entity
public class OrganizationPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("Участник")
    @ManyToOne
    private Person person;

    @ApiModelProperty("Организация")
    @ManyToOne
    @JsonIgnoreProperties(value = { "organization" })
    private Organization organization;

    @ApiModelProperty("Должность")
    private String position;


    @Override
    public String toString() {
        return "OrganizationPerson{" +
                "id=" + id +
                ", person=" + person +
                ", organization={id=" + organization.getId() + "}" +
                ", position='" + position + '\'' +
                '}';
    }
}
