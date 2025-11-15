package school.xset.homework11.models;

import lombok.Data;
import java.time.LocalDate;

@Data

public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String groupName;
}
