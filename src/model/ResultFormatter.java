package model;

/** Formate un {@link RollResult} en texte lisible pour la zone de résultats. */
public class ResultFormatter {

    private static final int    LARGEUR    = 36;
    private static final String SEPARATEUR = "─".repeat(LARGEUR);

    private ResultFormatter() {}

    /**
     * Produit le bloc de texte affiché après chaque lancer.
     *
     * @param resultat le résultat à afficher
     * @param numero   numéro du lancer dans l'historique (commence à 1)
     * @return le texte formaté, précédé d'un en-tête numéroté et terminé par un séparateur
     */
    public static String formater(RollResult resultat, int numero) {
        StringBuilder texte = new StringBuilder();

        texte.append(entete(numero)).append("\n");
        texte.append("Lancement de ").append(resultat.valeurs().size()).append(" d").append(resultat.faces()).append(" :\n");

        for (int i = 0; i < resultat.valeurs().size(); i++) {
            texte.append("  Dé ").append(i + 1).append(" : ").append(resultat.valeurs().get(i)).append("\n");
        }

        texte.append("Sous-total : ").append(resultat.sousTotal()).append("\n");

        if (resultat.bonus() != 0) {
            String signe = resultat.bonus() > 0 ? "+" : "";
            texte.append("Bonus/Malus : ").append(signe).append(resultat.bonus()).append("\n");
        }

        texte.append("Total final : ").append(resultat.total()).append("\n")
             .append(SEPARATEUR).append("\n\n");

        return texte.toString();
    }

    /** Construit un en-tête de largeur fixe : "══ Lancer n°N ════..." */
    private static String entete(int numero) {
        String debut = "══ Lancer n°" + numero + " ";
        int reste = LARGEUR - debut.length();
        return debut + "═".repeat(Math.max(0, reste));
    }
}
