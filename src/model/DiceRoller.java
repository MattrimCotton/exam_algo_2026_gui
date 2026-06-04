package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRoller {

    private static final Random rng = new Random();

    public static RollResult roll(int faces, int count, int bonus) {
        List<Integer> rolls = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            rolls.add(rng.nextInt(faces) + 1);
        }
        return new RollResult(faces, rolls, bonus);
    }
}
