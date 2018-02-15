package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;

import java.util.List;

import static org.hibernate.annotations.OnDeleteAction.NO_ACTION;

@ApiModel(value="TypeOrganization", description="Модель типа организации")
@Data
@NoArgsConstructor
@ToString
@Entity
public class TypeOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Имя категории", required = true)
    @Column(length = 64, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = NO_ACTION)
    @ApiModelProperty("Связь Категории и портлета")
    private  SubdivisionApplication application;

    @ApiModelProperty("Удалена")
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted;

    @Transient
    private List<Organization> organizations;

    @Transient
    private List<GroupOrganizations> groupOrganizations;


}
