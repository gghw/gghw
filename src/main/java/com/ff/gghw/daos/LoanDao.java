package com.ff.gghw.daos;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.ff.gghw.models.Loan;

@Repository
@Transactional
public class LoanDao extends BaseDao {
    public LoanDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public void insert(Loan loan) {
        persistObject(loan);
    }
    
    public void update(Loan loan) {
        updateObject(loan);
    }
    
    public Loan findById(int id) {
        Criteria criteria = getSession().createCriteria(Loan.class);
        criteria.add(Restrictions.eq("id", id));
        return (Loan) criteria.uniqueResult();
    }
    
    public List<Loan> findByClient(String client) {
        Criteria criteria = getSession().createCriteria(Loan.class);
        criteria.add(Restrictions.eq("client", client));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Loan>) criteria.list();
    }
}

