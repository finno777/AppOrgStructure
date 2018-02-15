package com.digdes.rst.orgstructure.persistance.dto.search;

import com.digdes.rst.orgstructure.persistance.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Samoylov Ilya
 *         Date: 25.01.18.
 *         Copyright http://digdes.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganSearchBean {

    private String name;
    private String description;
    private String documents;
    private String decision;
    private String schedule;
    private String conditions;
    private String meeting;
    private String plan;
    private String details;
    private String situation;
    private List<PersonSearchBean> people;
}
