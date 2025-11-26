package school.xset.homework12.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import school.xset.homework12.models.Count;
import school.xset.homework12.models.Department;
import school.xset.homework12.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KafkaConsumer {
    LogServiceConsumer logService = new LogServiceConsumer();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @KafkaListener(topics = "${spring.kafka.producer.topic.out}")

    public void consume(String message) throws JsonProcessingException {
//        logService.writeLog(message.toString(),"");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(message);
        String urlRequest = json.get("urlRequest").asText();
        String msg = json.get("message").asText();

        logService.writeLog(urlRequest, "{" + msg + "}");

        switch (urlRequest) {

//          1. POST /createUser - создание пользователя

            case "/createUser":
                Pattern patternCreateUser = Pattern.compile("firstName=([^,]+), lastName=([^,]+), birthDate=([^,]+), departId=(\\d{1})");
                Matcher matcherCreateUser = patternCreateUser.matcher(msg);
                User userCreate = new User();

                if (matcherCreateUser.find()) {
                    userCreate.setFirstName(matcherCreateUser.group(1));
                    userCreate.setLastName(matcherCreateUser.group(2));
                    userCreate.setBirthDate(LocalDate.parse(matcherCreateUser.group(3)));
                    userCreate.setDepartId(Integer.parseInt(matcherCreateUser.group(4)));
                    int result = jdbcTemplate.update("INSERT INTO Users (first_name, last_name, birth_day, depart_id) VALUES (?, ?, ?, ?)",
                           userCreate.getFirstName(), userCreate.getLastName(), userCreate.getBirthDate(), userCreate.getDepartId());
                    if (result == 1) {
                        logService.writeLog(urlRequest, "200 Ok");
                    }else{
                        logService.writeLog(urlRequest, "failure");
                    }
                }else{
                    logService.writeLog(urlRequest, "Error: the string is not parsed");
                }
                break;

//          2. POST /updateUser - обновление пользователя

            case "/updateUser":

                logService.writeLog(msg, "Это msg");
                Pattern patternUpdateUser = Pattern.compile("id=(\\d{1,4}), lastName=([^, ]+), departId=(\\d{1})$");
                Matcher matcherUpdateUser = patternUpdateUser.matcher(msg);

                if (matcherUpdateUser.find()) {
                    Integer id = Integer.parseInt(matcherUpdateUser.group(1));
                    String updateLastName = matcherUpdateUser.group(2);
                    Integer updateDepartId = Integer.parseInt(matcherUpdateUser.group(3));

                    logService.writeLog(updateLastName + " " + updateDepartId + " " + id, "Update user");

                    int result = jdbcTemplate.update("UPDATE users SET last_name = ?, depart_id = ? WHERE id = ?",
                                updateLastName, updateDepartId, id);
                    if (result != 0) {
                        logService.writeLog(urlRequest, "200 Ok");

                    }   else{
                        logService.writeLog(urlRequest, "failure");
                    }



                }else{
                    logService.writeLog(urlRequest, "Error: the string is not parsed");
                }
                break;

//          3. GET /getAllUsers - получение всех пользователей

            case "/getAllUsers":

                List<User> users = jdbcTemplate.query("SELECT * FROM users", new UserMapper());
                logService.writeLog(urlRequest, users.toString());
                break;

//          4. GET /getUserByBirthDate?birthDate={birthDate} - получение пользователей по дате рождения

            case "/getUserByBirthDate":
                List<User> usersBirth = jdbcTemplate.query("SELECT * FROM users WHERE birth_day = '" + msg + "'", new UserMapper());
                logService.writeLog(urlRequest, usersBirth.toString());
                break;

//          5. DELETE /deleteUserByLastName?LastName={LastName} - удаление пользователей по Фамилии

            case "/deleteUserByLastName":
                logService.writeLog("DELETE FROM users WHERE last_name = " + msg, "");
                int result = jdbcTemplate.update("DELETE FROM users WHERE last_name = '" + msg + "'");
                if (result != 0) {
                    logService.writeLog(urlRequest, "200 Ok");
                }else{
                    logService.writeLog(urlRequest, "failure");
                }

                break;

//          6. DELETE /deleteUsers - удаление пользователей (truncate)

            case "/deleteUsers":
                int resultDeleteUsers = jdbcTemplate.update("TRUNCATE TABLE users");
                // TRUNCATE возвращает 0 (затронутых записей), т.е. таблица пуста
                if (resultDeleteUsers == 0) {
                    logService.writeLog(urlRequest, "200 Ok");
                }else{
                    logService.writeLog(urlRequest, "failure");
                }
                break;

//          8. GET /getDepartments - получение всех департаментов

            case "/getDepartments":
                List<Department> departments = jdbcTemplate.query("SELECT * FROM departments", new BeanPropertyRowMapper<>(Department.class));
                logService.writeLog(urlRequest, departments.toString());
                break;

//          9. GET /getAmountUsersByDepartmentAndMore5 - получение количество юзеров по каждому департаменту, количество которых больше 5

            case "/getAmountUsersByDepartmentAndMore5":
                List<Count> count = jdbcTemplate.query("SELECT d.depart_name, COUNT(u.id) as user_count\n" +
                        "FROM departments d\n" +
                        "LEFT JOIN users u ON d.id = u.depart_id\n" +
                        "GROUP BY d.id, d.depart_name\n" +
                        "HAVING COUNT(u.id) > 5\n" +
                        "ORDER BY user_count DESC", new CountMapper());
                logService.writeLog(urlRequest, count.toString());
                break;

            default:
                logService.writeLog("/" + urlRequest, "unknown urlRequest");
        }


    }


    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setFirstName(rs.getString("first_name"));
            u.setLastName(rs.getString("last_name"));
            u.setBirthDate(rs.getDate("birth_day").toLocalDate());
            u.setDepartId(rs.getInt("depart_id"));
            return u;
        }
    }

    private static class DepartmentMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department d = new Department();
            d.setId(rs.getInt("id"));
            d.setDepartName(rs.getString("depart_name"));
            return d;
        }
    }

    private static class CountMapper implements RowMapper<Count> {
        @Override
        public Count mapRow(ResultSet rs, int rowNum) throws SQLException {
            Count c = new Count();
            c.setDepartName(rs.getString("depart_name"));
            c.setCount(rs.getInt("user_count"));
            return c;
        }

    }
}
