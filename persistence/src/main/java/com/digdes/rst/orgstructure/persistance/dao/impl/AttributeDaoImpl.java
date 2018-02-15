package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.AttributeDao;
import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class AttributeDaoImpl extends AbstractDaoImpl<Attribute, Long> implements AttributeDao {
    protected AttributeDaoImpl(Class<Attribute> entityClass) {
        super(entityClass);
    }
    public AttributeDaoImpl(){super(Attribute.class);}

    @SuppressWarnings("unchecked")
    @Override
    public Attribute findAttributeById(Long id) {
        return (Attribute) getCurrentSession().get(Attribute.class,id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attribute> findAttributeByPerson(Person person) {
        Criteria criteria=getCurrentSession().createCriteria(Attribute.class);
        criteria.add(Restrictions.eq("person",person));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attribute> findAttributeByPersonAndKey (Person person, String key){
        Criteria criteria=getCurrentSession().createCriteria(Attribute.class);
        criteria.add(Restrictions.eq("person",person));
        criteria.add(Restrictions.eq("key", key));
        return criteria.list();
    }

    @Override
    public void save(Attribute attribute) {
        saveOrUpdate(attribute);
    }

    @Override
    public void deleteAttribute(Long id) {
        Attribute attribute=findAttributeById(id);
        if(attribute!=null){
            getCurrentSession().delete(attribute);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Attribute> findAllAttribute() {
        return getCurrentSession().createCriteria(Attribute.class).list();
    }

    @Override
    public List<Attribute> findPeopleByAdviser(String id, String person) {
        Criteria criteria=getCurrentSession().createCriteria(Attribute.class);
        criteria.add(Restrictions.eq("key", person));
        criteria.add(Restrictions.eq("value", id));
        return criteria.list();
    }

}
