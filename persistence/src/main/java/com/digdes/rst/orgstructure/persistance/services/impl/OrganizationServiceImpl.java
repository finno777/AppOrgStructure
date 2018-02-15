package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.orgstructure.persistance.dao.*;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.OrganizationPersonService;
import com.digdes.rst.orgstructure.persistance.services.OrganizationService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
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
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    OrganizationAttachmentDao organizationAttachmentDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    TypeOrganizationDao typeOrganizationDao;

    @Autowired
    SubdivisionApplicationDao subdivisionApplicationDao;

    @Autowired
    OrganizationPersonDao organizationPersonDao;

    @Autowired
    OrganizationPersonService organizationPersonService;

    @Autowired
    PersonService personService;

    @Autowired
    OrganizationPhoneDao organizationPhoneDao;

    @Override
    public Organization getById(Long idOrganization) {
        Organization organization = organizationDao.findById(idOrganization);
        List<OrganizationPerson> organizationPeople = organizationPersonDao.findByOrganization(organization);
        organization.setPeople(organizationPeople);
        for(OrganizationPerson organizationPerson: organizationPeople){
            List<Attachment> attachPerson = personService.getAttachPerson(organizationPerson.getPerson().getId());
            organizationPerson.getPerson().setAttachments(attachPerson);
        }
        return organization;
    }

    @Override
    public List<Organization> getSimilarOrganizationByID(Long idOrganization) {
        Organization organization = organizationDao.findById(idOrganization);
        SubGroupOrganizations subGroupOrganizations = organization.getSubGroupOrganizations();

        return organizationDao.getBySubGroup(subGroupOrganizations);
    }

    @Override
    public void save(Organization organization) {
        Boolean newOrganization = organization.getId()==null;
        if(organization.getTypeOrganization()!=null){
            organization.setSubGroupOrganizations(null);
        }
        if(organization.getDeleted()==null){
            organization.setDeleted(false);
        }
        List<OrganizationPhone> phones = organization.getPhones();
        if(phones !=null && !phones.isEmpty())
        {
            for(OrganizationPhone organizationPhone: phones){
                organizationPhone.setOrganization(organization);
            }
        }
        organizationDao.saveOrUpdate(organization);
        List<Long> newIdsPhone = phones.stream().map(OrganizationPhone::getId).collect(Collectors.toList());
        List<OrganizationPhone> phonesDB = organizationPhoneDao.getByOrganization(organization);
        if(phonesDB!=null && !phonesDB.isEmpty()) {
            for (OrganizationPhone phone : phonesDB) {
                if (!newIdsPhone.contains(phone.getId())) {
                    log.debug("id for del " + phone.getId());
                    organizationPhoneDao.delete(phone);
                }
            }
        }


        List<Attachment> attachments = organization.getAttachments();
        List<OrganizationAttachment> organizationAttachments = organizationAttachmentDao.getByOrganization(organization);
        List<Attachment> attachmentsDB = getAttachment(organizationAttachments);
        if (attachments != null) {
            if(newOrganization || !attachmentsDB.isEmpty()){
                for(Attachment attachment: attachments){
                    saveAttachmentToOrganization(organization, attachment);
                }
            }else{
                for(Attachment attachment : attachments){
                    if(attachment.getId()==null){
                        saveAttachmentToOrganization(organization, attachment);
                    }
                }
                List<Long> newIds = attachments.stream().map(Attachment::getId).collect(Collectors.toList());
                for(Attachment attachment : attachmentsDB){
                    if(!checkContainsAttachment(newIds,attachment.getId()))
                        removeAttachmentFromOrganization(organization, attachment);
                }
            }
        }

    }

    private boolean checkContainsAttachment(List<Long> listIDs, Long id){
        return listIDs.contains(id);
    }

    private void removeAttachmentFromOrganization(Organization organization, Attachment attachment) {
        OrganizationAttachment organizationAttachment = organizationAttachmentDao.getByAttachmentAndOrganization(organization,attachment);
        if(organizationAttachment!=null)
        organizationAttachmentDao.delete(organizationAttachment);
    }

    private List<Attachment> getAttachment(List<OrganizationAttachment> organizationAttachments) {
        List<Attachment> attachments = new ArrayList<>();
        if(organizationAttachments != null && !organizationAttachments.isEmpty())
        for(OrganizationAttachment organizationAttachment: organizationAttachments){
            attachments.add(organizationAttachment.getAttachment());
        }
        return attachments;
    }

    private void saveAttachmentToOrganization(Organization organization, Attachment attachment) {
        attachmentDao.saveOrUpdate(attachment);
        OrganizationAttachment organizationAttachment = new OrganizationAttachment();
        organizationAttachment.setAttachment(attachment);
        organizationAttachment.setOrganization(organization);
        organizationAttachmentDao.saveOrUpdate(organizationAttachment);
    }

    @Override
    public void deleteById(Long id) {
        Organization byId = organizationDao.findById(id);
        byId.setDeleted(true);
        organizationDao.saveOrUpdate(byId);
    }

    @Override
    public List<Organization> getOrganizationGovernment() {
        List<SubdivisionApplication> subdivisionApplications = subdivisionApplicationDao.getAppByAppType(SubdivisionApplication.ApplicationType.SUBDIVISION);
        List<TypeOrganization> typeOrganizations = typeOrganizationDao.getByApps(subdivisionApplications);
        return organizationDao.getAllByTypes(typeOrganizations);
    }

    @Override
    public OrganizationPerson removePerson(Long idOrganizationPerson) {
        OrganizationPerson byId = organizationPersonDao.findById(idOrganizationPerson);
        organizationPersonDao.delete(byId);
        return byId;
    }

    @Override
    public void savePerson(OrganizationPerson organizationPerson) {
        organizationPersonService.savePerson(organizationPerson);
    }
}
