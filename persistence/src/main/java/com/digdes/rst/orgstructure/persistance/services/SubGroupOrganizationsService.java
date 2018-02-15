package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;

import java.util.List;

public interface SubGroupOrganizationsService {
    List<SubGroupOrganizations> getSimilarSubGroupByID(Long idSubGroup);

    SubGroupOrganizations getById(Long idSubgroup);

    void save(SubGroupOrganizations subGroupOrganizations);

    void deleteById(Long id);

    List<SubGroupOrganizations> getSubGroupOrganizationGovernment();

    List<SubGroupOrganizations> getAllActive();
}

