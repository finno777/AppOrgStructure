package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ApiModel(value="GroupOrganizations", description="Модель группы")
@Data
@NoArgsConstructor
@Entity
public class GroupOrganizations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Полное наименование", required = true)
    @Column(length = 248, nullable = false)
    private String title;

    @ApiModelProperty(value = "Краткое наименование", required = true)
    @Column(length = 64, nullable = false)
    private String titleShort;

    @ApiModelProperty(value = "Тип организации", required = true)
    @ManyToOne
    private TypeOrganization typeOrganization;

    @ApiModelProperty(value = "Описание", required = false)
    @Column(length = 8192, nullable = false)
    private String description;

    @ApiModelProperty(value = "ссылка на иконку", required = true)
    @Column(nullable = false, length = 255)
    private String uuid;

    @ApiModelProperty(value = "Подгруппы", required = false)
    @Transient
    @JsonIgnoreProperties(value = { "groupOrganizations" })
    private List<SubGroupOrganizations> subGroups;

    @ApiModelProperty(value = "Файлы", required = false)
    @Transient
    private List<Attachment> attachments;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Transient
    @ApiModelProperty("Ссылка на элемент")
    private String link;

    @Override
    public String toString() {
        return "GroupOrganizations{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", titleShort='" + titleShort + '\'' +
                ", typeOrganization={id=" + typeOrganization.getId()+"}" +
                ", description='" + description + '\'' +
                ", uuid=" + uuid +
                ", subGroups=" + subGroups +
                ", attachments=" + attachments +
                ", deleted=" + deleted +
                ", link='" + link + '\'' +
                '}';
    }
}
