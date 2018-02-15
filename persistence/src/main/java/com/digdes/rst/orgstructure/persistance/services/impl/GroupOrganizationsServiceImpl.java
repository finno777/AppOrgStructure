package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.GroupOrganizationsService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j
@Transactional
public class GroupOrganizationsServiceImpl implements GroupOrganizationsService {
    @Autowired
    GroupOrganizationsDao groupOrganizationsDao;

    @Autowired
    SubGroupOrganizationsDao subGroupOrganizationsDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    TypeOrganizationDao typeOrganizationDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    GroupAttachmentDao groupAttachmentDao;

    @Autowired
    SubdivisionApplicationDao subdivisionApplicationDao;

    @Override
    public GroupOrganizations getFullGroupById(Long idGroup) {
        GroupOrganizations group = groupOrganizationsDao.findById(idGroup);
        List<SubGroupOrganizations> subGroups = subGroupOrganizationsDao.getByGroup(group);
        for(SubGroupOrganizations subGroupOrganizations: subGroups){
            List<Organization> organizations = organizationDao.getBySubGroup(subGroupOrganizations);
            subGroupOrganizations.setOrganizations(organizations);
        }
        List<GroupAttachment> byGroup = groupAttachmentDao.getByGroup(group);
        if(byGroup!=null) {
            group.setAttachments(getAttachment(byGroup));
        }
        group.setSubGroups(subGroups);
        return group;
    }

    @Override
    public List<GroupOrganizations> getSimilarGroupByTypeId(Long idType) {
        TypeOrganization typeOrganization = typeOrganizationDao.findById(idType);
        return groupOrganizationsDao.getByType(typeOrganization);
    }

    @Override
    public void save(GroupOrganizations groupOrganizations) {
        Boolean newGroup = groupOrganizations.getId()==null;
        if(groupOrganizations.getDeleted()==null){
            groupOrganizations.setDeleted(false);
        }
        groupOrganizationsDao.saveOrUpdate(groupOrganizations);
        List<Attachment> attachments = groupOrganizations.getAttachments();
        List<GroupAttachment> groupAttachments = groupAttachmentDao.getByGroup(groupOrganizations);
        List<Attachment> attachmentsDB = getAttachment(groupAttachments);
        List<SubGroupOrganizations> subGroups = groupOrganizations.getSubGroups();
        List<SubGroupOrganizations> subGroupsDB = subGroupOrganizationsDao.getByGroup(groupOrganizations);
        if(subGroups !=null && !subGroups.isEmpty()){
            for(SubGroupOrganizations subGroupOrganizations : subGroups){
                if(subGroupOrganizations.getId()!=null){
                    SubGroupOrganizations byId = subGroupOrganizationsDao.findById(subGroupOrganizations.getId());
                    byId.setTitle(subGroupOrganizations.getTitle());
                    byId.setDeleted(subGroupOrganizations.getDeleted());
                    byId.setOrganizations(subGroupOrganizations.getOrganizations());
                    subGroupOrganizations = byId;
                }
                subGroupOrganizations.setGroupOrganizations(groupOrganizations);
                subGroupOrganizationsDao.saveOrUpdate(subGroupOrganizations);
            }
            if(!newGroup && subGroupsDB!=null && !subGroupsDB.isEmpty()){
                List<Long> newIds = subGroups.stream().map(SubGroupOrganizations::getId).collect(Collectors.toList());
                for(SubGroupOrganizations subGroupOrganizations : subGroupsDB){
                    if(!newIds.contains(subGroupOrganizations.getId())){
                        subGroupOrganizationsDao.deleteByEntity(subGroupOrganizations);
                    }
                }
            }
        }else{
            for(SubGroupOrganizations subGroupOrganizations : subGroupsDB){
                subGroupOrganizationsDao.deleteByEntity(subGroupOrganizations);
            }
        }
        if (attachments != null) {
            if (newGroup || attachmentsDB!=null && !attachmentsDB.isEmpty()) {
                for (Attachment attachment : attachments) {
                    saveAttachmentToGroup(groupOrganizations, attachment);
                }
            } else {
                for (Attachment attachment : attachments) {
                    if (attachment.getId() == null) {
                        saveAttachmentToGroup(groupOrganizations, attachment);
                    }
                }
                List<Long> newIds = attachments.stream().map(Attachment::getId).collect(Collectors.toList());
                if(attachmentsDB!=null && !attachmentsDB.isEmpty())
                for (Attachment attachment : attachmentsDB) {
                    if (!checkContainsAttachment(newIds, attachment.getId()))
                        removeAttachmentFromGroup(groupOrganizations, attachment);
                }
            }
        }
    }

    private boolean checkContainsAttachment(List<Long> listIDs, Long id){
        return listIDs.contains(id);
    }

    private void removeAttachmentFromGroup(GroupOrganizations groupOrganizations, Attachment attachment) {
        GroupAttachment groupAttachment = groupAttachmentDao.getByAttachmentAndGroup(groupOrganizations,attachment);
        if(groupAttachment!=null)
            groupAttachmentDao.delete(groupAttachment);
    }

    private List<Attachment> getAttachment(List<GroupAttachment> groupAttachments) {
        List<Attachment> attachments = new ArrayList<>();
        if(groupAttachments != null && !groupAttachments.isEmpty())
            for(GroupAttachment groupAttachment: groupAttachments){
                attachments.add(groupAttachment.getAttachment());
            }
        return attachments;
    }

    private void saveAttachmentToGroup(GroupOrganizations groupOrganizations, Attachment attachment) {
        Attachment attachmentDB = attachmentDao.getByUuid(attachment.getUuid());
        if (attachmentDB == null) {
            attachmentDao.saveOrUpdate(attachment);
        } else {
            attachment = attachmentDB;
        }
        GroupAttachment groupAttachment = new GroupAttachment();
        groupAttachment.setAttachment(attachment);
        groupAttachment.setGroupOrganizations(groupOrganizations);
        if (!groupAttachmentDao.isDuplicate(groupAttachment))
        groupAttachmentDao.saveOrUpdate(groupAttachment);
    }

    @Override
    public void deleteById(Long id) {
        GroupOrganizations byId = groupOrganizationsDao.findById(id);
        subGroupOrganizationsDao.deleteByGroup(byId);
        byId.setDeleted(true);
        groupOrganizationsDao.saveOrUpdate(byId);
    }

    @Override
    public GroupOrganizations getById(Long id) {
        return groupOrganizationsDao.findById(id);
    }

    @Override
    public List<GroupOrganizations> getGroupOrganizationGovernment() {
        List<SubdivisionApplication> subdivisionApplications = subdivisionApplicationDao.getAppByAppType(SubdivisionApplication.ApplicationType.SUBDIVISION);
        List<TypeOrganization> typeOrganizations = typeOrganizationDao.getByApps(subdivisionApplications);
        return groupOrganizationsDao.getAllByTypes(typeOrganizations);
    }
}
