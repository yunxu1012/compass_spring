package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerSearch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class CustomerSearchRepositoryImpl {
	@PersistenceContext
    private EntityManager em;
	Logger logger = LoggerFactory.getLogger(CustomerSearchRepositoryImpl.class);
	

	 @Transactional
	public List<Customer> searchCustomer(CustomerSearch search){
		 logger.info("customer search here003!!!!\n\n");
		String sql = 
				  "SELECT c.customer_id as customerId, c.first_name as firstName, "
				  + " c.last_name as lastName, c.email as email, "
				  + " c.phone_number as phoneNumber, c.password as password "
				  +"FROM Customer c, Customer_Preference cp WHERE "
						  + " cp.min_price <=:price  and cp.max_price>=:price "
						  + " and cp.min_square_feet <=:squarefeet  and cp.max_square_feet >=:squarefeet "
						  + " and cp.min_bed <= CAST(:bedCount AS bedcount) "
						  + " and cp.max_bed >= CAST(:bedCount AS bedcount) "
						  + " and cp.min_bath <=  CAST(:bathCount AS bathcount)"
						  + " and cp.customer_id = c.customer_id";
				Query query = em.createNativeQuery(sql);
						List<Object[]> objects = query
						  .setParameter("price", search.getPrice())
						  .setParameter("squarefeet", search.getSquareFeet())
						  .setParameter("bedCount", search.getBedCount().getCount())
						  .setParameter("bathCount", search.getBathCount().getCount())
						  .getResultList();
			 List<Customer> customers = new ArrayList<>();
			 for(Object[] object:objects) {
				 Customer customer = new Customer();
				 customer.setCustomerId((Integer)object[0]);
				 customer.setFirstName((String)object[1]);
				 customer.setLastName((String)object[2]);
				 customer.setEmail((String)object[3]);
				 customer.setPhoneNumber((String)object[4]);
				 customers.add(customer);
			 }
			 
		return customers;
	}
	 
	
}
