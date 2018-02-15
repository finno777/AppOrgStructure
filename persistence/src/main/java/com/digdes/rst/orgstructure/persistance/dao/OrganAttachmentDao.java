package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.OrganAttachment;

import java.util.List;

public interface OrganAttachmentDao extends AbstractDao<OrganAttachment,Long> {
    List<OrganAttachment> getByOrgan(Organ organ);

    OrganAttachment getByAttachmentAndOrgan(Organ organ, Attachment attachment);

    List<OrganAttachment> getAllOrganAttach();

    boolean isDuplicate(OrganAttachment organAttachment);
}
