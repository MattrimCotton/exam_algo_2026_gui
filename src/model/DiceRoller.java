package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceRoller {

    /** Faces disponibles, dans l'ordre d'affichage. */
    public static final int[] AVAILABLE_FACES = {4, 6, 8, 10, 12, 20, 100};

    private static final Random rng = new Random();

    public static RollResult roll(int faces, int count, int bonus) {
        List<Integer> rolls = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            rolls.add(rng.nextInt(faces) + 1);
        }
        return new RollResult(faces, rolls, bonus);
    }
}
