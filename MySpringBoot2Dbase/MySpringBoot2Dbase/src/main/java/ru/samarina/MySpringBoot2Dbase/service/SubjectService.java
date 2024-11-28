package ru.samarina.MySpringBoot2Dbase.service;

import ru.samarina.MySpringBoot2Dbase.entity.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> getAllSubjects();

    Subject saveSubject (Subject subject);

    Subject getSubject(int id);

    void deleteSubject (int id);
}
