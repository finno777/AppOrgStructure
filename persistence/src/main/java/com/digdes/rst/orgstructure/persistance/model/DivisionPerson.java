package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="DivisionPerson", description="Сотрудник отдела")
@Data
@NoArgsConstructor
@Entity
public class DivisionPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("Участник")
    @ManyToOne
    private Person person;

    @ApiModelProperty("Отдел")
    @ManyToOne
    @JsonIgnoreProperties(value = { "personList" })
    private Division division;

    @ApiModelProperty("Должность")
    private String position;

    @Override
    public String toString() {
        return "DivisionPerson{" +
                "id=" + id +
                ", person=" + person +
                ", division={id=" + division.getId() + "}" +
                ", position='" + position + '\'' +
                '}';
    }
}
