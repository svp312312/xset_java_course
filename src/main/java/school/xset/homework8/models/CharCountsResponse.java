package school.xset.homework8.models;

import lombok.Data;

import java.util.Map;

@Data
public class CharCountsResponse {
    Map<Character, Integer> charCounts;
}
