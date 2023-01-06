package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Address;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
