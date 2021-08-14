package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
public class CreateStudentRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
    @NotBlank(message = "email is required")
    private String email;

    private String street;
    private String city;

    private List<CreateSubjectRequest> subjectsLearning;
}
