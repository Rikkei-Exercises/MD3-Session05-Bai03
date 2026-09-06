package com.example.coursemanagement.controller;

import com.example.coursemanagement.dto.ApiResponse;
import com.example.coursemanagement.dto.CourseResponse;
import com.example.coursemanagement.dto.PageResponse;
import com.example.coursemanagement.entity.CourseStatus;
import com.example.coursemanagement.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CourseResponse>>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction direction,
            @RequestParam(defaultValue = "ACTIVE") CourseStatus status
    ) {

        PageResponse<CourseResponse> result =
                courseService.getPagedCoursesByStatus(
                        page,
                        size,
                        sortBy,
                        direction,
                        status
                );

        return ResponseEntity.ok(
                new ApiResponse<>(result)
        );
    }
}