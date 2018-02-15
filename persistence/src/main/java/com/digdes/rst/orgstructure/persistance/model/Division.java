package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ApiModel(value="Division", description="Модель отдела")
@Data
@NoArgsConstructor
@ToString
@Entity
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Название", required = false)
    @Column(length = 300, nullable = false)
    private String title;

    @ApiModelProperty(value = "Задачи отдела", required = false)
    @Column(length = 49152, nullable = false)
    private String description;

    @ApiModelProperty(value = "ссылка на иконку", required = true)
    @Column(nullable = false, length = 255)
    private String uuid;

    @ManyToOne
    @ApiModelProperty(value = "Управление", required = false)
    private Government government;

    @Transient
    @ApiModelProperty(value = "Участники", required = false)
    @JsonIgnoreProperties(value = {"division"})
    private List<DivisionPerson> personList;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", uuid=" + uuid +
                ", government={id=" + government.getId()+ "}" +
                ", personList=" + personList +
                ", deleted=" + deleted +
                '}';
    }
}
