package fr.formation.jpa.springdata.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "adresses")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Address {

    public Address(Long id, String street, String postalCode, String city, TypeAddress typeAdress, Country country) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.typeAdress = typeAdress;
        this.country = country;
    }

    @Id
    @Column(name = "id_adresse")
    private Long id;

    @Column(name = "rue")
    private String street;

    @Column(name = "code_postal")
    private String postalCode;

    @Column(name = "ville")
    private String city;
    
    @ManyToOne
    @JoinColumn(name = "fk_id_type_adresse")
    private TypeAddress typeAdress;

    @ManyToOne
    @JoinColumn(name = "fk_id_pays")
    private Country country;

}
