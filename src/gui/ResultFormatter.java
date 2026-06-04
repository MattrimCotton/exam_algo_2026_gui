package gui;

import i18n.Messages;
import model.RollResult;

import java.text.MessageFormat;

/** Formate un {@link RollResult} en texte lisible pour la zone de résultats. */
public class ResultFormatter {

    private static final String SEPARATOR = "─".repeat(36);

    public static String format(RollResult result) {
        StringBuilder sb = new StringBuilder();

        sb.append(MessageFormat.format(
                Messages.get("result.intro"), result.rolls().size(), result.faces()))
          .append("\n");

        for (int i = 0; i < result.rolls().size(); i++) {
            sb.append(MessageFormat.format(
                    Messages.get("result.die"), i + 1, result.rolls().get(i)))
              .append("\n");
        }

        sb.append(MessageFormat.format(Messages.get("result.subtotal"), result.subtotal()))
          .append("\n");

        if (result.bonus() != 0) {
            String sign = result.bonus() > 0 ? "+" : "";
            sb.append(MessageFormat.format(
                    Messages.get("result.bonus"), sign + result.bonus()))
              .append("\n");
        }

        sb.append(MessageFormat.format(Messages.get("result.total"), result.total()))
          .append("\n")
          .append(SEPARATOR)
          .append("\n");

        return sb.toString();
    }
}
