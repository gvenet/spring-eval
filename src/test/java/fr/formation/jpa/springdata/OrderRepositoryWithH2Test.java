package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.Customer;
import fr.formation.jpa.springdata.entities.Order;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import fr.formation.jpa.springdata.repositories.CustomerRepository;
import fr.formation.jpa.springdata.repositories.OrderRepository;

import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryWithH2Test {

    public static void printGreen(String input) {
        System.out.println("\u001B[32m" + input + "\u001B[0m");
    }

    public static void printDate(String label, Date date) {
        printGreen(label + " : " + new SimpleDateFormat("dd/MM/yyyy").format(date));
    }

    public static Date createDate(String date_string) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date_string);
    }

    public static Date createDateByIndex(int i) throws ParseException {
        return createDate("0" + i + "/01/2022");
    }

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void shouldUpdateOrderDeliveryDate() throws Exception {
        final String ref = "REF-123456";
        final Date orderDate = createDate("01/01/2022");
        final Date deliveryDate = createDate("01/02/2022");

        // Create and save order
        Order order = new Order(ref, customerRepository.findById(1L).get(), orderDate, deliveryDate,
                countryRepository.findById("FR").get());
        orderRepository.save(order);

        // Get orderFromDB
        Order orderFromDB = orderRepository.findByReference(ref);

        // Compare with deliverydate (volontairement, je ne met pas de condition sur
        // orderFromDB, cela permet de verifier si le find fonctionne)
        Assertions.assertEquals(deliveryDate, orderFromDB.getDateDelivery());
        printDate("deliveryDate", deliveryDate);
        printDate("orderFromDB.getDateDelivery()", orderFromDB.getDateDelivery());

        // Cache clear
        entityManager.clear();

        // Create newDeliveryDate
        final Date newDeliveryDate = createDate("01/03/2022");

        // Update DeliveryDate
        final int nbRowsUpdate = orderRepository.updateOrderDeliveryDate(newDeliveryDate, ref);

        // Get orderFromDB
        orderFromDB = orderRepository.findByReference(ref);

        // Compare with newDeliveryDate
        Assertions.assertEquals(newDeliveryDate, orderFromDB.getDateDelivery());
        Assertions.assertEquals(nbRowsUpdate, 1);
        printDate("neDeliveryDate", newDeliveryDate);
        printDate("orderFromDB.getDateDelivery()", orderFromDB.getDateDelivery());
    }

    @Test
    public void shouldFindByDateOrderAfterOrderByDateOrderAsc() throws Exception {
        // Create Orders
        final String ref = "REF-123456";
        List<Order> orders = new ArrayList<Order>();

        for (int i = 9; i > 0; i--) {
            orders.add(new Order(ref + i, customerRepository.findById(1L).get(), createDateByIndex(i), null,
                    countryRepository.findById("FR").get()));
        }

        // Save all orders
        orderRepository.saveAll(orders);

        // Get filtered ordersFromDB
        final Date indexDate = createDate("02/01/2022");
        List<Order> ordersFromDB = (List<Order>) orderRepository
                .findByDateOrderAfterOrderByDateOrderAsc(indexDate);

        // Orders list format for compare
        orders.removeIf(order -> order.getDateOrder().compareTo(indexDate) <= 0);
        Collections.reverse(orders);

        // Print filterd/sort ordersFromDB with ref,id,orderDate
        orders.forEach(order -> printDate("orders : ref = " + order.getReference() + " || id = " + order.getId(),
                order.getDateOrder()));

        // Print filterd/sort ordersFromDB with ref,id,orderDate
        ordersFromDB.forEach(orderFromDB -> printDate(
                "ordersFromDB : ref = " + orderFromDB.getReference() + " || id = " + orderFromDB.getId(),
                orderFromDB.getDateOrder()));

        // Compare the orders lists
        Assertions.assertEquals(orders, ordersFromDB);

    }

    @Test
    public void shouldFindCustomOrder() throws Exception {
        final String ref1 = "REF-123456";
        final String ref2 = "REF-123457";
        final Date orderDate = createDate("01/01/2022");
        final Date deliveryDate = createDate("01/02/2022");
        final Country france = countryRepository.findById("FR").get();
        final Country italie = countryRepository.findById("IT").get();
        
        // Create customers
        final Customer martin = customerRepository.findById(1L).get();
        final Customer michel = new Customer(2L, "Toto", "Michel", italie);
        customerRepository.saveAll(Arrays.asList(martin,michel));

        // Create and save orders
        final Order order1 = new Order(ref1, martin, orderDate, deliveryDate,france);
        final Order order2 = new Order(ref2, michel, orderDate, deliveryDate, italie);
        orderRepository.saveAll(Arrays.asList(order1, order2));
        
        List<Order> ordersFromDB = orderRepository.findCustomOrder("Martin", "Pierre", france, 2022);

        List<String> toPrint = new ArrayList<String>();
        toPrint.add(order1.getCustomer().getLastName());
        toPrint.add(order1.getCustomer().getFirstName());
        toPrint.add(order1.getCountry().getId());

        printDate(toPrint.toString(), order1.getDateDelivery());

        Assertions.assertEquals(ordersFromDB, Arrays.asList(order1));
        
         
    }
}
