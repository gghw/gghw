package com.ff.gghw.daos;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.ff.gghw.models.Extension;

@Repository
@Transactional
public class ExtensionDao extends BaseDao {
    public ExtensionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public void insert(Extension extension) {
        persistObject(extension);
    }
    
    public Extension findById(int id) {
        Criteria criteria = getSession().createCriteria(Extension.class);
        criteria.add(Restrictions.eq("id", id));
        return (Extension) criteria.uniqueResult();
    }
}

