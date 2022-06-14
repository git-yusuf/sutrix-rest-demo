package com.sutrix.yusuf.repo;

import com.sutrix.yusuf.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepoTest {

    @Autowired
    private PersonRepo personRepo;

    @Test
    public void testRepo(){
        Person testPerson = Person.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .email("test@email.com")
                .age(40)
                .build();

        personRepo.save(testPerson);

        Person foundPersonWithEmail = personRepo.findFirstByEmail("test@email.com");

        assertEquals("test@email.com", foundPersonWithEmail.getEmail());
    }
}