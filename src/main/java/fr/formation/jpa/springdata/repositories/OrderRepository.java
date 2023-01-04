package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Order;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends CrudRepository<Order, Long> {
	@Modifying
	@Query("UPDATE Order o SET o.dateDelivery = :dateDelivery WHERE o.reference = :reference")
	void updateOrderDeliveryDate(@Param("dateDelivery") Date dateDelivery, @Param("reference") String reference);

}
