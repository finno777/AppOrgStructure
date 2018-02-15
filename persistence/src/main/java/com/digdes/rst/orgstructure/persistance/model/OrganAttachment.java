package com.digdes.rst.orgstructure.persistance.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString
@Entity
public class OrganAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Орган", required = true)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Organ organ;

    @ApiModelProperty(value = "Файл", required = true)
    @ManyToOne
    private Attachment attachment;

}
