package com.example.demo.repositories;

import com.example.demo.entities.Address;
import com.example.demo.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByFirstName(String firstName);
    List<Student> findByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findByFirstNameOrLastName(String firstName, String lastName);
    List<Student> findByFirstNameIn(List<String> firstNames);

//    Variables can be by name ":variable" or by position "?position"
//    although, we can use alias with @Param("alias") annotation
    @Query("from Student where firstName like %:firstName%")
    List<Student> searchBar(String firstName);

//    These annotations are required for every method that change something in database
    @Modifying
    @Transactional
    @Query("update Student set email=?2 where id=?1")
    Integer updateEmail(Long id, String email);

    @Modifying
    @Transactional
    @Query("delete from  Student where email=?1")
    Integer deleteByEmail(String email);

//    this equivalent to "select * from student s, address a where a.id=s.address_id and a.city=?1"
    List<Student> findByAddress_City (String city);

    @Query("from Student s, Address a where s.address.id=a.id and a.street=?1")
    List<Student> getForStreet(String street);
}
