package com.example.demo.entities;

import com.example.demo.requests.CreateStudentRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    //fetch argument in OneToOne annotation means that address data only be accessed when we needed it explicitly
    //and don't be accessed when we fetch a student, normally is fetched when we invoke getMethod of the relationship
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "student")
    private List<Subject> subjects;

    public Student(CreateStudentRequest reqStudent){
        this.firstName = reqStudent.getFirstName();
        this.lastName = reqStudent.getLastName();
        this.email = reqStudent.getEmail();
    }
}


