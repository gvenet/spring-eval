package fr.formation.jpa.springdata.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {
    public Customer(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Customer(Long id, String lastName, String firstName, Country country) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.country = country;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client", nullable = false)
    private Long id;

    @Column(name = "nom")
    private String lastName;

    @Column(name = "prenom")
    private String firstName;

    @ManyToOne
    @JoinColumn(name = "fk_id_pays", nullable = false)
    private Country country;
    
    @ManyToMany
    @JoinTable( name = "adresses_client",
                joinColumns = @JoinColumn( name = "id_client" ),
                inverseJoinColumns = @JoinColumn( name = "id_adresse" ) )
    private Set<Address> addresses = new HashSet<Address>();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
