package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.GroupOrganizationsDao;
import com.digdes.rst.orgstructure.persistance.dao.OrganizationDao;
import com.digdes.rst.orgstructure.persistance.dao.SubGroupOrganizationsDao;
import com.digdes.rst.orgstructure.persistance.dao.TypeOrganizationDao;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.GroupOrganizationsService;
import com.digdes.rst.orgstructure.persistance.services.TypeOrganizationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class TypeOrganizationServiceImpl implements TypeOrganizationService {

    @Autowired
    TypeOrganizationDao typeOrganizationDao;

    @Autowired
    GroupOrganizationsDao groupOrganizationsDao;

    @Autowired
    OrganizationDao organizationDao;

    @Override
    public List<TypeOrganization> getByApp(SubdivisionApplication application) {
        return typeOrganizationDao.getByApp(application);
    }

    @Override
    public void save(TypeOrganization typeOrganization) {
        if(typeOrganization.getDeleted()==null){
            typeOrganization.setDeleted(false);
        }
        typeOrganizationDao.saveOrUpdate(typeOrganization);
    }

    @Override
    public void deleteById(Long id) {
        TypeOrganization byId = typeOrganizationDao.findById(id);
        byId.setDeleted(true);
        groupOrganizationsDao.deleteByType(byId);
        organizationDao.deleteByType(byId);
        typeOrganizationDao.saveOrUpdate(byId);
    }

    @Override
    public List<TypeOrganization> getFullByApp(SubdivisionApplication application) {
        List<TypeOrganization> byApp = typeOrganizationDao.getByApp(application);
        for(TypeOrganization typeOrganization :byApp){
            typeOrganization.setGroupOrganizations(groupOrganizationsDao.getByType(typeOrganization));
            typeOrganization.setOrganizations(organizationDao.getByType(typeOrganization));
        }
        return byApp;
    }

    @Override
    public TypeOrganization getById(Long idType) {
        return typeOrganizationDao.findById(idType);
    }
}
