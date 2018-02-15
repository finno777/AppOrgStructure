package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attachment;
import com.digdes.rst.orgstructure.persistance.model.GroupAttachment;
import com.digdes.rst.orgstructure.persistance.model.GroupOrganizations;

import java.util.List;

public interface GroupAttachmentDao extends AbstractDao<GroupAttachment,Long> {
    List<GroupAttachment> getByGroup(GroupOrganizations groupOrganizations);

    GroupAttachment getByAttachmentAndGroup(GroupOrganizations groupOrganizations, Attachment attachment);

    boolean isDuplicate(GroupAttachment groupAttachment);
}
