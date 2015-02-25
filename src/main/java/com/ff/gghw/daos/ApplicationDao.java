package com.ff.gghw.daos;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.joda.time.LocalDateTime;
import com.google.common.primitives.Ints;

import com.ff.gghw.models.Application;

@Repository
@Transactional
public class ApplicationDao extends BaseDao {
    public ApplicationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public void insert(Application application) {
        persistObject(application);
    }
    
    public Application findById(int id) {
        Criteria criteria = getSession().createCriteria(Application.class);
        criteria.add(Restrictions.eq("id", id));
        return (Application) criteria.uniqueResult();
    }
    
    public int countSinceWithIp(LocalDateTime since, String ip) {
        Criteria criteria = getSession().createCriteria(Application.class);
        criteria.add(Restrictions.ge("timestamp", since));
        criteria.add(Restrictions.eq("ip", ip));
        criteria.setProjection(Projections.rowCount());
        return Ints.checkedCast((long)criteria.uniqueResult());
    }
}

