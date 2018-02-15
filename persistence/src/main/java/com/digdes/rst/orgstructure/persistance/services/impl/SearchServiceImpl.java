package com.digdes.rst.orgstructure.persistance.services.impl;

import com.digdes.rst.commons.portal.PortalUtils;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.dto.search.OrganSearchBean;
import com.digdes.rst.orgstructure.persistance.dto.search.PersonSearchBean;
import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.services.SearchService;
import com.digdes.rst.searchService.client.SearchServiceClient;
import com.digdes.rst.searchService.dto.ActionSearchServiceResponse;
import com.digdes.rst.searchService.dto.EntityTransfer;
import com.digdes.rst.searchService.dto.IndexActionType;
import lombok.extern.log4j.Log4j;
import org.exoplatform.portal.webui.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Samoylov Ilya
 *         Date: 25.01.18.
 *         Copyright http://digdes.com
 */
@Log4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchServiceClient searchServiceClient;

    private static final String PERSON_ENTITY_NAME = Person.class.getSimpleName();
    private static final String ORGAN_ENTITY_NAME = Organ.class.getSimpleName();

    @Override
    public String index(Person person, String appId, String userId) {
        EntityTransfer<PersonSearchBean> entityTransfer = new EntityTransfer<>();
        entityTransfer.setAppName(AppConfig.APP_NAME);
        entityTransfer.setLocale(AppConfig.DEFAULT_LOCALE.getLanguage());
        entityTransfer.setEntity(toPersonSearchBean(person));
        entityTransfer.setEntityId(String.valueOf(person.getId()));
        entityTransfer.setPortalName(PortalUtils.getPortalName(appId));
        entityTransfer.setDisplayName(person.getName());
        entityTransfer.setIndexActionType(IndexActionType.SAVE);
        entityTransfer.setDateCreated(person.getCreationDate());
        entityTransfer.setDateUpdated(new Date());
        entityTransfer.setEntityName(PERSON_ENTITY_NAME);
        entityTransfer.setUserName(userId);
        entityTransfer.setAppId(appId);
        entityTransfer.setUri(AppConfig.getConcretePersonRelativeUrl(person.getId(), appId));
        log.debug(AppConfig.getConcretePersonRelativeUrl(person.getId(), appId));
        try {
            ActionSearchServiceResponse actionSearchServiceResponse = searchServiceClient.indexEntity(entityTransfer);
            if (actionSearchServiceResponse.getSuccess() && actionSearchServiceResponse.getItem() != null) {
                return actionSearchServiceResponse.getItem().toString();
            } else {
                log.debug("Entity indexing result: " + actionSearchServiceResponse);
                return null;
            }
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public String index(Organ organ, String appId, String userId) {
        EntityTransfer<OrganSearchBean> entityTransfer = new EntityTransfer<>();
        entityTransfer.setAppName(AppConfig.APP_NAME);
        entityTransfer.setLocale(AppConfig.DEFAULT_LOCALE.getLanguage());
        entityTransfer.setEntity(toOrganSearchBean(organ));
        entityTransfer.setEntityId(String.valueOf(organ.getId()));
        entityTransfer.setPortalName(PortalUtils.getPortalName(appId));
        entityTransfer.setDisplayName(organ.getName());
        entityTransfer.setIndexActionType(IndexActionType.SAVE);
        entityTransfer.setDateCreated(organ.getCreationDate());
        entityTransfer.setDateUpdated(new Date());
        entityTransfer.setEntityName(ORGAN_ENTITY_NAME);
        entityTransfer.setUserName(userId);
        entityTransfer.setAppId(appId);
        entityTransfer.setUri(AppConfig.getConcreteOrganRelativeUrl(organ.getId(), appId));
        log.debug(AppConfig.getConcreteOrganRelativeUrl(organ.getId(), appId));
        try {
            ActionSearchServiceResponse actionSearchServiceResponse = searchServiceClient.indexEntity(entityTransfer);
            if (actionSearchServiceResponse.getSuccess() && actionSearchServiceResponse.getItem() != null) {
                return actionSearchServiceResponse.getItem().toString();
            } else {
                log.debug("Entity indexing result: " + actionSearchServiceResponse);
                return null;
            }
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public boolean unindexOrgan(String appId, String userID, Long id) {
        EntityTransfer<OrganSearchBean> et = new EntityTransfer<>();
        et.setAppId(appId);
        et.setUserName(userID);
        et.setAppName(AppConfig.APP_NAME);
        et.setEntityId(Long.toString(id));
        et.setPortalName(Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        et.setEntityName(ORGAN_ENTITY_NAME);
        et.setIndexActionType(IndexActionType.REMOVE);
        et.setLocale(AppConfig.DEFAULT_LOCALE.getLanguage());
        et.setEntity(new OrganSearchBean());
        try {
            ActionSearchServiceResponse actionSearchServiceResponse = searchServiceClient.indexEntity(et);
            return actionSearchServiceResponse.getSuccess();
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }


    @Override
    public boolean unindexPerson(String appId, String userID, Long id) {
        EntityTransfer<PersonSearchBean> et = new EntityTransfer<>();
        et.setAppId(appId);
        et.setUserName(userID);
        et.setAppName(AppConfig.APP_NAME);
        et.setEntityId(Long.toString(id));
        et.setPortalName(Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        et.setEntityName(PERSON_ENTITY_NAME);
        et.setIndexActionType(IndexActionType.REMOVE);
        et.setLocale(AppConfig.DEFAULT_LOCALE.getLanguage());
        et.setEntity(new PersonSearchBean());
        try {
            ActionSearchServiceResponse actionSearchServiceResponse = searchServiceClient.indexEntity(et);
            return actionSearchServiceResponse.getSuccess();
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            return false;
        }
    }


    private PersonSearchBean toPersonSearchBean(Person person) {
        PersonSearchBean bean = new PersonSearchBean();
        bean.setPhone(person.getPhone());
        bean.setEmail(person.getEmail());
        bean.setPosition(person.getPosition());
        bean.setName(person.getName());
        if (person.getAttributes() != null) {
            bean.setAttributes(person.getAttributes().stream().collect(Collectors.toMap(Attribute::getKey, Attribute::getValue)));
        }
        if (person.getCuratorGovernments() != null) {
            List<String> curatorOf = new ArrayList<>();
            person.getCuratorGovernments().forEach(gov -> {
                curatorOf.add(gov.getGovernment().getTitle());
            });
        }
        return bean;
    }

    private OrganSearchBean toOrganSearchBean(Organ organ) {
        OrganSearchBean bean = new OrganSearchBean();
        bean.setConditions(organ.getConditions());
        bean.setDecision(organ.getDecision());
        bean.setDescription(organ.getDecision());
        bean.setDetails(organ.getDetails());
        bean.setMeeting(organ.getMeeting());
        bean.setDocuments(organ.getDocuments());
        bean.setName(organ.getName());
        bean.setPlan(organ.getPlan());
        bean.setSchedule(organ.getSchedule());
        bean.setSituation(organ.getSituation());
        if (organ.getPeople() != null) {
            List<PersonSearchBean> pb = new ArrayList<>();
            organ.getPeople().forEach(item -> {
                pb.add(toPersonSearchBean(item));
            });
            bean.setPeople(pb);
        }
        return bean;
    }
}
