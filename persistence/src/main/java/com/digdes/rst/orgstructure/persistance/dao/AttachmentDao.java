package com.digdes.rst.orgstructure.persistance.dao;

import com.digdes.rst.orgstructure.persistance.model.Attachment;

import java.util.List;

public interface AttachmentDao extends AbstractDao<Attachment,Long> {

    List<Attachment> findAll();

    Attachment getByUuid(Long uuid);
}
