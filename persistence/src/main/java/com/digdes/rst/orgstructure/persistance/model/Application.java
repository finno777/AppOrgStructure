package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @ApiModelProperty("id")
    private Long id;
    @Column
    @ApiModelProperty("id портлета")
    public String appId;
    @Column
    @ApiModelProperty("id ноды")
    public String nodeId;
    @Column
    @ApiModelProperty("uri страницы")
    public String pageUri;
    @Column
    @ApiModelProperty("Id организации")
    private Long idOrganization;

}
