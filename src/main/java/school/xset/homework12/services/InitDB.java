package school.xset.homework12.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class InitDB {
    LogServiceProducer logService = new LogServiceProducer();
    @Autowired
    private JdbcTemplate jdbc;

    // Создание таблиц и их наполнение
    @PostConstruct
    public void initData() throws IOException {
        List<String> tables = List.of("users", "departments");
        for (String table : tables) {
            String sql = "DROP TABLE IF EXISTS " + table + " CASCADE";
            jdbc.execute(sql);
            logService.writeLog(sql, "200 OK");
        }

        jdbc.execute("""
            CREATE TABLE departments
            (   id SERIAL PRIMARY KEY,
                depart_name VARCHAR(30) NOT NULL)       
                """);
        logService.writeLog("CREATE TABLE departments", "200 OK");
//
        jdbc.execute("""

           CREATE TABLE users
            (
            id SERIAL PRIMARY KEY,
            first_name VARCHAR(30) NOT NULL,
            Last_name VARCHAR(30) NOT NULL,
            birth_day DATE,
            depart_id INTEGER REFERENCES departments (id) ON DELETE CASCADE,
            UNIQUE (first_name, last_name)
            )
                """);
        logService.writeLog("CREATE TABLE users ", "200 OK");

        jdbc.execute("""
            INSERT INTO departments (depart_name) values\s
            ('Бухгалтерия'),
            ('Закупки'),
            ('Продажи');
                """);
        logService.writeLog("INSERT INTO departments", "200 OK");
        jdbc.execute("""

                insert into users (first_name, last_name, birth_day, depart_id)\s
                values
                ('Оксана', 'Мушкина', '2005-01-10', 1),
                ('Герман', 'Пушистиков', '2004-02-12', 1),
                ('Дана','Кошакова', '1999-11-17', 1),
                ('Дарья', 'Фокс', '2000-10-09', 1),
                ('Марианна', 'Петухова', '1963-07-27', 1),
                ('Карина', 'Ежикова', '1992-05-15', 1),
                ('Ангелина', 'Пыжикова', '1998-12-11', 1),
                ('Вениамин', 'Тушканчик', '1999-11-17', 2),
                ('Дмитрий', 'Ондатров', '2004-02-12', 2),
                ('Дарина', 'Мишкина', '1999-11-17', 2),
                ('Борислав', 'Топорков', '2000-01-01', 2),
                ('Марк', 'Петухов', '1963-07-27', 2),
                ('Зинатулла', 'Асланов', '1992-05-15', 2),
                ('Василиса', 'Цыплакович', '1998-12-11', 3),
                ('Эдуард', 'Попугаев', '2004-02-12', 3),
                ('Глафира', 'Коргина', '1999-11-17', 3),
                ('Вероника', 'Паукова', '2000-01-01', 3),
                ('Таисия', 'Чижикова', '1963-07-27', 2),
                ('Карл', 'Сурикатов', '1992-05-15', 2),
                ('Артур', 'Львовский', '1998-12-11', 1)
                """);
        logService.writeLog("INSERT INTO users", "200 OK");

    }
}
