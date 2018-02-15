package com.digdes.rst.orgstructure.persistance.dto;

import com.digdes.rst.orgstructure.persistance.model.Person;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ApiModel(value="PersonSearchDto", description="Запрос поиска Person")
@Data
@NoArgsConstructor
@ToString
public class PersonSearchDto {
    @ApiModelProperty("ФИО")
    String fio;
    @ApiModelProperty("Роли")
    List<Person.Role> roles;
}
