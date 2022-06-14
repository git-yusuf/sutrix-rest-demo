package com.sutrix.yusuf.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sutrix.yusuf.model.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Long>{
    Person findFirstByEmail(String email);
}
