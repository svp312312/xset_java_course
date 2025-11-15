//Необходимо реализовать backend для подключения через JDBC к PostgreSQL со следующими условиями:
//        1. Подключение осуществляется через application.properties
//        2. Создание таблиц и их наполнение осуществляется через BackEnd, предусмотреть предварительную
//        чистку ранее созданных таблиц
//        3. Необходимо реализовать следующие методы (
//        3.1 получение всех студентов, которые отсортированы по фамилии
//        3.2 получение студента по id
//        3.3 получение списка студентов по названию группы
//        3.4 обновление имени студента и названии группы по id
//        3.5 создание нового студента
//        3.6 удаление студента
//        4. Необходимо реализовать логирование:
//        4.1 Каждый запрос к БД необходимо писать в текстовый файл
//        4.2 Реализовать запрос /logs с получением данных из файла за определенный промежуток времени


package school.xset.homework11.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.xset.homework11.models.Student;
import school.xset.homework11.services.LogService;
import school.xset.homework11.services.StudentService;
import java.io.IOException;
import java.util.List;

@RestController

public class DBController {
    LogService logService = new LogService();
    private final StudentService service;
    public DBController(StudentService service) {this.service = service;}

    //  3.1 получение всех студентов, которые отсортированы по фамилии
    @GetMapping("/student/all-students")
    public List<Student> allStudents() throws IOException {
        List<Student> result = service.findAllStudents();
        logService.writeLog("SELECT * FROM students ORDER BY last_name", "200 OK");
        return service.findAllStudents();
    }

//  3.2 получение студента по id
    @GetMapping("/student/{id}")
    public Student student(@PathVariable("id") Integer id) throws IOException {
        Student result = service.findStudentById(id);
        logService.writeLog("SELECT * FROM students WHERE student_id = " + id, "200 OK");
        return result;
    }

//  3.3 получение списка студентов по названию группы
    @GetMapping("/student/groupId/{groupId}")
    public List<Student> getStudentsOfGroup(@PathVariable("groupId") String groupId) throws IOException {
        List<Student> result = service.findByGroup(groupId);
        logService.writeLog("SELECT * FROM students WHERE group_name = " + groupId, "200 OK");
        return result;
    }

//  3.4 обновление имени студента и названия группы по id
    @PutMapping("/student/updateStudent")
    public String updateLastNameAndGroupName(@RequestBody Student student, @RequestParam("id") Integer id) throws IOException {
        String lastName = student.getLastName();
        String groupName = student.getGroupName();
        String strErr = "";
        if (lastName != null && lastName.isBlank()) {
            strErr = "Error: blank lastName";
            logService.writeLog("UPDATE students", strErr);
            return strErr;
        }
        if (groupName != null && groupName.isBlank()) {
            strErr = "Error: blank groupName";
            logService.writeLog("UPDATE students", strErr);
            return strErr;
        }
        service.updateStudentLastNameAndGroupName(id, lastName, groupName);
        logService.writeLog("UPDATE students", "200 OK");
        return "Student updated";
    }

//  3.5 создание нового студента
    @PostMapping("/student/create")
    public String createStudent(@RequestBody Student student) throws IOException {
        String lastName = student.getLastName();
        String firstName = student.getFirstName();
        String strErr = "";
        if (lastName != null && lastName.isBlank()) {
            strErr = "Error: blank lastName";
            logService.writeLog("INSERT INTO student", strErr);
            return strErr;
        }
        if (firstName != null && firstName.isBlank()) {
            strErr = "Error: blank firstName";
            logService.writeLog("INSERT INTO student", strErr);
            return strErr;
        }
        String result = service.createStudent(student);
        logService.writeLog("INSERT INTO students","New student " + firstName + " " + lastName + " created");
        return result;
    }

//  3.6 удаление студента
    @DeleteMapping("student/{id}/deleteStudentById")
    public String deleteStudentById(@PathVariable("id") Integer id) throws IOException {
        service.deleteStudent(id);
        String result = "200 OK";
        logService.writeLog("DELETE FROM students WHERE student_id = " + id, result);
        return result;
    }

//  4.2 Реализовать запрос /logs с получением данных из файла за определенный промежуток времени
    @GetMapping("/logs")
    public ResponseEntity<String> getLogs(@RequestParam String startDateTime, @RequestParam String endDateTime) {
        List<String> logs = LogService.readLog(startDateTime, endDateTime);
        return ResponseEntity.ok(logs.toString());
    }

}




