package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.Organization;
import com.digdes.rst.orgstructure.persistance.model.OrganizationAttachment;

import java.util.List;

public interface OrganizationAttachmentDao extends AbstractDao<OrganizationAttachment,Long> {
    List<OrganizationAttachment> getByOrganization(Organization organization);

    OrganizationAttachment getByAttachmentAndOrganization(Organization organization, Attachment attachment);
}
