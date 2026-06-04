package model;

import java.util.List;

public record RollResult(int faces, List<Integer> rolls, int bonus) {

    public int subtotal() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    public int total() {
        return subtotal() + bonus;
    }
}
