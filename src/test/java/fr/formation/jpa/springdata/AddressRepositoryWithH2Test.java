package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.AddressRepository;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import fr.formation.jpa.springdata.repositories.TypeAddressRepository;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AddressRepositoryWithH2Test {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TypeAddressRepository typeAddressRepository;

    @Autowired
    private CountryRepository countryReposotory;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldSave() {
        final String countryID = "PO";
        final Long typeAddressID = 5L;
        final Long addressID = 1L;
        
        countryReposotory.save(new Country(countryID, "popopo", "80.00", true));
        typeAddressRepository.save(new TypeAddress(typeAddressID, "type5"));
        

        //Method to test
        addressRepository.save(new Address(addressID, "une_rue", "un_code_postal", "une_ville",
                typeAddressRepository.findById(typeAddressID).get(), countryReposotory.findById(countryID).get()));

        //Test
        Optional<Address> addressFromDB = addressRepository.findById(addressID);
        Assertions.assertEquals(addressID, addressFromDB.get().getId());
        Assertions.assertEquals(countryID, addressFromDB.get().getCountry().getId());
        Assertions.assertEquals(typeAddressID, addressFromDB.get().getTypeAdress().getId());
        
        System.out.println("\u001B[32m __AddressRepositoryTest__ : " + addressFromDB.get() + "\u001B[0m");

        
    }
}
