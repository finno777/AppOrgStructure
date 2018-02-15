package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@ApiModel(value="CuratorPerson", description="Куратор управления")
@Data
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class CuratorPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("Участник")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @ApiModelProperty("Упраление")
    @ManyToOne
    private Government government;

    @ApiModelProperty("Описание")
    @Column(length = 400)
    private String description;

    @Transient
    private String link;

    @Override
    public String toString() {
        return "CuratorPerson{" +
                "id=" + id +
                ", person=" + person +
                ", government={id=" + government.getId()+ "}" +
                ", description='" + description + '\'' +
                '}';
    }
}
