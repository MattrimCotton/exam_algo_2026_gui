package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Logique de lancer de dés, sans dépendance à l'interface graphique. */
public class DiceRoller {

    /** Faces disponibles, dans l'ordre d'affichage dans le sélecteur. */
    public static final int[] FACES_DISPONIBLES = {4, 6, 8, 10, 12, 20, 100};

    private static final Random hasard = new Random();

    private DiceRoller() {}

    /**
     * Lance {@code nombre} dés à {@code faces} faces et applique un {@code bonus}.
     *
     * @param faces  nombre de faces du dé (doit être > 0)
     * @param nombre nombre de dés à lancer (doit être >= 1)
     * @param bonus  bonus ou malus à ajouter au total (peut être négatif)
     * @return le résultat complet du lancer
     */
    public static RollResult lancer(int faces, int nombre, int bonus) {
        List<Integer> valeurs = new ArrayList<>(nombre);
        for (int i = 0; i < nombre; i++) {
            valeurs.add(hasard.nextInt(faces) + 1);
        }
        return new RollResult(faces, valeurs, bonus);
    }
}
