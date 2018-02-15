package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@ApiModel(value="Government", description="Модель управления")
@Data
@NoArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class Government {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Название", required = true)
    @Column(length = 512, nullable = false)
    private String title;

    @ApiModelProperty(value = "ссылка на иконку", required = true)
    @Column(nullable = false, length = 255)
    private String uuid;

    @ManyToOne(fetch = EAGER)
    @ApiModelProperty(value = "Руководитель", required = false)
    private GovernmentPerson person;

    @ManyToOne
    @OnDelete(action = NO_ACTION)
    @ApiModelProperty("Связь управления и портлета")
    private  GovernmentApplication application;

    @ApiModelProperty(value = "Кураторы", required = false)
    @Transient
    private List<CuratorPerson> curators;

    @ApiModelProperty(value = "Отделы", required = false)
    @Transient
    private List<Division> divisions;

    @ApiModelProperty(value = "Положение об управлении", required = false)
    @Column(length = 49152, nullable = false)
    private String description;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @ApiModelProperty("Порядковый номер")
    @Column(nullable = true)
    private Long position;

    @Transient
    @ApiModelProperty("Ссылка на элемент")
    private String link;

    @Transient
    @ApiModelProperty("Связанные организации")
    private List<Organization> organizations;

    @Transient
    @ApiModelProperty("Связанные группы")
    private List<GroupOrganizations> groupOrganizations;

    @Transient
    @ApiModelProperty("Связанные подгруппы")
    private List<SubGroupOrganizations> subGroupOrganizations;

}
