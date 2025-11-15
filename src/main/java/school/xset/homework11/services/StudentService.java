package school.xset.homework11.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import school.xset.homework11.models.Student;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentService {
    LogService logService = new LogService();

    @Autowired
    private JdbcTemplate jdbc;

    // 2. Создание таблиц и их наполнение осуществляется через BackEnd, предусмотреть предварительную
    // чистку ранее созданных таблиц
    @PostConstruct
    public void initData() throws IOException {
        List<String> tables = List.of("enrollments", "courses", "students");
        for (String table : tables) {
            String sql = "DROP TABLE IF EXISTS " + table + " CASCADE";
            jdbc.execute(sql);
            logService.writeLog(sql, "200 OK");
        }

        jdbc.execute("""
                CREATE TABLE students( 
                student_id SERIAL PRIMARY KEY, 
                first_name VARCHAR(30) NOT NULL,
                last_name VARCHAR(30) NOT NULL,
                birth_date DATE NOT NULL,
                group_name VARCHAR(6) NOT NULL)
                """);
        logService.writeLog("CREATE TABLE students", "200 OK");
//
        jdbc.execute("""
                CREATE TABLE courses (
                course_id SERIAL PRIMARY KEY,
                course_name VARCHAR(50) NOT NULL,
                lecturer VARCHAR(30) NOT NULL)
                """ );
        logService.writeLog("CREATE TABLE courses ", "200 OK");

        jdbc.execute("""
                CREATE TABLE enrollments (
                enrollment_id SERIAL PRIMARY KEY,
                student_id INT REFERENCES students (student_id) ON DELETE CASCADE,
                course_id INT REFERENCES courses (course_id) ON DELETE CASCADE,
                grade INT CHECK(grade between 0 AND 10))
                """);
        logService.writeLog("CREATE TABLE enrollments", "200 OK");

        jdbc.execute("""
                INSERT INTO students (first_name, last_name, birth_date, group_name)
                VALUES
                ('Максим', 'Вязовской', '2004-01-20', 'ПИ-201'),
                ('Александр', 'Ким', '2003-11-03', 'ПИ-202'),
                ('Камиль', 'Валиуллин', '2002-12-04', 'ПИ-202'),
                ('Ульяна', 'Журавлёва', '2004-07-25', 'ПИ-201'),
                ('Захар', 'Ведмедко', '2006-10-22', 'ПИ-202'),
                ('Арсений', 'Шмидт', '2006-10-22', 'ПИ-202'),
                ('Аким', 'Часовщиков', '2006-10-22', 'ПИ-202'),
                ('Ева', 'Ноэль', '2006-10-10', 'ПИ-202'),
                ('Эльмира', 'Шафикова', '2005-12-27', 'ПИ-201'),
                ('Николь', 'Марциевич', '2003-10-15', 'ПИ-202')
                """);
        logService.writeLog("INSERT INTO students", "200 OK");
        jdbc.execute("""
                INSERT INTO courses (course_name, lecturer)
                VALUES
                ('Базы данных','Иконникова Т.Н.'),
                ('Основы web-технологий','Чеботаренко Ю.А.'),
                ('Информационный менеджмент',' Турицын О.Д.'),
                ('Интеллектуальные информационные системы','Василевская Н.Н.'),
                ('Программирование','Капитанов К.Ю.')
                """ );
        logService.writeLog("INSERT INTO courses", "200 OK");
        jdbc.execute("""
                INSERT INTO enrollments (student_id, course_id, grade)
                VALUES
                (1, 1, 8),(1, 2, 9),(1, 3, 7),(1, 5, 6),
                (2, 1, 5),(2, 2, 6),(2, 4, 7),(2, 5, 8),
                (3, 1, 7),(3, 2, 8),(3, 4, 10),(3, 5, 6),
                (4, 1, 10),(4, 2, 10),(4, 3, 10),(4, 5, 10),
                (5, 1, 9),(5, 2, 9),(5, 4, 10),(5, 5, 10),
                (6, 1, 4),(6, 2, 7),(6, 4, 5),(6, 5, 3),
                (7, 1, 6),(7, 2, 7),(7, 4, 6),(7, 5, 7),
                (8, 1, 10),(8, 2, 10),(8, 4, 10),(8, 5, 9),
                (9, 1, 8),(9, 2, 8),(9, 3, 7),(9, 5, 6),
                (10, 1, 9),(10, 2, 10),(10, 4, 10),(10, 5, 8)
                """ );
        logService.writeLog("INSERT INTO enrollments", "200 OK");
    }

    //  3.1 получение всех студентов, которые отсортированы по фамилии
    public List<Student> findAllStudents(){
        return jdbc.query("SELECT * FROM students ORDER BY last_name", new StudentMapper());
    }

    //  3.2 получение студента по id
    public Student findStudentById(Integer id){
        return jdbc.queryForObject("SELECT * FROM students WHERE student_id = ?", new StudentMapper(), id);
    }

    //  3.3 получение списка студентов по названию группы
    public List<Student> findByGroup(String group){
        return jdbc.query("SELECT * FROM students WHERE group_name = ?", new StudentMapper(), group);
    }

    //  3.4 обновление имени студента и названии группы по id
    public void updateStudentLastNameAndGroupName(int id, String newName, String newGroup){
        jdbc.update("UPDATE students SET last_name = ?, group_name = ? WHERE student_id = ?", newName, newGroup, id);
    }

    //   3.5 создание нового студента
    public String createStudent(Student student) {
        jdbc.update("INSERT INTO students(first_name, last_name, birth_date, group_name) VALUES (?, ?, ?, ?)",
                student.getFirstName(), student.getLastName(), student.getBirthDate(), student.getGroupName());
        return "Student was created";
    }

    //   3.6 удаление студента
    public void deleteStudent(Integer id){
        jdbc.update("DELETE FROM students WHERE student_id = ?", id);
    }

    private static class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow (ResultSet rs, int rowNum) throws SQLException {
            Student s = new Student();
            s.setStudentId(rs.getInt("student_id"));
            s.setFirstName(rs.getString("first_name"));
            s.setLastName(rs.getString("last_name"));
            s.setBirthDate(rs.getDate("birth_date").toLocalDate());
            s.setGroupName(rs.getString("group_name"));
            return s;
        }
    }

}
