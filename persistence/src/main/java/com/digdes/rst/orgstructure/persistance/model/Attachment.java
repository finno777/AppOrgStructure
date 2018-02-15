package com.digdes.rst.orgstructure.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@ApiModel(value="Attachment", description="Модель вложения")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Attachment {
    @ApiModelProperty(value = "Идентификатор вложения", required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "UUID", required = false)
    @Column(nullable = false)
    private Long uuid;

    @ApiModelProperty(value = "Уникальный ID", required = false)
    @Column(nullable = true)
    private Long uniqID;

    @ApiModelProperty(value = "Имя", required = false)
    @Column(length = 255)
    private String fileName;

    @ApiModelProperty(value = "Расширение", required = false)
    @Column(length = 10)
    private String extName;

    @ApiModelProperty(value = "Размер", required = false)
    @Column
    private String size;

    @ApiModelProperty(value = "Тип", required = false)
    @Column
    private String type;

    @ApiModelProperty(value = "Путь", required = false)
    @Transient
    private String path;

}
