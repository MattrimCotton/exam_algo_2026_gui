package gui;

import i18n.Messages;
import model.DiceRoller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/** Fenêtre principale de l'application — gère la mise en page et les interactions. */
public class MainWindow extends JFrame {

    // ── Champs partagés entre les panneaux ────────────────────────────────────
    private final JComboBox<String> selecteurDe;
    private final JSpinner          spinnerNombre;
    private final JSpinner          spinnerBonus;
    private final JTextArea         zoneResultats;

    public MainWindow() {
        super(Messages.lire("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 400));

        // Sélecteur de type de dé : construit à partir du modèle
        String[] nomsDes = new String[DiceRoller.FACES_DISPONIBLES.length];
        for (int i = 0; i < DiceRoller.FACES_DISPONIBLES.length; i++) {
            nomsDes[i] = "d" + DiceRoller.FACES_DISPONIBLES[i];
        }
        selecteurDe = new JComboBox<>(nomsDes);
        selecteurDe.setSelectedIndex(1); // d6 par défaut
        selecteurDe.getAccessibleContext().setAccessibleName(Messages.lire("accessible.die"));

        // Spinner : nombre de dés (1 à 100)
        spinnerNombre = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        nommerSpinner(spinnerNombre, Messages.lire("accessible.count"));
        largeurSpinner(spinnerNombre, 55);

        // Spinner : bonus / malus (-999 à 999)
        spinnerBonus = new JSpinner(new SpinnerNumberModel(0, -999, 999, 1));
        nommerSpinner(spinnerBonus, Messages.lire("accessible.bonus"));
        largeurSpinner(spinnerBonus, 65);

        // Zone d'affichage des résultats (lecture seule)
        zoneResultats = new JTextArea(14, 42);
        zoneResultats.setEditable(false);
        zoneResultats.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        zoneResultats.getAccessibleContext().setAccessibleName(Messages.lire("accessible.results"));

        // Bouton Lancer : déclenche un lancer de dés
        JButton boutonLancer = new JButton(Messages.lire("button.roll"));
        boutonLancer.setMnemonic(KeyEvent.VK_L);
        boutonLancer.getAccessibleContext().setAccessibleDescription(Messages.lire("accessible.roll"));
        boutonLancer.addActionListener(e -> lancer());

        // Assemblage des trois zones de la fenêtre
        setLayout(new BorderLayout(4, 4));
        add(construirePanneauControles(boutonLancer), BorderLayout.NORTH);
        add(construirePanneauResultats(),              BorderLayout.CENTER);
        add(construirePanneauBas(),                    BorderLayout.SOUTH);

        // La touche Entrée déclenche le lancer depuis n'importe quel champ
        getRootPane().setDefaultButton(boutonLancer);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        boutonLancer.requestFocusInWindow();
    }

    // ── Construction des panneaux ─────────────────────────────────────────────

    /** Panneau haut : sélecteur de dé, spinners, bouton Lancer. */
    private JPanel construirePanneauControles(JButton boutonLancer) {
        JLabel etiquetteDe = new JLabel(Messages.lire("label.die"));
        etiquetteDe.setDisplayedMnemonic(KeyEvent.VK_T);
        etiquetteDe.setLabelFor(selecteurDe);

        JLabel etiquetteNombre = new JLabel(Messages.lire("label.count"));
        etiquetteNombre.setDisplayedMnemonic(KeyEvent.VK_N);
        etiquetteNombre.setLabelFor(champSpinner(spinnerNombre));

        JLabel etiquetteBonus = new JLabel(Messages.lire("label.bonus"));
        etiquetteBonus.setDisplayedMnemonic(KeyEvent.VK_B);
        etiquetteBonus.setLabelFor(champSpinner(spinnerBonus));

        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));

        GridBagConstraints contrainte = new GridBagConstraints();
        contrainte.insets = new Insets(2, 6, 2, 6);
        contrainte.anchor = GridBagConstraints.WEST;

        // Ligne 0 : étiquettes
        contrainte.gridy = 0;
        contrainte.gridx = 0; panneau.add(etiquetteDe,     contrainte);
        contrainte.gridx = 1; panneau.add(etiquetteNombre,  contrainte);
        contrainte.gridx = 2; panneau.add(etiquetteBonus,   contrainte);

        // Ligne 1 : champs de saisie + bouton Lancer
        contrainte.gridy = 1;
        contrainte.gridx = 0; panneau.add(selecteurDe,    contrainte);
        contrainte.gridx = 1; panneau.add(spinnerNombre,   contrainte);
        contrainte.gridx = 2; panneau.add(spinnerBonus,    contrainte);
        contrainte.gridx = 3; contrainte.insets = new Insets(2, 14, 2, 6);
                              panneau.add(boutonLancer,   contrainte);

        return panneau;
    }

    /** Panneau central : zone de résultats avec barre de défilement. */
    private JScrollPane construirePanneauResultats() {
        JScrollPane defilement = new JScrollPane(zoneResultats);
        defilement.setBorder(BorderFactory.createTitledBorder(Messages.lire("result.header")));
        return defilement;
    }

    /** Panneau bas : boutons Effacer et Quitter. */
    private JPanel construirePanneauBas() {
        JButton boutonEffacer = new JButton(Messages.lire("button.clear"));
        boutonEffacer.setMnemonic(KeyEvent.VK_E);
        boutonEffacer.addActionListener(e -> zoneResultats.setText(""));

        JButton boutonQuitter = new JButton(Messages.lire("button.quit"));
        boutonQuitter.setMnemonic(KeyEvent.VK_Q);
        boutonQuitter.addActionListener(e ->
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        JPanel panneau = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        panneau.add(boutonEffacer);
        panneau.add(boutonQuitter);
        return panneau;
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    /** Lit les valeurs saisies, lance les dés et affiche le résultat. */
    private void lancer() {
        int faces  = DiceRoller.FACES_DISPONIBLES[selecteurDe.getSelectedIndex()];
        int nombre = (int) spinnerNombre.getValue();
        int bonus  = (int) spinnerBonus.getValue();

        String texte = ResultFormatter.formater(DiceRoller.lancer(faces, nombre, bonus));
        zoneResultats.append(texte);
        zoneResultats.setCaretPosition(zoneResultats.getDocument().getLength());
    }

    // ── Utilitaires accessibilité ─────────────────────────────────────────────

    /**
     * Retourne le champ texte interne d'un spinner.
     * C'est ce champ que le lecteur d'écran et la plage braille voient réellement.
     */
    private static JTextField champSpinner(JSpinner spinner) {
        return ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
    }

    /**
     * Propage le nom accessible sur le spinner ET sur son champ interne,
     * pour que les lecteurs d'écran annoncent le bon libellé.
     */
    private static void nommerSpinner(JSpinner spinner, String nom) {
        spinner.getAccessibleContext().setAccessibleName(nom);
        champSpinner(spinner).getAccessibleContext().setAccessibleName(nom);
    }

    // ── Utilitaires mise en page ──────────────────────────────────────────────

    /** Fixe la largeur préférée d'un spinner en pixels. */
    private static void largeurSpinner(JSpinner spinner, int largeur) {
        Dimension taille = spinner.getPreferredSize();
        spinner.setPreferredSize(new Dimension(largeur, taille.height));
    }
}
