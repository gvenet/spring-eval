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
@Table(name = "articles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Articles {

    public Articles(Long id, String label, String description, Double price) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.price = price;
    }

    @Id
    @Column(name = "id_article")
    private Long id;

    @Column(name = "libelle")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "prix_HT")
    private Double price;

}
