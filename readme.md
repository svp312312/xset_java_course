## Содержимое репозитория xset_java_course
В директории [src/main/java/school/xset/](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset) находятся выполненные задания из курса **"Java для тестировщиков с нуля"** ([школа "XSET Technologies"](https://xset.skillspace.ru/school/courses), ноябрь-декабрь 2025 г.) 

### Решённые задачи

✅ [homework1](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework1)  
Задача про двух программистов. Реализованы класс, конструктор, методы, проверка отработки методов в main.
  
✅ [homework2](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework2)    
Реализация контроллера для GET-запросов. Был создан класс с аннотацией `@RestController` для обработки HTTP запросов.
<details><summary>Подробнее...</summary>  
  
  Созданы методы:
  - получение текущего времени;
  - получение текущего месяца и определения сезона;
  - генерация случайной даты в будущем.
    
</details>

✅ [homework3](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework3)  
Задача про коробку со сладостями. 
<details><summary>Подробнее...</summary>
  
Созданы:
  - базовый абстрактный класс для сладостей и конкретные сладости, наследующиеся от базового;
  - интерфейс для коробки сладостей с методами добавления и удаления сладости, вывода веса, стоимости коробки и информации обо всех сладостях;
  - конкретные реализации интерфейса коробки (внутри коробка содержит ArrayList сладостей и работает с ним, вне коробки ArrayList недоступен);
  - методы для умной оптимизации подарка: удаление сладостей с меньшим весом и с меньшей ценой, пока вес коробки не станет меньше входного параметра.
    
</details>

✅ [homework4](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework4)  
Ещё одна задача на `@RestController` для GET-запросов. 

<details><summary>Подробнее...</summary>
  
  Созданы методы:
  - вычисление дня недели по переданной дате (с примером лямбда подобного синтаксиса в switch);
  - для генерации случайного пароля переданной длины;
  - для вычисления факториала числа;
  - для возведения числа в степень;
  - для генерации случайной даты между двумя переданными;
  - для сортировки массива по возрастанию или убыванию (порядок определяется переданным в запросе логическим значением);
  - для разделения строки по позиции и отправки части строки.

    </details>

✅ [homework5](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework5)  
Сравнительный анализ `ArrayList` и `LinkedList`. 

<details><summary>Подробнее...</summary>
  
Исходный размер коллекций - 1 млн. элементов.   
Сранивалось время выполнения операций:
- добавление 500 тысяч элементов в середину списка;
- получение элемента из середины списка;
- удаление элемента из середины списка.

</details>

✅ [homework6](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework6)  
Задача на обработку информации из текстового файла. 

<details><summary>Подробнее...</summary>

Необходимо было:
- cчитать все слова из файла, не учитывая регистр;
- удалить из слов все знаки препинания;
- отсортировать полученные слова в порядке убывания длины, а при равной длине — в алфавитном порядке;
- подсчитать, сколько раз встречается каждое слово, и вывести статистику;
- Найти самое длинное слово в файле и вывести его на экран (если таких несколько — вывести все).

</details>

✅ [homework7](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework7)   
Создание контроллера для POST-запросов. Данные передаются в теле запроса в формате `JSON`.

<details><summary>Подробнее...</summary>

Необходимо было:
- вернуть случайную дату между двумя переданными;
- вернуть отсортированный массив (порядок определяется переданным в запросе логическим значением);
- вернуть частоту символов в переданной строке (отсортировано по убыванию);
- вычислить сумму всех элементов массива;
- вычислить сумму с учетом условия.

</details>

✅ [homework8](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework8)  
Задача на работу с логами. 
Требовалось для приложения из homework7 реализовать отдельный класс с методами записи сообщений в текстовый файл и возвращением всех логов из файла по GET-запросу.

\*\*\* *Примечание*. В данном репозитории нет выполненных домашних заданий homework9 (развертывание элементов архитектуры в Docker-контейнерах и настройка мониторинга) и homework10 (тренинг по созданию SQL-запросов).

✅ [homework11](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework11)      
Задача на создание backend для подключения через JDBC к PostgreSQL (БД "Студенты"). 

<details><summary>Подробнее...</summary>

Реализованы: 
- подключение к БД через `application.properties`;
- создание таблиц и их наполнение через backend, с предварительной чисткой ранее созданных таблиц;
- методы для выполнения запросов к БД (выборка данных, обновление, добавление, удаление записи);
- логирование (каждый запрос к БД записывается в текстовый файл);
- запрос с получением логов из файла за определенный промежуток времени.

</details>  

✅ [homework12](https://github.com/svp312312/xset_java_course/tree/master/src/main/java/school/xset/homework12)  
Финальный проект: разработка сервисов Producer и Consumer для взаимодействия с БД через Kafka-брокер, в соответствии с [заданием](https://github.com/svp312312/xset_java_course/blob/master/src/main/java/school/xset/homework12/homeworkAssignment.txt). 

<table>
  <caption>
    Rest-API сервиса Producer
  </caption> 
  <tr>
     <th>№</th><th>Метод</th><th>Запрос</th><th>Назначение</th>
  </tr>
  <tr>
    <td>1.</td><td>POST</td><td>/createUser</td><td>Создание пользователя</td>
  </tr>
  <tr>
    <td>2.</td><td>POST</td><td>/updateUser</td><td>Обновление пользователя</td>
  </tr>
  <tr>
    <td>3.</td><td>GET</td><td>/getAllUsers</td><td>Получение всех пользователей</td>
  </tr>
  <tr>
    <td>4.</td><td>GET</td><td>/getUserByBirthDate?birthDate={birthDate}</td><td>получение пользователей по дате рождения</td>
  </tr>
  <tr>
    <td>5.</td><td>DELETE</td><td>/deleteUserByLastName?LastName={LastName}</td><td>Удаление пользователей по фамилии</td>
  </tr>
  <tr>
    <td>6.</td><td>DELETE</td><td>/deleteUsers</td><td>Удаление пользователей (truncate)</td>
  </tr>
</table>









