package gui;

import i18n.Messages;
import model.DiceRoller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private final JComboBox<String> dieBox;
    private final JSpinner countSpinner;
    private final JSpinner bonusSpinner;
    private final JTextArea resultsArea;

    public MainWindow() {
        super(Messages.get("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 400));

        // ── Champs partagés entre les panneaux ────────────────────────────────
        String[] dieNames = new String[DiceRoller.AVAILABLE_FACES.length];
        for (int i = 0; i < DiceRoller.AVAILABLE_FACES.length; i++) {
            dieNames[i] = "d" + DiceRoller.AVAILABLE_FACES[i];
        }
        dieBox = new JComboBox<>(dieNames);
        dieBox.setSelectedIndex(1); // d6 par défaut
        dieBox.getAccessibleContext().setAccessibleName(Messages.get("accessible.die"));

        countSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        setSpinnerAccessibleName(countSpinner, Messages.get("accessible.count"));
        setSpinnerWidth(countSpinner, 55);

        bonusSpinner = new JSpinner(new SpinnerNumberModel(0, -999, 999, 1));
        setSpinnerAccessibleName(bonusSpinner, Messages.get("accessible.bonus"));
        setSpinnerWidth(bonusSpinner, 65);

        resultsArea = new JTextArea(14, 42);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        resultsArea.getAccessibleContext().setAccessibleName(Messages.get("accessible.results"));

        // ── Assemblage ────────────────────────────────────────────────────────
        JButton rollButton = new JButton(Messages.get("button.roll"));
        rollButton.setMnemonic(KeyEvent.VK_L);
        rollButton.getAccessibleContext().setAccessibleDescription(Messages.get("accessible.roll"));
        rollButton.addActionListener(e -> roll());

        setLayout(new BorderLayout(4, 4));
        add(buildControlsPanel(rollButton), BorderLayout.NORTH);
        add(buildResultsPanel(),            BorderLayout.CENTER);
        add(buildSouthPanel(),              BorderLayout.SOUTH);

        getRootPane().setDefaultButton(rollButton);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        rollButton.requestFocusInWindow();
    }

    // ── Construction des panneaux ─────────────────────────────────────────────

    private JPanel buildControlsPanel(JButton rollButton) {
        JLabel dieLabel = new JLabel(Messages.get("label.die"));
        dieLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        dieLabel.setLabelFor(dieBox);

        JLabel countLabel = new JLabel(Messages.get("label.count"));
        countLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        countLabel.setLabelFor(spinnerField(countSpinner));

        JLabel bonusLabel = new JLabel(Messages.get("label.bonus"));
        bonusLabel.setDisplayedMnemonic(KeyEvent.VK_B);
        bonusLabel.setLabelFor(spinnerField(bonusSpinner));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        GridBagConstraints c = new GridBagConstraints();
        c.insets  = new Insets(2, 6, 2, 6);
        c.anchor  = GridBagConstraints.WEST;

        // Ligne 0 : labels
        c.gridy = 0;
        c.gridx = 0; panel.add(dieLabel,   c);
        c.gridx = 1; panel.add(countLabel,  c);
        c.gridx = 2; panel.add(bonusLabel,  c);

        // Ligne 1 : champs + bouton Lancer
        c.gridy = 1;
        c.gridx = 0; panel.add(dieBox,        c);
        c.gridx = 1; panel.add(countSpinner,   c);
        c.gridx = 2; panel.add(bonusSpinner,   c);
        c.gridx = 3; c.insets = new Insets(2, 14, 2, 6);
                     panel.add(rollButton,     c);

        return panel;
    }

    private JScrollPane buildResultsPanel() {
        JScrollPane scroll = new JScrollPane(resultsArea);
        scroll.setBorder(BorderFactory.createTitledBorder(Messages.get("result.header")));
        return scroll;
    }

    private JPanel buildSouthPanel() {
        JButton clearButton = new JButton(Messages.get("button.clear"));
        clearButton.setMnemonic(KeyEvent.VK_E);
        clearButton.addActionListener(e -> resultsArea.setText(""));

        JButton quitButton = new JButton(Messages.get("button.quit"));
        quitButton.setMnemonic(KeyEvent.VK_Q);
        quitButton.addActionListener(e ->
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        panel.add(clearButton);
        panel.add(quitButton);
        return panel;
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    private void roll() {
        int faces = DiceRoller.AVAILABLE_FACES[dieBox.getSelectedIndex()];
        int count = (int) countSpinner.getValue();
        int bonus = (int) bonusSpinner.getValue();

        String text = ResultFormatter.format(DiceRoller.roll(faces, count, bonus));
        resultsArea.append(text);
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }

    // ── Utilitaires accessibilité ─────────────────────────────────────────────

    /** Retourne le JTextField interne du spinner — c'est lui que le lecteur d'écran voit. */
    private static JTextField spinnerField(JSpinner spinner) {
        return ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
    }

    /** Propage le nom accessible sur le spinner ET sur son champ interne. */
    private static void setSpinnerAccessibleName(JSpinner spinner, String name) {
        spinner.getAccessibleContext().setAccessibleName(name);
        spinnerField(spinner).getAccessibleContext().setAccessibleName(name);
    }

    // ── Utilitaires layout ────────────────────────────────────────────────────

    private static void setSpinnerWidth(JSpinner spinner, int width) {
        Dimension d = spinner.getPreferredSize();
        spinner.setPreferredSize(new Dimension(width, d.height));
    }
}
