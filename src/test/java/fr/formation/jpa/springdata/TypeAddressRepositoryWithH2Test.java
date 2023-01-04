package fr.formation.jpa.springdata;


import fr.formation.jpa.springdata.entities.TypeAddress;
import fr.formation.jpa.springdata.repositories.TypeAddressRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TypeAddressRepositoryWithH2Test {

    @Autowired
    private TypeAddressRepository typeAddressRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldSave() {
        final Long ID = 4L;
        final String LABEL = "type4";

        //WHEN
        typeAddressRepository.save(new TypeAddress(ID,LABEL));
        
        //THEN
        Optional<TypeAddress> typeAddressFromDB = typeAddressRepository.findById(ID);
        Assertions.assertEquals(ID, typeAddressFromDB.get().getId());
        Assertions.assertEquals(LABEL, typeAddressFromDB.get().getLabel());

        System.out.println("\u001B[32m__TypeAddressRepositoryTest__ : " + typeAddressFromDB + "\u001B[0m");

        
        
        
    }
}
