package fr.formation.jpa.springdata;


import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CountryRepositoryWithH2Test {

    @Autowired
    private CountryRepository repository;


    @BeforeEach
    public void setup() {
        // initialisation de la base avant chaque test.

    }

    @Test
    public void shouldInsertOrderIntoDB() {
        final String ID = "DE";
        final String label = "Allemagne";

        // Given
        Country country = new Country();
        country.setActif(true);
        country.setLabel(label);
        country.setTva("15.00");
        country.setId(ID);

        // When
        repository.save(country);

        // Then
        final Optional<Country> countryFromDataBase = repository.findById(ID);
        Assertions.assertEquals(countryFromDataBase.get().getLabel(), label);
        Assertions.assertTrue(countryFromDataBase.get().isActif());

        System.out.println("\u001B[32m __CountryRepositoryTest__ : " + countryFromDataBase + "\u001B[0m");



    }

    @Test
    public void shouldDeletetOrderFromDB() {
        final String ID = "DE";
        final String label = "Allemagne";

        // Given
        Country country = new Country();
        country.setActif(true);
        country.setLabel(label);
        country.setTva("15.00");
        country.setId(ID);
        repository.save(country);

        // When
        repository.delete(country);

        // Then
        final boolean existsById = repository.existsById(ID);
        Assertions.assertFalse(existsById);

    }
}
