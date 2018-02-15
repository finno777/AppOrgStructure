package com.digdes.rst.orgstructure.persistance.dao.impl;

import com.digdes.rst.orgstructure.persistance.dao.DivisionPersonDao;
import com.digdes.rst.orgstructure.persistance.model.Division;
import com.digdes.rst.orgstructure.persistance.model.DivisionPerson;
import lombok.extern.log4j.Log4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Log4j
public class DivisionPersonDaoImpl extends AbstractDaoImpl<DivisionPerson, Long> implements DivisionPersonDao {
    protected DivisionPersonDaoImpl(Class<DivisionPerson> entityClass) {
        super(entityClass);
    }
    public DivisionPersonDaoImpl(){super(DivisionPerson.class);}

    @Override
    public DivisionPerson findDuplicate(DivisionPerson divisionPerson) {
        Criteria criteria = getCurrentSession().createCriteria(DivisionPerson.class);
        criteria.add(Restrictions.eq("person", divisionPerson.getPerson()));
        criteria.add(Restrictions.eq("division", divisionPerson.getDivision()));
        List<DivisionPerson> list = criteria.list();
        if(list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<DivisionPerson> getByDivision(Division division) {
        Criteria criteria = getCurrentSession().createCriteria(DivisionPerson.class);
        criteria.add(Restrictions.eq("division", division));
        return criteria.list();
    }
}