package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.TypeAddress;
import org.springframework.data.repository.CrudRepository;

public interface TypeAddressRepository extends CrudRepository<TypeAddress, Long> {
}
