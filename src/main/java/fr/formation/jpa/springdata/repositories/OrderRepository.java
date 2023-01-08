package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.Order;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
	@Modifying
	@Query("UPDATE Order o SET o.dateDelivery = ?1 WHERE o.reference = ?2")
	int updateOrderDeliveryDate(Date dateDelivery, String reference);

	Order findByReference(String reference);

	@Query("SELECT o FROM Order o WHERE o.dateOrder > ?1 ORDER BY o.dateOrder ASC")
	List<Order> findCustomByDateOrder(Date date);

	@Query("SELECT o FROM Order o WHERE o.customer.lastName = ?1 AND o.customer.firstName = ?2 AND o.country = ?3 AND YEAR(o.dateOrder) = ?4")
	List<Order> findCustomOrder(String customerLastName, String customerFirstName, Country country, Integer year);

	@Query("SELECT a FROM Order o JOIN o.addresses a WHERE o.reference = ?1 AND a.typeAddress.id = 1")
	List<Object[]> findCustomOrderByReference(String ref);
}
