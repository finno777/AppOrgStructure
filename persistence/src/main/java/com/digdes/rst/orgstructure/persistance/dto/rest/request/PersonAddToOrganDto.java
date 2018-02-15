package com.digdes.rst.orgstructure.persistance.dto.rest.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Samoylov Ilya
 *         Date: 09.01.18.
 *         Copyright http://digdes.com
 */

@ApiModel(value = "PersonAddToOrganDto", description = "Request DTO на добавление человека в организацию")
@Data
@NoArgsConstructor
@ToString
public class PersonAddToOrganDto {

    @ApiModelProperty(value = "Ид организации", required = true)
    @NotNull
    private Long organId;

    @ApiModelProperty(value = "Ид человека. null - если добавляется новый человек, id - если из предложенного списка")
    private Long personId;

    @ApiModelProperty(value = "Имя", required = true)
    @NotNull
    @Size(max = 50)
    private String name;

    @ApiModelProperty("Должность")
    @Size(max = 1000)
    private String position;

    @ApiModelProperty("Телефон")
    @Size(max = 30)
    private String phone;

    @ApiModelProperty("Электронная почта")
    @Email
    private String email;

}
