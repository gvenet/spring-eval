package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, String> {
}
