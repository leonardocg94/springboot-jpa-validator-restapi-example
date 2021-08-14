package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubjectRequest {
    private String subjectName;
    private Double marksObtained;
}
