package com.example.demo.responses;

import com.example.demo.entities.Student;
import com.example.demo.entities.Subject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class StudentResponse {

    private long id;

//    @JsonProperty("first_name")
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String city;

    private List<SubjectResponse> subjectsLearning;

    public StudentResponse(Student student){
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();

        this.street = student.getAddress().getStreet();
        this.city = student.getAddress().getCity();

        this.subjectsLearning = student.getSubjects().stream()
                .map(SubjectResponse::new)
                .collect(Collectors.toList());
        ;
    }
}
