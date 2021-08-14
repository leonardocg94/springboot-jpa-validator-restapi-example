package com.example.demo.responses;

import com.example.demo.entities.Subject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectResponse {
    private String subjectName;
    private Double marksObtained;

    public SubjectResponse(Subject subject){
        this.subjectName = subject.getSubjectName();
        this.marksObtained = subject.getMarksObtained();
    }
}
