package com.example.coursemanagement.dto;

import com.example.coursemanagement.entity.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String title;
    private CourseStatus status;
    private Long instructorId;
}