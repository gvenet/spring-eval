package fr.formation.jpa.springdata.repositories;

import fr.formation.jpa.springdata.entities.Articles;

import org.springframework.data.repository.CrudRepository;

public interface ArticlesRepository extends CrudRepository<Articles, Long> {
}
