package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.CustomerPreference;
import com.example.demo.entity.CustomerSearch;
import com.example.demo.entity.Hometype;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
public class CustomerSearchRepositoryImpl {
	@PersistenceContext
	private EntityManager em;
	Logger logger = LoggerFactory.getLogger(CustomerSearchRepositoryImpl.class);

	public List<Customer> searchCustomer(CustomerSearch search) {
		try {
			StringBuilder bu = new StringBuilder();
			String sql = "SELECT c.customer_id as customerId, c.first_name as firstName,"
					+ " c.last_name as lastName, c.email as email, "
					+ " c.phone_number as phoneNumber "
					+ " FROM Customer c, Customer_Preference cp "
					+ "WHERE cp.customer_id = c.customer_id ";
			bu.append(sql);

			if (search.getPrice() != null) {
				bu.append(" and cp.min_price <=:price  and cp.max_price>=:price ");
			}
			if (search.getSquareFeet() != null) {
				bu.append(" and cp.min_square_feet <=:squarefeet " 
			          + " and cp.max_square_feet >=:squarefeet ");
			}
			if (search.getBedCount() != null) {
				bu.append(" and cp.min_bed <= CAST(:bedCount AS bedcount) "
						+ " and cp.max_bed >= CAST(:bedCount AS bedcount) ");
			}
			if (search.getBathCount() != null) {
				bu.append(" and cp.min_bath <=  CAST(:bathCount AS bathcount) ");
			}
			if (search.getHomeType() != null) {
				bu.append(" and c.customer_id in (select customer_id from "
			            + " customer_hometype  where hometype = "
						+ " CAST(:hometype AS hometype))");
			}
			if (search.getCityId() != null) {
				bu.append(" and c.customer_id in (select customer_id from " 
				        + " customer_city where city_id = :cityId) ");
			}
			bu.append("  order by lower(c.first_name); ");
			Query query = em.createNativeQuery(bu.toString());
			if (search.getPrice() != null) {
				query.setParameter("price", Integer.parseInt(search.getPrice()));
			}
			if (search.getSquareFeet() != null) {
				query.setParameter("squarefeet", Integer.parseInt(search.getSquareFeet()));
			}
			if (search.getBedCount() != null) {
				query.setParameter("bedCount", search.getBedCount().getCount());
			}
			if (search.getBathCount() != null) {
				query.setParameter("bathCount", search.getBathCount().getCount());
			}
			if (search.getHomeType() != null) {
				query.setParameter("hometype", Hometype.fromType(search.getHomeType()).name());
			}
			if (search.getCityId() != null) {
				query.setParameter("cityId", search.getCityId());
			}

			List<Object[]> objects = query.getResultList();
			List<Customer> customers = new ArrayList<>();
			for (Object[] object : objects) {
				Customer customer = new Customer();
				customer.setCustomerId((Integer) object[0]);
				customer.setFirstName((String) object[1]);
				customer.setLastName((String) object[2]);
				customer.setEmail((String) object[3]);
				customer.setPhoneNumber((String) object[4]);
				customers.add(customer);
			}
			return customers;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

}
