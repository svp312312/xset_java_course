package school.xset.homework7.models;

import lombok.Data;

import java.util.List;

@Data

public class NumbersRequest {
    private List<Integer> numbers;
    private Boolean isAsc;
}
