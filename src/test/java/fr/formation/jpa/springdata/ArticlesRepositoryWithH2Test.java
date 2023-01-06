package fr.formation.jpa.springdata;

import fr.formation.jpa.springdata.entities.Articles;
import fr.formation.jpa.springdata.repositories.ArticlesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ArticlesRepositoryWithH2Test {

    @Autowired
    private ArticlesRepository articlesRepository;

    @BeforeEach
    public void setup() {
        // initialisation de la base avant chaque test.

    }

    @Test
    public void shouldInsertArticlesIntoDB() {
        Articles sugar = new Articles(1L, "Sucre",
                "Le sucre est une substance de saveur douce extraite principalement de la canne à sucre ou de la betterave sucrière.",
                1.39);
        final Articles flour = new Articles(2L,
                "farine",
                "La farine est une poudre obtenue en broyant et en moulant des céréales ou d'autres produits agricoles alimentaires solides, souvent des graines.",
                1.00);
        final Articles confectionery = new Articles(3L,
                "confiserie",
                "Une confiserie est un produit à base de sucre qui est vendu dans un magasin du même nom et fabriqué par un confiseur.",
                4.76);
        // articlesRepository.saveAll(Arrays.asList(sugar, flour, confectionery));
        articlesRepository.save(sugar);

        Articles sugarFromDB = articlesRepository.findById(1L).get();

        Assertions.assertEquals(sugar, sugarFromDB);

    }

}
