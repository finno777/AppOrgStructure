package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.GovernmentOrganizationService;
import com.digdes.rst.orgstructure.persistance.utils.LinksUtils;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */

@Service
@Log4j
@Transactional
public class GovernmentOrganizationServiceImpl implements GovernmentOrganizationService {
    @Autowired
    GovernmentOrganizationDao governmentOrganizationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    TypeOrganizationDao typeOrganizationDao;

    @Autowired
    SubdivisionApplicationDao subdivisionApplicationDao;

    @Autowired
    SubGroupOrganizationsDao subGroupOrganizationsDao;

    @Autowired
    GroupOrganizationsDao groupOrganizationsDao;

    @Override
    public List<GovernmentOrganization> save(Government government) {
        List<Organization> organizations = government.getOrganizations();
        List<GovernmentOrganization> governmentOrganizations = new ArrayList<>();
        if(organizations!=null && !organizations.isEmpty()) {
            for (Organization organization : organizations) {
                GovernmentOrganization governmentOrganization = new GovernmentOrganization();
                governmentOrganization.setGovernment(government);
                governmentOrganization.setOrganization(organization);
                GovernmentOrganization byGovernmentOrganization = governmentOrganizationDao.getByGovernmentOrganization(governmentOrganization);
                if(byGovernmentOrganization!=null && byGovernmentOrganization.getId()!=null){
                    governmentOrganization=byGovernmentOrganization;
                }else{
                    governmentOrganizationDao.saveOrUpdate(governmentOrganization);
                }
                governmentOrganizations.add(governmentOrganization);
            }
            List<GovernmentOrganization> governmentGroupsDB = governmentOrganizationDao.getByGovernment(government);
            List<Long> newIds = governmentOrganizations.stream().map(GovernmentOrganization::getId).collect(Collectors.toList());
            for(GovernmentOrganization governmentOrganization : governmentGroupsDB){
                Long id = governmentOrganization.getId();
                if(!newIds.contains(id)){
                    governmentOrganizationDao.delete(governmentOrganization);
                }
            }
        }else{
            List<GovernmentOrganization> governmentGroupsDB = governmentOrganizationDao.getByGovernment(government);
            if(governmentGroupsDB!=null) {
                if (governmentGroupsDB != null) {
                    for (GovernmentOrganization governmentOrganization : governmentGroupsDB) {
                        governmentOrganizationDao.delete(governmentOrganization);
                    }
                }
            }
        }
        return governmentOrganizations;
    }

    private boolean checkGovernmentOrganizations(List<GovernmentOrganization> governmentOrganizations, Long id){
        for(GovernmentOrganization governmentOrganization : governmentOrganizations){
            if(governmentOrganization.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Organization> getByGovernment(Long governmentID) {
        Government government = new Government();
        government.setId(governmentID);
        List<GovernmentOrganization> byGovernment = governmentOrganizationDao.getByGovernment(government);
        List<Organization> organizations = new ArrayList<>();
        LinksUtils linksUtils = new LinksUtils();
        for(GovernmentOrganization governmentOrganization : byGovernment){
            Organization organization = governmentOrganization.getOrganization();
            organization = organizationDao.findById(organization.getId());
            SubdivisionApplication application =null;
            if(organization.getTypeOrganization()!=null) {
                TypeOrganization byId = typeOrganizationDao.findById(organization.getTypeOrganization().getId());
                application = subdivisionApplicationDao.findById(byId.getApplication().getId());
            }else{
                SubGroupOrganizations subGroupOrganizations = subGroupOrganizationsDao.findById(organization.getSubGroupOrganizations().getId());
                GroupOrganizations groupOrganizations = groupOrganizationsDao.findById(subGroupOrganizations.getGroupOrganizations().getId());
                application = subdivisionApplicationDao.findById(groupOrganizations.getTypeOrganization().getId());
            }
            String pageUri = application.getPageUri();
            String appId = application.getAppId();
            Map<String, String[]> ps = new HashMap<>();
            ps.put("action",new String[]{"organizationView"});
            ps.put("id",new String[]{organization.getId().toString()});
            organization.setLink(linksUtils.createURI(appId, pageUri, ps));

            organizations.add(organization);
        }
        return organizations;
    }
}
