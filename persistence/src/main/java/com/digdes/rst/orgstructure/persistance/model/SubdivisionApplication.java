package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="SubdivisionApplication", description="Модель приложения")
@Data
@NoArgsConstructor
@ToString
@Entity
public class SubdivisionApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @ApiModelProperty("id")
    private Long id;
    @Column
    @ApiModelProperty("id портлета")
    public String appId;
    @Column
    @ApiModelProperty("uri страницы")
    public String pageUri;
    @Column(columnDefinition = "int default 0",nullable = false)
    @ApiModelProperty("Тип приложения")
    public ApplicationType typeApplication;


    public enum ApplicationType {
        SUBDIVISION,
        TERRITORIAL
    }

    public SubdivisionApplication(String appId) {
        this.appId = appId;
        this.typeApplication = ApplicationType.SUBDIVISION;
    }
}
