package com.digdes.rst.orgstructure.persistance.services;

import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;

import java.util.List;

public interface TypeOrganizationService {
    List<TypeOrganization> getByApp(SubdivisionApplication application);

    void save(TypeOrganization typeOrganization);

    void deleteById(Long id);

    List<TypeOrganization> getFullByApp(SubdivisionApplication application);

    TypeOrganization getById(Long idType);
}
