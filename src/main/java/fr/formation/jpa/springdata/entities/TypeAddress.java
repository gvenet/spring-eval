package fr.formation.jpa.springdata.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_adresse")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TypeAddress {

    public TypeAddress(Long id, String label) {
        this.id = id;
        this.label = label;
    }

    @Id
    @Column(name = "id_type_adresse")
    private Long id;

    @Column(name = "libelle")
    private String label;

}
