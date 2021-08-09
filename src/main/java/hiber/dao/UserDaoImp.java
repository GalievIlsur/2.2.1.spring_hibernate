package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser(String model, int series) {
//    Запрос был таким:
//    Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.car in (from Car c where c.model = :model and c.series = :series)");
//
//    Стал таким:
      Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.car.model = :model and u.car.series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      return (User)query.getSingleResult();
   }
}
