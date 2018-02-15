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
public class PersonAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "Юзер", required = true)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @ApiModelProperty(value = "Файл", required = true)
    @ManyToOne
    private Attachment attachment;
}
