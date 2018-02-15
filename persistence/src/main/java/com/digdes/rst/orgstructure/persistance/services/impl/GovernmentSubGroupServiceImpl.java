package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentSubGroupDao;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.GovernmentSubGroupService;
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
public class GovernmentSubGroupServiceImpl implements GovernmentSubGroupService {
    @Autowired
    GovernmentSubGroupDao governmentSubGroupDao;

    @Override
    public List<GovernmentSubGroup> save(Government government) {
        List<SubGroupOrganizations> subGroupOrganizations = government.getSubGroupOrganizations();
        List<GovernmentSubGroup> governmentSubGroups = new ArrayList<>();
        if(subGroupOrganizations!=null && !subGroupOrganizations.isEmpty()) {
            for (SubGroupOrganizations subGroupOrganizations1 : subGroupOrganizations) {
                GovernmentSubGroup subGroup = new GovernmentSubGroup();
                subGroup.setGovernment(government);
                subGroup.setSubGroupOrganizations(subGroupOrganizations1);
                GovernmentSubGroup governmentSubGroup = governmentSubGroupDao.getByGovernmentSubGroup(subGroup);
                if(governmentSubGroup!=null && governmentSubGroup.getId()!=null) {
                    subGroup = governmentSubGroup;
                }else {
                    governmentSubGroupDao.saveOrUpdate(subGroup);
                }
                governmentSubGroups.add(subGroup);
            }
            List<GovernmentSubGroup> subGroupsDB = governmentSubGroupDao.getByGovernment(government);
            List<Long> newIds = governmentSubGroups.stream().map(GovernmentSubGroup::getId).collect(Collectors.toList());
            for(GovernmentSubGroup governmentSubGroup : subGroupsDB){
                Long id = governmentSubGroup.getId();
                if(!newIds.contains(id)){
                    governmentSubGroupDao.delete(governmentSubGroup);
                }
            }
        }else{
            List<GovernmentSubGroup> subGroupsDB = governmentSubGroupDao.getByGovernment(government);
            if(subGroupsDB!=null) {
                for (GovernmentSubGroup governmentSubGroup : subGroupsDB) {
                    governmentSubGroupDao.delete(governmentSubGroup);
                }
            }
        }
        return governmentSubGroups;
    }

    private boolean checkGovernmentSubGroups(List<GovernmentSubGroup> governmentSubGroups, Long id){
        for(GovernmentSubGroup governmentSubGroup : governmentSubGroups){
            if(governmentSubGroup.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SubGroupOrganizations> getByGovernment(Long governmentID) {
        Government government = new Government();
        government.setId(governmentID);
        List<GovernmentSubGroup> byGovernment = governmentSubGroupDao.getByGovernment(government);
        List<SubGroupOrganizations> subGroupOrganizations = new ArrayList<>();
        LinksUtils linksUtils = new LinksUtils();
        for(GovernmentSubGroup governmentSubGroup: byGovernment){
            SubGroupOrganizations subGroupOrganization = governmentSubGroup.getSubGroupOrganizations();
            SubdivisionApplication application = subGroupOrganization.getGroupOrganizations().getTypeOrganization().getApplication();
            String pageUri = application.getPageUri();
            String appId = application.getAppId();

            Map<String, String[]> ps = new HashMap<>();
            ps.put("action",new String[]{"groupView"});
            ps.put("id",new String[]{subGroupOrganization.getGroupOrganizations().getId().toString()});
            ps.put("idSubGroup",new String[]{subGroupOrganization.getId().toString()});
            subGroupOrganization.setLink(linksUtils.createURI(appId, pageUri, ps));

            subGroupOrganizations.add(subGroupOrganization);
        }
        return subGroupOrganizations;
    }
}
