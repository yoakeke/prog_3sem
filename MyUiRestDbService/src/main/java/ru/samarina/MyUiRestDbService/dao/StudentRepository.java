package ru.samarina.MyUiRestDbService.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.samarina.MyUiRestDbService.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
