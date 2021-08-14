package com.example.demo.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InQueryRequest {
    private List<String> firstNames;
}
