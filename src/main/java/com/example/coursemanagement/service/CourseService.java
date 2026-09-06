package com.example.coursemanagement.service;

import com.example.coursemanagement.dto.CourseResponse;
import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.repository.ICourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final ICourseRepository courseRepository;

    public CourseService(ICourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<CourseResponse> getPagedCourses(
            int page,
            int size,
            String sortBy,
            Sort.Direction direction
    ) {

        // 1. Safety check page
        if (page < 0) {
            page = 0;
        }

        // 2. Nếu không truyền sortBy thì mặc định sort theo id
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        // 3. Tạo Sort
        Sort sort = Sort.by(direction, sortBy);

        // 4. Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // 5. Lấy dữ liệu từ database
        Page<Course> courses = courseRepository.findAll(pageable);

        // 6. Map Page<Course> -> Page<CourseResponse>
        return courses.map(course -> new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                course.getInstructorId()
        ));
    }
}