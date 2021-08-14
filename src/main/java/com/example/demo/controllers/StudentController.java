package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.requests.CreateStudentRequest;
import com.example.demo.requests.InQueryRequest;
import com.example.demo.requests.UpdateStudentRequest;
import com.example.demo.responses.StudentResponse;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    //This annotation serves to read variables from application.properties file
    //right side from double dots specify the default value in case that variable will not found
    @Value("${app.name:Demo app}")
    private String appName;

//    @GetMapping("/get")
//    public StudentResponse getStudent(){
//        return new StudentResponse(1, "Jhon", "Smith");
//    }

    @GetMapping
    public List<StudentResponse> retrieveAllStudents(){
         return studentService.getAll();
    }

    @GetMapping("/{id}")
    public StudentResponse retrieveOneStudent(@PathVariable Long id){
        return new StudentResponse(studentService.getOne(id));
    }

    @PostMapping
    public StudentResponse createNewStudent(@Valid @RequestBody CreateStudentRequest reqStudent){
        return new StudentResponse(studentService.addNew(reqStudent));
    }

    @PatchMapping
    public StudentResponse updateOneStudent(@Valid @RequestBody UpdateStudentRequest reqStudent){
        return new StudentResponse(studentService.updateOne(reqStudent));
    }

    @DeleteMapping
    public void deleteOneStudent(@RequestParam Long id){
        studentService.deleteOne(id);
    }

    @GetMapping("/byName")
    public List<StudentResponse> getAllByFirstName(@RequestParam String firstName){
        return studentService.getByFirstName(firstName);
    }

    @GetMapping("/byFullName")
    public List<StudentResponse> getAllByNameAndLastName(@RequestParam String firstName, @RequestParam String lastName){
        return studentService.getByFullName(firstName, lastName);
    }

    @GetMapping("/byBothNames")
    public List<StudentResponse> getAllByNameOrLastName(@RequestParam String firstName, @RequestParam String lastName){
        return studentService.getByBothNames(firstName,lastName);
    }

    @GetMapping("/byNames")
    public List<StudentResponse> getAllByFirstNames(@RequestBody InQueryRequest inQueryRequest){
        return studentService.getByNames(inQueryRequest);
    }

    @GetMapping("/paginated")
    public List<StudentResponse> getAllWithPagination(@RequestParam int pageNum, @RequestParam int pageSize){
        return studentService.getAllWithPagination(pageNum, pageSize);
    }

    @GetMapping("/sorted/{criteria}")
    public List<StudentResponse> getAllWithSorting(@PathVariable String criteria, @RequestParam boolean desc){
        return studentService.getAllWithSorting(criteria, desc);
    }

    @GetMapping("/search")
    public List<StudentResponse> getAllWithSearchCriteria(@RequestParam String text){
        return studentService.getBySearchText(text);
    }

    @PatchMapping("/updateEmail/{id}")
    public String updateOneEmailStudent(@PathVariable Long id, @RequestParam String email){
        return studentService.updateEmailById(id, email) + "  Student(s) was updated";
    }

    @DeleteMapping("/deleteByEmail/{email}")
    public Integer deleteOneStudentByEmail(@PathVariable String email){
        return studentService.deleteByEmail(email);
    }

    @GetMapping("/byCity/{city}")
    public List<StudentResponse> getAllStudentsByCity(@PathVariable String city){
        return studentService.getByCity(city);
    }

    @GetMapping("/byStreet/{street}")
    public List<StudentResponse> getAllStudentsByStreet(@PathVariable String street){
        return studentService.getByStreet(street);
    }
}
