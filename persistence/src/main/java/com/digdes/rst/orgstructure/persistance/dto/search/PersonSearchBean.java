package com.digdes.rst.orgstructure.persistance.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Samoylov Ilya
 *         Date: 25.01.18.
 *         Copyright http://digdes.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonSearchBean {

    private String name;

    private String phone;

    private String email;

    private String position;

    private Map<String, String> attributes;
}
