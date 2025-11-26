package school.xset.homework12.models;

import lombok.Data;

import java.time.LocalDate;
@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private int departId;
}
