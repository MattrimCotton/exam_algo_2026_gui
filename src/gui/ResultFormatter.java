package gui;

import i18n.Messages;
import model.RollResult;

import java.text.MessageFormat;

/** Formate un {@link RollResult} en texte lisible pour la zone de résultats. */
public class ResultFormatter {

    private static final String SEPARATEUR = "─".repeat(36);

    /**
     * Produit le bloc de texte affiché après chaque lancer.
     *
     * @param resultat le résultat à afficher
     * @return le texte formaté, terminé par un séparateur
     */
    public static String formater(RollResult resultat) {
        StringBuilder texte = new StringBuilder();

        // Titre : "Lancement de N dF :"
        texte.append(MessageFormat.format(
                Messages.lire("result.intro"), resultat.valeurs().size(), resultat.faces()))
             .append("\n");

        // Détail de chaque dé
        for (int i = 0; i < resultat.valeurs().size(); i++) {
            texte.append(MessageFormat.format(
                    Messages.lire("result.die"), i + 1, resultat.valeurs().get(i)))
                 .append("\n");
        }

        // Sous-totale (sans bonus)
        texte.append(MessageFormat.format(Messages.lire("result.subtotal"), resultat.sousTotale()))
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
             .append("\n");

        return texte.toString();
    }
}
