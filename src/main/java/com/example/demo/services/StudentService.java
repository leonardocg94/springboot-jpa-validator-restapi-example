package com.example.demo.services;


import com.example.demo.entities.Address;
import com.example.demo.entities.Student;
import com.example.demo.entities.Subject;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.SubjectRepository;
import com.example.demo.requests.CreateStudentRequest;
import com.example.demo.requests.InQueryRequest;
import com.example.demo.requests.UpdateStudentRequest;
import com.example.demo.responses.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, AddressRepository addressRepository,
                          SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.addressRepository = addressRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream()
                .map(StudentResponse::new).collect(Collectors.toList());
    }

    public Student getOne(Long id) {
        return studentRepository.findById(id).orElseGet(() -> null);
    }

    public Student addNew(CreateStudentRequest reqStudent) {
        Address relAddress = addressRepository.save(new Address(reqStudent.getStreet(), reqStudent.getCity()));
        Student relStudent = new Student(reqStudent);
        relStudent.setAddress(relAddress);
        Student finalStudent = studentRepository.save(relStudent);

        List<Subject> relSubjects = subjectRepository.saveAll(reqStudent.getSubjectsLearning().stream()
                .map(s -> new Subject(s.getSubjectName(), s.getMarksObtained(), finalStudent))
                .collect(Collectors.toList())
        );
        finalStudent.setSubjects(relSubjects);

        return finalStudent;
    }

    public Student updateOne(UpdateStudentRequest reqStudent) {
        return studentRepository.findById(reqStudent.getId())
                .map(s -> {
                    if (reqStudent.getEmail() != null && !reqStudent.getEmail().isEmpty())
                        s.setEmail(reqStudent.getEmail());
                    if (reqStudent.getFirstName() != null && !reqStudent.getFirstName().isEmpty())
                        s.setFirstName(reqStudent.getFirstName());
                    if (reqStudent.getLastName() != null && !reqStudent.getLastName().isEmpty())
                        s.setLastName(reqStudent.getLastName());
                    return studentRepository.save(s);
                })
                .orElseGet(() -> null);
    }

    public void deleteOne(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentResponse> getByFirstName(String firstName) {
        return studentRepository.findByFirstName(firstName).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getByFullName(String firstName, String lastName) {
        return studentRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getByBothNames(String firstName, String lastName) {
        return studentRepository.findByFirstNameOrLastName(firstName, lastName).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getByNames(InQueryRequest inQueryRequest) {
        return studentRepository.findByFirstNameIn(inQueryRequest.getFirstNames()).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getAllWithPagination(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return studentRepository.findAll(pageable).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getAllWithSorting(String criteria, boolean desc) {
        Sort sort = Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, criteria);
        return studentRepository.findAll(sort).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getBySearchText(String firstName) {
        return studentRepository.searchBar(firstName).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public Integer updateEmailById(Long id, String email) {
        return studentRepository.updateEmail(id, email);
    }

    public Integer deleteByEmail(String email) {
        return studentRepository.deleteByEmail(email);
    }

    public List<StudentResponse> getByCity(String city) {
        return studentRepository.findByAddress_City(city).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getByStreet(String street) {
        return studentRepository.getForStreet(street).stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }
}
