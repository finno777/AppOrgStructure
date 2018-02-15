package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.GovernmentGroupDao;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.GovernmentGroupService;
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
public class GovernmentGroupServiceImpl  implements GovernmentGroupService{

    @Autowired
    GovernmentGroupDao governmentGroupDao;



    @Override
    public List<GovernmentGroup> save(Government government) {
        List<GroupOrganizations> groupOrganizations = government.getGroupOrganizations();
        List<GovernmentGroup> governmentGroups = new ArrayList<>();
        if(groupOrganizations!=null && !groupOrganizations.isEmpty()) {
            for (GroupOrganizations organizations : groupOrganizations) {
                GovernmentGroup governmentGroup = new GovernmentGroup();
                governmentGroup.setGovernment(government);
                governmentGroup.setGroupOrganizations(organizations);
                GovernmentGroup byGovernmentGroup = governmentGroupDao.getByGovernmentGroup(governmentGroup);
                if(byGovernmentGroup!=null && byGovernmentGroup.getId()!=null){
                    governmentGroup = byGovernmentGroup;
                }else {
                    governmentGroupDao.saveOrUpdate(governmentGroup);
                }
                governmentGroups.add(governmentGroup);
            }
            List<GovernmentGroup> governmentGroupsDB = governmentGroupDao.getByGovernment(government);
            List<Long> newIds = governmentGroups.stream().map(GovernmentGroup::getId).collect(Collectors.toList());
            for(GovernmentGroup governmentGroup : governmentGroupsDB){
                Long id = governmentGroup.getId();
                if(!newIds.contains(id)){
                    governmentGroupDao.delete(governmentGroup);
                }
            }
        }else{
            List<GovernmentGroup> governmentGroupsDB = governmentGroupDao.getByGovernment(government);
            for(GovernmentGroup governmentGroup : governmentGroupsDB){
                governmentGroupDao.delete(governmentGroup);
            }
        }
        return governmentGroups;
    }

    private boolean checkGovernmentGroups(List<GovernmentGroup> governmentGroups, Long id){
        for(GovernmentGroup governmentGroupA : governmentGroups){
            if(governmentGroupA.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<GroupOrganizations> getByGovernment(Long governmentID) {
        Government government = new Government();
        government.setId(governmentID);
        List<GovernmentGroup> byGovernment = governmentGroupDao.getByGovernment(government);
        List<GroupOrganizations> organizationsList = new ArrayList<>();
        LinksUtils linksUtils = new LinksUtils();
        for (GovernmentGroup governmentGroup: byGovernment){
            SubdivisionApplication application = governmentGroup.getGroupOrganizations().getTypeOrganization().getApplication();
            String pageUri = application.getPageUri();
            String appId = application.getAppId();
            Map<String, String[]> ps = new HashMap<>();
            ps.put("action",new String[]{"groupView"});
            GroupOrganizations groupOrganizations = governmentGroup.getGroupOrganizations();
            ps.put("idGroup",new String[]{groupOrganizations.getId().toString()});
            groupOrganizations.setLink(linksUtils.createURI(appId, pageUri, ps));
            organizationsList.add(groupOrganizations);
        }
        return organizationsList;
    }


}
