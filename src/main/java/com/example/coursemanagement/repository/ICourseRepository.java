package com.example.coursemanagement.repository;

import com.example.coursemanagement.entity.Course;
import com.example.coursemanagement.entity.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.status = :status")
    Page<Course> findAllByStatus(
            @Param("status") CourseStatus status,
            Pageable pageable
    );
}