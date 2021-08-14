package com.example.demo.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;

    //this specified a bidirectional relationship, but this entity is not owner's relationship.
    @OneToOne(mappedBy = "address")
    private Student student;

    public Address(String street, String city){
        this.city = city;
        this.street = street;
    }
}
