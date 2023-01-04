package fr.formation.jpa.springdata.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "pays")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Country {

    public Country(String id, String label, String tva, boolean actif) {
        this.id = id;
        this.label = label;
        this.tva = tva;
        this.actif = actif;
    }

    @Id
    @Column(name = "id_pays")
    private String id;

    @Column(name = "libelle")
    private String label;

    @Column(name = "TVA")
    private String tva;

    @Column(name = "actif")
    private boolean actif;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Country country = (Country) o;
        return id != null && Objects.equals(id, country.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
