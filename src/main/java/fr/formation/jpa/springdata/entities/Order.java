package fr.formation.jpa.springdata.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "commandes")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {

    public Order(String reference, Customer customer, Set<Address> addresses, Set<Articles> articles) {
        this.reference = reference;
        this.customer = customer;
        this.addresses = addresses;
        this.articles = articles;
    }

    public Order(String reference, Customer customer, Date dateOrder, Date dateDelivery, Country country) {
        this.reference = reference;
        this.customer = customer;
        this.dateOrder = dateOrder;
        this.dateDelivery = dateDelivery;
        this.country = country;
    }

    @Id
    @Column(name = "id_commande")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "fk_id_client", nullable = false)
    private Customer customer;

    @Column(name = "date_commande")
    private Date dateOrder;

    @Column(name = "date_livraison")
    private Date dateDelivery;

    @ManyToOne
    @JoinColumn(name = "fk_id_pays", nullable = false)
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @ManyToMany
    @JoinTable(name = "commande_adresses", joinColumns = @JoinColumn(name = "id_commande"), inverseJoinColumns = @JoinColumn(name = "id_adresse"))
    private Set<Address> addresses = new HashSet<Address>();

    @ManyToMany
    @JoinTable(name = "commande_articles", joinColumns = @JoinColumn(name = "id_commande"), inverseJoinColumns = @JoinColumn(name = "id_article"))
    private Set<Articles> articles = new HashSet<Articles>();

}
