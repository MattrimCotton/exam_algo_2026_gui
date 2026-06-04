package model;

import java.util.List;

/**
 * Résultat d'un lancer de dés.
 *
 * @param faces   nombre de faces du dé utilisé
 * @param valeurs liste des valeurs obtenues à chaque dé
 * @param bonus   bonus (positif) ou malus (négatif) ajouté au total
 */
public record RollResult(int faces, List<Integer> valeurs, int bonus) {

    /** Somme des valeurs obtenues, sans le bonus. */
    public int sousTotale() {
        return valeurs.stream().mapToInt(Integer::intValue).sum();
    }

    /** Total final : sous-totale + bonus. */
    public int total() {
        return sousTotale() + bonus;
    }
}
