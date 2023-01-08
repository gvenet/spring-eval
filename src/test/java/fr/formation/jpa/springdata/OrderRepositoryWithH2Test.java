package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Address;
import fr.formation.jpa.springdata.entities.Articles;
import fr.formation.jpa.springdata.entities.Country;
import fr.formation.jpa.springdata.entities.Customer;
import fr.formation.jpa.springdata.entities.Order;
import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.AddressRepository;
import fr.formation.jpa.springdata.repositories.ArticlesRepository;
import fr.formation.jpa.springdata.repositories.CountryRepository;
import fr.formation.jpa.springdata.repositories.CustomerRepository;
import fr.formation.jpa.springdata.repositories.OrderRepository;
import fr.formation.jpa.springdata.repositories.TypeAddressRepository;

import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        public static void print(String input) {
                System.out.println("\u001B[32m" + input + "\u001B[0m");
        }

        public static void printDate(String label, Date date) {
                print(label + " : " + new SimpleDateFormat("dd/MM/yyyy").format(date));
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

        @Autowired
        private ArticlesRepository articlesRepository;

        @Autowired
        private AddressRepository addressRepository;

        @Autowired
        private TypeAddressRepository typeAddressRepository;

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
                                .findCustomByDateOrder(indexDate);

                // Orders list format for compare
                orders.removeIf(order -> order.getDateOrder().compareTo(indexDate) <= 0);
                Collections.reverse(orders);

                // Print filterd/sort ordersFromDB with ref,id,orderDate
                orders.forEach(order -> printDate(
                                "orders : ref = " + order.getReference() + " || id = " + order.getId(),
                                order.getDateOrder()));

                // Print filterd/sort ordersFromDB with ref,id,orderDate
                ordersFromDB.forEach(orderFromDB -> printDate(
                                "ordersFromDB : ref = " + orderFromDB.getReference() + " || id = "
                                                + orderFromDB.getId(),
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
                customerRepository.saveAll(Arrays.asList(martin, michel));

                // Create and save orders
                final Order order1 = new Order(ref1, martin, orderDate, deliveryDate, france);
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

        @Test
        public void shouldFindCustomByOrderReference() throws Exception {
                // Save Articles
                final Articles sugar = new Articles(1L, "Sucre",
                                "Le sucre est une substance de saveur douce extraite principalement de la canne à sucre ou de la betterave sucrière.",
                                1.39);
                final Articles flour = new Articles(2L,
                                "Farine",
                                "La farine est une poudre obtenue en broyant et en moulant des céréales ou d'autres produits agricoles alimentaires solides, souvent des graines.",
                                1.00);
                final Articles confectionery = new Articles(3L,
                                "Confiserie",
                                "Une confiserie est un produit à base de sucre qui est vendu dans un magasin du même nom et fabriqué par un confiseur.",
                                4.76);
                articlesRepository.saveAll(Arrays.asList(sugar, flour, confectionery));

                // Create Country
                final Country country = countryRepository.findById("FR").get();

                // Create TypeAddress
                final TypeAddress type1 = typeAddressRepository.findById(1L).get();
                final TypeAddress type2 = typeAddressRepository.findById(2L).get();
                final TypeAddress type3 = typeAddressRepository.findById(3L).get();

                // Create Address
                final Address deliveryAddress = new Address(1L, "rue de la livraison", "69000", "Lyon",
                                type1, country);
                final Address billingAddress = new Address(2L, "rue de la facturation", "69002", "Lyon",
                                type2, country);
                final Address otherAddress = new Address(3L, "rue de l'autre type", "69007", "Lyon",
                                type3, country);
                addressRepository.saveAll(Arrays.asList(deliveryAddress, billingAddress, otherAddress));

                // Create orders
                final Date orderDate = createDate("01/01/2022");
                final Date deliveryDate = createDate("01/02/2022");
                final String ref = "REF-123456";
                Order order1 = new Order(ref, customerRepository.findById(1L).get(), orderDate, deliveryDate,
                                countryRepository.findById("FR").get());
                order1.getAddresses().addAll(Arrays.asList(deliveryAddress, billingAddress, otherAddress));
                order1.getArticles().addAll(Arrays.asList(sugar, flour, confectionery));
                orderRepository.save(order1);

                // Get CustomQuery
                List<Object[]> objList = orderRepository.findCustomOrderByReference(ref);
                Customer customer = (Customer) objList.get(0)[0];
                Set<Articles> articles = new HashSet<Articles>();
                objList.forEach(obj -> articles.add((Articles) obj[1]));
                Set<Address> addresses = new HashSet<Address>();
                objList.forEach(obj -> addresses.add((Address) obj[2]));

                Order order = new Order(ref, customer, addresses, articles);
                print(order.getReference().toString());
                print(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
                order.getArticles().forEach(article -> print(article.getLabel()));
                order.getAddresses().forEach(address -> print(address.toString()));

        }
}
