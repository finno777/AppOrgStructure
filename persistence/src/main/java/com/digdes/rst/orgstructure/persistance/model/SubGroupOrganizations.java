package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ApiModel(value="SubGroupOrganizations", description="Модель подгруппы")
@Data
@NoArgsConstructor
@Entity
public class SubGroupOrganizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Наименование", required = true)
    @Column(length = 248, nullable = false)
    private String title;

    @ManyToOne
    @ApiModelProperty(value = "Родительская группа", required = true)
    private GroupOrganizations groupOrganizations;

    @Transient
    @JsonIgnoreProperties(value = { "subGroupOrganizations" })
    private List<Organization> organizations;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Transient
    @ApiModelProperty("Ссылка на элемент")
    private String link;

    @Override
    public String toString() {
        return "SubGroupOrganizations{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", groupOrganizations={id={" + groupOrganizations.getId() +"}" +
                ", organizations=" + organizations +
                ", deleted=" + deleted +
                ", link='" + link + '\'' +
                '}';
    }
}
