package school.xset.homework12.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.xset.homework11.services.StudentService;
import school.xset.homework12.models.User;
import school.xset.homework12.services.InitDB;
import school.xset.homework12.services.KafkaProducer;
import school.xset.homework12.services.LogServiceProducer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
public class KafkaController {

    LogServiceProducer logService = new LogServiceProducer();
    private final InitDB service;
    public KafkaController(InitDB service) {this.service = service;}

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) throws JsonProcessingException {
        // Валидация: FirstName и LastName не null
        if ((user.getFirstName() != null && user.getFirstName().isBlank())||(user.getLastName() != null && user.getLastName().isBlank())) {
            return ResponseEntity.badRequest().body("Ошибка: FirstName и LastName обязательны");
        }
        logService.writeLog(user.toString(), "");
        String message = user.toString();
//        String message = new ObjectMapper().writeValueAsString(user);
        kafkaProducer.sendMessage("{\"urlRequest\":\"/createUser\", \"message\":\"" + message + "\"}");
//        kafkaProducer.sendMessage("urlRequest:createUser,message:" + message);
        return ResponseEntity.ok("Запись по созданию пользователя успешно добавлена в кафку");
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateLastNameAndGroupName(@RequestBody User user, @RequestParam("id") Integer id) throws IOException {

        String lastName = user.getLastName();
        int departId = user.getDepartId();
        String strErr = "";
        if (lastName != null && lastName.isBlank()) {
            strErr = "Error: blank data";
            logService.writeLog("UPDATE users", strErr);
            return ResponseEntity.ok("Нет фамилии для обновления");
        }
        String message = "id=" + id + ", lastName=" + lastName + ", departId=" + departId;
        kafkaProducer.sendMessage("{\"urlRequest\":\"/updateUser\", \"message\":\"" + message + "\"}");
        return ResponseEntity.ok("Запись по обновлению пользователя успешно добавлена в кафку");
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<String> getAllUsers() {
        kafkaProducer.sendMessage("{\"urlRequest\":\"/getAllUsers\", \"message\":\"\"}");
        return ResponseEntity.ok("Запись по получению всех пользователей успешно добавлена в кафку");
    }

    @GetMapping("/getUserByBirthDate")
    public ResponseEntity<String> getUserByBirthDate(@RequestParam String birthDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(birthDate, formatter);
            kafkaProducer.sendMessage("{\"urlRequest\":\"/getUserByBirthDate\", \"message\":\"" + birthDate + "\"}");
            return ResponseEntity.ok("Запись по получению пользователей по дате рождения успешно добавлена в кафку");
        } catch (DateTimeParseException e) {
            return ResponseEntity.ok("неверный формат даты");
        }

    }

    @DeleteMapping("/deleteUserByLastName")
    public ResponseEntity<String> deleteUserByLastName(@RequestParam String lastName) {
        String strErr = "";
        if (lastName != null && lastName.isBlank()) {
            strErr = "Error: blank data";
            logService.writeLog("UPDATE users", strErr);
            return ResponseEntity.ok("Нет фамилии для удаления");
        }
        kafkaProducer.sendMessage("{\"urlRequest\":\"/deleteUserByLastName\", \"message\":\"" + lastName + "\"}");
        return ResponseEntity.ok("Запись по удаление пользователей по Фамилии успешно добавлена в кафку");
    }
    @DeleteMapping("/deleteUsers")
    public ResponseEntity<String> deleteUserByLastName() {
        kafkaProducer.sendMessage("{\"urlRequest\":\"/deleteUsers\", \"message\":\"\"}");
        return ResponseEntity.ok("Запись по удалению всех пользователей успешно добавлена в кафку");
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<String> getDepartments() {
        kafkaProducer.sendMessage("{\"urlRequest\":\"/getDepartments\", \"message\":\"\"}");
        return ResponseEntity.ok("Запись по получению всех департаментов успешно добавлена в кафку");
    }

    @GetMapping("/getAmountUsersByDepartmentAndMore5")
    public ResponseEntity<String> getAmountUsersByDepartmentAndMore5() {
        kafkaProducer.sendMessage("{\"urlRequest\":\"/getAmountUsersByDepartmentAndMore5\", \"message\":\"\"}");
        return ResponseEntity.ok("Запись по получению количества юзеров по каждому департаменту, количество которых больше 5, успешно добавлена в кафку");
    }

    @GetMapping("/log")
    public ResponseEntity<String> getLogs() {
        List<String> logs = LogServiceProducer.readLog();
        return ResponseEntity.ok(logs.toString());
    }
}

