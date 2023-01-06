package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Customer;


import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
