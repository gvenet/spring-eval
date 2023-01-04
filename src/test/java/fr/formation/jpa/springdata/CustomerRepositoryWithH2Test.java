package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.Customer;
import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.AddressRepository;
import fr.formation.jpa.springdata.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryWithH2Test {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        
    }
    
    @Test
    public void shouldSave() {
        final Long customerID = 1L;
        //Create country
        Country country = new Country("FR", "France", "20.00", true);
        
        //Create address type
        TypeAddress typeAddress = new TypeAddress(1L, "type d'adresse 1");
        
        //Create three addresses
        Address address1 = new Address(1L, "rue1", "zip1", "ville1", typeAddress, country);
        Address address2 = new Address(2L, "rue2", "zip2", "ville2", typeAddress, country);
        Address address3 = new Address(3L, "rue3", "zip3", "ville3", typeAddress, country);        
        addressRepository.saveAll(Arrays.asList(address1, address2, address3));

        //Create customer
        Customer customer = new Customer(customerID, "Do", "Jhon", country);        
        customerRepository.save(customer);
        customer.getAddresses().addAll(Arrays.asList(address1, address2, address3));
        customerRepository.save(customer);

        //Get customer from db
        Optional<Customer> customerFromDB = customerRepository.findById(customerID);

        //Compare local objet and object from db
        Assertions.assertEquals(customerID, customerFromDB.get().getId());

        //Print
        System.out.println(
                "\u001B[32m __CustomerRepositoryTest__ : " + customerFromDB.get().getFirstName()+ " " + customerFromDB.get().getLastName() + "\u001B[0m");
        customerFromDB.get().getAddresses().forEach(address -> {
            System.out.println("\u001B[32m __address_from_customer__  : " + address + "\u001B[0m");
        });
    }
}