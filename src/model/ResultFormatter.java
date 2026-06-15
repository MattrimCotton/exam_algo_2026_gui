package model;

import i18n.Messages;

import java.text.MessageFormat;

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

        // En-tête numéroté : "══ Lancer n°N ══════..."
        texte.append(entete(numero)).append("\n");

        // Titre : "Lancement de N dF :"
        texte.append(MessageFormat.format(
                Messages.lire("result.intro"), resultat.valeurs().size(), resultat.faces()))
             .append("\n");

        // Détail de chaque dé
        String motifDe = Messages.lire("result.die");
        for (int i = 0; i < resultat.valeurs().size(); i++) {
            texte.append(MessageFormat.format(motifDe, i + 1, resultat.valeurs().get(i)))
                 .append("\n");
        }

        // Sous-total (sans bonus)
        texte.append(MessageFormat.format(Messages.lire("result.subtotal"), resultat.sousTotal()))
             .append("\n");

        // Bonus ou malus, affiché seulement s'il est non nul
        if (resultat.bonus() != 0) {
            String signe = resultat.bonus() > 0 ? "+" : "";
            texte.append(MessageFormat.format(
                    Messages.lire("result.bonus"), signe + resultat.bonus()))
                 .append("\n");
        }

        // Total final
        texte.append(MessageFormat.format(Messages.lire("result.total"), resultat.total()))
             .append("\n")
             .append(SEPARATEUR)
             .append("\n\n");

        return texte.toString();
    }

    /** Construit un en-tête de largeur fixe : "══ Lancer n°N ════..." */
    private static String entete(int numero) {
        String debut = "══ Lancer n°" + numero + " ";
        int reste = LARGEUR - debut.length();
        return debut + "═".repeat(Math.max(0, reste));
    }
}
