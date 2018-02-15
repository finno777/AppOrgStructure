package com.digdes.rst.orgstructure.persistance.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="OrganizationPhone", description="Телефоны организации")
@Data
@NoArgsConstructor
@Entity
public class OrganizationPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Телефон", required = true)
    @Column(length = 64, nullable = false)
    private String phone;

    @ApiModelProperty(value = "Организация", required = true)
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Override
    public String toString() {
        return "OrganizationPhone{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", organization={id=" + organization.getId() +"}" +
                '}';
    }
}
