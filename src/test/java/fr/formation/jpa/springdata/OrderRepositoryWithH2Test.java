package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.Customer;
import fr.formation.jpa.springdata.entities.Order;
import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.AddressRepository;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import fr.formation.jpa.springdata.repositories.CustomerRepository;
import fr.formation.jpa.springdata.repositories.OrderRepository;
import fr.formation.jpa.springdata.repositories.TypeAddressRepository;
import org.junit.jupiter.api.Assertions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryWithH2Test {

    @Autowired
    private CountryRepository countryReposotory;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldUpdateOrderDeliveryDate() throws Exception{
        final String ref = "REF-123456";
        final Customer customer = customerRepository.findById(1L).get();
        final Country country = countryReposotory.findById("FR").get();
        final Date orderDate = new SimpleDateFormat("yyyyMMdd").parse("20220101");
        final Date deliveryDate = new SimpleDateFormat("yyyyMMdd").parse("20220102");
        Order order = new Order(ref, customer, orderDate, deliveryDate, country);
        orderRepository.save(order);
        
        Optional<Order> orderFromDB = orderRepository.findById(1L);
        Assertions.assertEquals(deliveryDate, orderFromDB.get().getDateDelivery());
        System.out.println("\u001B[32m __OrderDeliveryDate__ : " + orderFromDB.get().getDateDelivery() + "\u001B[0m");
        
        final Date newDeliveryDate = new SimpleDateFormat("yyyyMMdd").parse("20220103");
        // orderRepository.updateOrderDeliveryDate(newDeliveryDate, ref);
        orderFromDB.get().setDateDelivery(newDeliveryDate);
        orderRepository.save(order);
        
        Assertions.assertEquals(newDeliveryDate, orderFromDB.get().getDateDelivery());
        System.out.println("\u001B[32m __OrderDeliveryDate__ : " + orderFromDB.get().getDateDelivery() + "\u001B[0m");
        
    }
}
