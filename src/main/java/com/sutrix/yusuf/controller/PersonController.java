package com.sutrix.yusuf.controller;

import com.sutrix.yusuf.dto.PersonDTO;
import com.sutrix.yusuf.exceptions.ResourceNotFoundException;
import com.sutrix.yusuf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.sutrix.yusuf.model.Person;

import javax.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPersonById(@PathVariable("id") Long id) throws ResourceNotFoundException {
		
        Person person = personService.getPersonById(id);
        
        if (person == null){
            throw new ResourceNotFoundException("No user found with the Id: " + id);
        }

        return ResponseEntity.ok(person);
	}

    @GetMapping(path = "/")
    public ResponseEntity<Object> getAllPersons(){
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping(path = "/eligible")
    public ResponseEntity<Object> getEligiblePersons(){
        return ResponseEntity.ok(personService.getEligiblePersons());
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createPerson(@Valid @RequestBody PersonDTO personDTO) {

        if (personService.isEmailAlreadyExist(personDTO.getEmail())){
            return ResponseEntity.badRequest().body("Email is already taken.");
        }

        Person createdPerson = personService.create(personDTO.toPerson());

        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }


}
