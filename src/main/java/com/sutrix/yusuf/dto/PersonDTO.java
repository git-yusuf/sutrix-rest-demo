package com.sutrix.yusuf.dto;

import com.sutrix.yusuf.model.Person;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PersonDTO {

    @NotNull(message = "First Name is mandatory")
    @Size(min = 1, max = 20, message = "First Name cannot exceed 20 characters")
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email must be a valid email address")
    @Size(min = 1, max = 50)
    private String email;

    @NotNull(message = "Age is mandatory")
    @Min(value = 18, message = "Age must be greater than or equal to 18")
    @Max(value = 49, message = "Age must be less than 50")
    private Integer age;

    public Person toPerson(){
        return Person.builder()
                .firstName(firstName.toUpperCase())
                .lastName(lastName.toUpperCase())
                .email(email.toUpperCase())
                .age(age)
                .build();
    }

    public PersonDTO(String firstName, String lastName, String email, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }
}
