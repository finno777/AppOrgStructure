package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@ApiModel(value="OrganizationAttachment", description="Файлы организации")
@Data
@NoArgsConstructor
@ToString
@Entity
public class OrganizationAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Родительская организация", required = true)
    @ManyToOne
    private Organization organization;

    @ApiModelProperty(value = "Файл", required = true)
    @ManyToOne
    private Attachment attachment;
}
