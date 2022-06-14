package com.sutrix.yusuf.service;

import com.sutrix.yusuf.repo.PersonRepo;
import com.sutrix.yusuf.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    PersonRepo personRepo;

    private static boolean checkIfMoreThan35(Person person) {
        return person.getAge() > 35;
    }

    public Person getPersonById(Long id) {
        return personRepo.findById(id).orElse(null);
    }

    public Person create(Person person){
        return personRepo.save(person);
    }

    public List<Person> getEligiblePersons() {

        List<Person> all = getAllPersons();

        return all.parallelStream()
                .filter(PersonService::checkIfMoreThan35)
                .collect(Collectors.toList());

    }

    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    public boolean isEmailAlreadyExist(String email) {
        return (personRepo.findFirstByEmail(email.toUpperCase()) != null);
    }
}
