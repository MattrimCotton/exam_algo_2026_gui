package i18n;

import java.util.ResourceBundle;

/** Accès centralisé aux chaînes de l'interface (fichiers messages.properties). */
public class Messages {

    private static final ResourceBundle catalogue = ResourceBundle.getBundle("messages");

    /** Retourne la chaîne associée à la {@code clé} dans le catalogue courant. */
    public static String lire(String clé) {
        return catalogue.getString(clé);
    }
}
