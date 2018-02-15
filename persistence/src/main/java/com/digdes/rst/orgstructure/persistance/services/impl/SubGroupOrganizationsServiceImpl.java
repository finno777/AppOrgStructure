package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.SubGroupOrganizations;
import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
import com.digdes.rst.orgstructure.persistance.model.TypeOrganization;
import com.digdes.rst.orgstructure.persistance.services.GroupOrganizationsService;
import com.digdes.rst.orgstructure.persistance.services.SubGroupOrganizationsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Log4j
@Transactional
public class SubGroupOrganizationsServiceImpl implements SubGroupOrganizationsService {
    @Autowired
    SubGroupOrganizationsDao subGroupOrganizationsDao;

    @Autowired
    GroupOrganizationsService groupOrganizationsService;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SubdivisionApplicationDao subdivisionApplicationDao;

    @Autowired
    GroupOrganizationsDao groupOrganizationsDao;

    @Autowired
    TypeOrganizationDao typeOrganizationDao;

    @Override
    public List<SubGroupOrganizations> getSimilarSubGroupByID(Long idSubGroup) {
        SubGroupOrganizations byId = subGroupOrganizationsDao.findById(idSubGroup);
        GroupOrganizations groupOrganizations = byId.getGroupOrganizations();
        return subGroupOrganizationsDao.getByGroup(groupOrganizations);
    }

    @Override
    public SubGroupOrganizations getById(Long idSubGroup) {
        return subGroupOrganizationsDao.findById(idSubGroup);
    }

    @Override
    public void save(SubGroupOrganizations subGroupOrganizations) {
        if(subGroupOrganizations.getId()!=null){
            SubGroupOrganizations subGroupDB = subGroupOrganizationsDao.findById(subGroupOrganizations.getId());
            subGroupDB.setTitle(subGroupOrganizations.getTitle());
            subGroupDB.setDeleted(subGroupOrganizations.getDeleted());
            subGroupOrganizations = subGroupDB;
        }

        if(subGroupOrganizations.getDeleted()==null){
            subGroupOrganizations.setDeleted(false);
        }
        subGroupOrganizationsDao.saveOrUpdate(subGroupOrganizations);
    }

    @Override
    public void deleteById(Long id) {
        SubGroupOrganizations byId = subGroupOrganizationsDao.findById(id);
        organizationDao.deleteBySubGroup(byId);
        byId.setDeleted(true);
        subGroupOrganizationsDao.saveOrUpdate(byId);
    }

    @Override
    public List<SubGroupOrganizations> getSubGroupOrganizationGovernment() {
        List<SubdivisionApplication> appByAppType = subdivisionApplicationDao.getAppByAppType(SubdivisionApplication.ApplicationType.TERRITORIAL);
        List<TypeOrganization> typeOrganizations = typeOrganizationDao.getByApps(appByAppType);
        List<GroupOrganizations> groupOrganizations = groupOrganizationsDao.getAllByTypes(typeOrganizations);
        return subGroupOrganizationsDao.getByGroups(groupOrganizations);
    }

    @Override
    public List<SubGroupOrganizations> getAllActive() {
        return subGroupOrganizationsDao.getAllActive();
    }
}
