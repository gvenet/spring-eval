package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.TypeAddress;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
	@Query("SELECT a FROM Address a WHERE a.typeAddress = ?1")
	public List<Address> findByTypeAddress(TypeAddress type);
}
