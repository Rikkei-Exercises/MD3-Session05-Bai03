package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.CourseResponse;
import com.example.coursemanagement.dto.PageResponse;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.entity.CourseStatus;
import com.example.coursemanagement.repository.ICourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final ICourseRepository courseRepository;

    public PageResponse<CourseResponse> getPagedCourses(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {

        // Safety check page
        if (page < 0) {
            page = 0;
        }

        // Nếu không truyền sortBy
        // thì mặc định sắp xếp theo id
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        // Tạo Sort
        Sort sort = Sort.by(direction, sortBy);

        // Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy tất cả Course có phân trang
        Page<Course> courses = courseRepository.findAll(pageable);

        // Map Course -> CourseResponse
        Page<CourseResponse> courseResponses = courses.map(course ->
                new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getStatus(),
                        course.getInstructorId()
                )
        );

        // Map Page<CourseResponse>
        // -> PageResponse<CourseResponse>
        return new PageResponse<>(
                courseResponses.getContent(),
                courseResponses.getNumber(),
                courseResponses.getSize(),
                (int) courseResponses.getTotalElements(),
                courseResponses.getTotalPages(),
                courseResponses.isLast()
        );
    }

    public PageResponse<CourseResponse> getPagedCoursesByStatus(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction,
            CourseStatus status
    ) {

        // Safety check page
        if (page < 0) {
            page = 0;
        }

        // Nếu không truyền sortBy
        // thì mặc định sắp xếp theo id
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        // Tạo Sort
        Sort sort = Sort.by(direction, sortBy);

        // Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Lấy Course theo status + phân trang
        Page<Course> courses =
                courseRepository.findAllByStatus(status, pageable);

        // Map Course -> CourseResponse
        Page<CourseResponse> courseResponses = courses.map(course ->
                new CourseResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getStatus(),
                        course.getInstructorId()
                )
        );

        // Map Page<CourseResponse>
        // -> PageResponse<CourseResponse>
        return new PageResponse<>(
                courseResponses.getContent(),
                courseResponses.getNumber(),
                courseResponses.getSize(),
                (int) courseResponses.getTotalElements(),
                courseResponses.getTotalPages(),
                courseResponses.isLast()
        );
    }
}