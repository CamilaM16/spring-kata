package com.ss.web.app.enrollment.repository;

import com.ss.web.app.enrollment.Enrollment;
import com.ss.web.app.student.Student;
import com.ss.web.app.subject.Subject;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepoImpl implements EnrollmentRepo {

  private List<Enrollment> college;

  public EnrollmentRepoImpl() {
    college = fillCollege();
  }

  private List<Enrollment> fillCollege() {
    List<Enrollment> result = new ArrayList<>();
    Subject math = new Subject(101L, "Math", "It's math");
    Subject programming = new Subject(103L, "Programming", "It's programming");
    Subject physics = new Subject(102L, "Physics", "It's physics");

    Student first = new Student(201L, "Next", "Test");
    Student second = new Student(202L, "Curl", "Test");
    Student third = new Student(203L, "Sho", "Test");

    result.add(new Enrollment(math, first));
    result.add(new Enrollment(math, third));
    result.add(new Enrollment(programming, first));
    result.add(new Enrollment(programming, second));
    result.add(new Enrollment(physics, second));
    result.add(new Enrollment(physics, third));

    return result;
  }

  @Override
  public List<Student> listStudents(Long idSubject) {
    return college.stream()
            .filter(c -> equalsCode(c.getSubject(), idSubject))
            .map(Enrollment::getStudent)
            .collect(Collectors.toList());
  }

  @Override
  public List<Subject> listSubjects(Long idStudent) {
    return college.stream()
            .filter(c -> equalsId(c.getStudent(), idStudent))
            .map(Enrollment::getSubject)
            .collect(Collectors.toList());
  }

  @Override
  public Student getStudentOf(Long idSubject, Long idStudent) {
    return college.stream()
              .filter(c -> equalsCodeAndId(c, idSubject, idStudent)
              )
              .map(Enrollment::getStudent)
              .findFirst()
              .orElse(null);
  }

  @Override
  public Subject getSubjectOf(Long idStudent, Long idSubject) {
    return college.stream()
            .filter(c -> equalsCodeAndId(c, idSubject, idStudent)
            )
            .map(Enrollment::getSubject)
            .findFirst()
            .orElse(null);
  }

  private boolean equalsCodeAndId(Enrollment c, Long code, Long id) {
    return equalsId(c.getStudent(), id) && equalsCode(c.getSubject(), code);
  }
  private boolean equalsCode(Subject subject, Long code) {
    return subject.getCode().equals(code);
  }

  private boolean equalsId(Student student, Long id) {
    return student.getId().equals(id);
  }
}