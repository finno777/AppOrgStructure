package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.CuratorPersonDao;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import com.digdes.rst.orgstructure.persistance.utils.LinksUtils;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
@Log4j
public class CuratorPersonDaoImpl extends AbstractDaoImpl<CuratorPerson, Long> implements CuratorPersonDao{
    protected CuratorPersonDaoImpl(Class<CuratorPerson> entityClass) {
        super(entityClass);
    }
    public CuratorPersonDaoImpl(){super(CuratorPerson.class);}

    @Autowired
    PersonService personService;

    @Override
    public CuratorPerson findDuplicate(CuratorPerson curatorPerson) {
        Criteria criteria = getCurrentSession().createCriteria(CuratorPerson.class);
        criteria.add(Restrictions.eq("person", curatorPerson.getPerson()));
        criteria.add(Restrictions.eq("government", curatorPerson.getGovernment()));
        List<CuratorPerson> list = criteria.list();
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<CuratorPerson> findByIdPerson(Long l) {
        LinksUtils linksUtils = new LinksUtils();
        Person person = new Person();
        person.setId(l);
        Criteria criteria = getCurrentSession().createCriteria(CuratorPerson.class);
        criteria.add(Restrictions.eq("person", person));
        List<CuratorPerson> list = criteria.list();
        if(!list.isEmpty()) {
            for (CuratorPerson curatorPerson : list) {
                Person personPerson = curatorPerson.getPerson();
                personPerson.setOrgans(null);
                List<Attachment> attachPerson = personService.getAttachPerson(personPerson.getId());
                personPerson.setAttachments(attachPerson);
                Government government = curatorPerson.getGovernment();
                String pageUri = government.getApplication().getPageUri();
                String appId = government.getApplication().getAppId();
                Map<String, String[]> ps = new HashMap<>();
                ps.put("action",new String[]{"singleGovernmentView"});
                ps.put("id",new String[]{government.getId().toString()});
                government.setLink(linksUtils.createURI(appId, pageUri, ps));
            }
        }
        return list;
    }

    @Override
    public List<CuratorPerson> findByGovernment(Government government) {
        LinksUtils linksUtils = new LinksUtils();
        Criteria criteria = getCurrentSession().createCriteria(CuratorPerson.class);
        criteria.add(Restrictions.eq("government", government));
        List<CuratorPerson> list = criteria.list();
        if(!list.isEmpty()) {
            for (CuratorPerson curatorPerson : list) {
                Government personGovernment = curatorPerson.getGovernment();
                String pageUri = personGovernment.getApplication().getPageUri();
                String appId = personGovernment.getApplication().getAppId();
                Map<String, String[]> ps = new HashMap<>();
                ps.put("action",new String[]{"singledDivisionsView"});
                ps.put("id",new String[]{personGovernment.getId().toString()});
                personGovernment.setLink(linksUtils.createURI(appId, pageUri, ps));

                Person person = curatorPerson.getPerson();
                Application application = person.getApplication();
                String pagePersonUri = application.getPageUri();
                String appIdPerson = application.getAppId();
                Map<String, String[]> ps2 = new HashMap<>();
                ps2.put("action",new String[]{"renderPerson"});
                ps2.put("id",new String[]{person.getId().toString()});
                curatorPerson.setLink(linksUtils.createURI(appIdPerson, pagePersonUri, ps));
                List<Attachment> attachPerson = personService.getAttachPerson(person.getId());
                person.setAttachments(attachPerson);
            }
        }
        return list;
    }
}
