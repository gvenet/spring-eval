package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.AddressRepository;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import fr.formation.jpa.springdata.repositories.TypeAddressRepository;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AddressRepositoryWithH2Test {

    public static void print(String input) {
        System.out.println("\u001B[32m" + input + "\u001B[0m");
    }

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TypeAddressRepository typeAddressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldSave() {
        final String countryID = "PO";
        final Long typeAddressID = 5L;
        final Long addressID = 1L;

        countryRepository.save(new Country(countryID, "popopo", "80.00", true));
        typeAddressRepository.save(new TypeAddress(typeAddressID, "type5"));

        // Method to test
        addressRepository.save(new Address(addressID, "une_rue", "un_code_postal", "une_ville",
                typeAddressRepository.findById(typeAddressID).get(), countryRepository.findById(countryID).get()));

        // Test
        Optional<Address> addressFromDB = addressRepository.findById(addressID);
        Assertions.assertEquals(addressID, addressFromDB.get().getId());
        Assertions.assertEquals(countryID, addressFromDB.get().getCountry().getId());
        Assertions.assertEquals(typeAddressID, addressFromDB.get().getTypeAddress().getId());

        System.out.println("\u001B[32m __AddressRepositoryTest__ : " + addressFromDB.get() + "\u001B[0m");

    }

    @Test
    public void shouldFindByTypeAddress() {
        final Country country = countryRepository.findById("FR").get();
        final TypeAddress type1 = typeAddressRepository.findById(1L).get();
        final TypeAddress type2 = typeAddressRepository.findById(2L).get();

        addressRepository.save(new Address(1L, "une_rue", "un_code_postal", "une_ville",
                type1, country));
        addressRepository.save(new Address(2L, "une_rue", "un_code_postal", "une_ville",
                type2, country));

        // List<Address> addresses = (List<Address>) addressRepository.findAll();
        List<Address> addresses = addressRepository.findByTypeAddress(type1);

        addresses.forEach(address -> print(address.toString()));

    }
}
