package com.spring.dao;

import java.util.ArrayList;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.model.Employee;

@Component

public class EmployeeDao {
	
	@Autowired
	SessionFactory sessionFactory;
	@Transactional()
	public void addEmployee(Employee empBean) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
	    session.save(empBean);
	    tx.commit();
	    session.close();
	    System.out.println("employee saved successfully, Person Details="+empBean);
		}
	
	
	@Transactional()
	public void updateEmployee(Employee empBean) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
	    session.update(empBean);
	    tx.commit();
	    session.close();
	    System.out.println("Employee update successfully, Person Details="+empBean);
		}
	 @Transactional()
	 @SuppressWarnings("unchecked")
	public ArrayList<Employee> listemployee(){
		ArrayList<Employee> empList = new ArrayList<Employee>();
		 /*for(Employee empBean : empList){
	            logger.info("Person List::"+p);
	        }*/
		Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM Employee WHERE dflag = :dflag";
		Query query = session.createQuery(hql);
		query.setParameter("dflag",1);
		return (ArrayList<Employee>) query.list();
		/*return (ArrayList<Employee>) sessionFactory.openSession().createQuery("SELECT E.* FROM Employee E WHERE E.dflag =: 1 ")
			    .list();*/
		/*return (ArrayList<Employee>) sessionFactory.openSession().createQuery("select id, firstname , lastname , address from Employee where dflag = 1")
			    .list();*/
		}
	 @Transactional()
	public Employee getById(int id){
		return (Employee) sessionFactory.openSession().get(Employee.class, id);
		}
	 @Transactional()
	public int deleteEmp(int id) throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException{
		// System.out.println("before id :"+id +"dflag"+((Employee) sessionFactory.openSession().get(Employee.class, id)).getDflag());
	 	Session session= sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		/*session.createQuery("update Employee E set E.dfalg = 0 where E.id ="+id).executeUpdate();*/
		String hql = "update Employee set dflag=:dflag where id ="+ id ;
	             
		Query query = session.createQuery(hql);
		query.setParameter("dflag",0);
		int i = query.executeUpdate();
		tx.commit();
		//System.out.println("after id:"+id+" dflag = "+((Employee) sessionFactory.openSession().get(Employee.class, id)).getDflag());
		System.out.println("value is deleted"+i);
		  session.close();
		return i;
	}

}
