package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();

   }

   @Override
   public List<User> getUserByCarId(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE usercar.model = :setModel AND usercar.series = :setSeries", User.class);
      query.setParameter("setSeries", series);
      query.setParameter("setModel", model);
      return query.getResultList();
   }


}


