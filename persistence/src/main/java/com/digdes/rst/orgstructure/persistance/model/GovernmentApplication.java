package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="GovernmentApplication", description="Модель приложения")
@Data
@NoArgsConstructor
@ToString
@Entity
public class GovernmentApplication {
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

}
