package gui;

import i18n.Messages;
import model.DiceRoller;
import model.RollResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;

public class MainWindow extends JFrame {

    private static final int[] DICE = {4, 6, 8, 10, 12, 20, 100};

    private final JComboBox<String> dieBox;
    private final JSpinner countSpinner;
    private final JSpinner bonusSpinner;
    private final JTextArea resultsArea;

    public MainWindow() {
        super(Messages.get("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(520, 400));

        // ── Contrôles ──────────────────────────────────────────────────────────
        String[] dieNames = new String[DICE.length];
        for (int i = 0; i < DICE.length; i++) dieNames[i] = "d" + DICE[i];

        dieBox = new JComboBox<>(dieNames);
        dieBox.setSelectedIndex(1); // d6 par défaut
        dieBox.getAccessibleContext().setAccessibleName(Messages.get("accessible.die"));

        JLabel dieLabel = new JLabel(Messages.get("label.die"));
        dieLabel.setDisplayedMnemonic(KeyEvent.VK_T);
        dieLabel.setLabelFor(dieBox);

        countSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        countSpinner.getAccessibleContext().setAccessibleName(Messages.get("accessible.count"));
        setSpinnerWidth(countSpinner, 55);

        JLabel countLabel = new JLabel(Messages.get("label.count"));
        countLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        countLabel.setLabelFor(countSpinner);

        bonusSpinner = new JSpinner(new SpinnerNumberModel(0, -999, 999, 1));
        bonusSpinner.getAccessibleContext().setAccessibleName(Messages.get("accessible.bonus"));
        setSpinnerWidth(bonusSpinner, 65);

        JLabel bonusLabel = new JLabel(Messages.get("label.bonus"));
        bonusLabel.setDisplayedMnemonic(KeyEvent.VK_B);
        bonusLabel.setLabelFor(bonusSpinner);

        JButton rollButton = new JButton(Messages.get("button.roll"));
        rollButton.setMnemonic(KeyEvent.VK_L);
        rollButton.getAccessibleContext().setAccessibleDescription(Messages.get("accessible.roll"));
        rollButton.addActionListener(e -> roll());

        // ── Panneau de contrôles ───────────────────────────────────────────────
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        controls.add(dieLabel);
        controls.add(dieBox);
        controls.add(Box.createHorizontalStrut(4));
        controls.add(countLabel);
        controls.add(countSpinner);
        controls.add(Box.createHorizontalStrut(4));
        controls.add(bonusLabel);
        controls.add(bonusSpinner);
        controls.add(Box.createHorizontalStrut(8));
        controls.add(rollButton);

        // ── Zone de résultats ─────────────────────────────────────────────────
        resultsArea = new JTextArea(14, 42);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        resultsArea.getAccessibleContext().setAccessibleName(Messages.get("accessible.results"));

        JScrollPane scroll = new JScrollPane(resultsArea);
        scroll.setBorder(BorderFactory.createTitledBorder(Messages.get("result.header")));

        // ── Barre du bas ──────────────────────────────────────────────────────
        JButton clearButton = new JButton(Messages.get("button.clear"));
        clearButton.setMnemonic(KeyEvent.VK_E);
        clearButton.addActionListener(e -> resultsArea.setText(""));

        JButton quitButton = new JButton(Messages.get("button.quit"));
        quitButton.setMnemonic(KeyEvent.VK_Q);
        quitButton.addActionListener(e -> System.exit(0));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        south.add(clearButton);
        south.add(quitButton);

        // ── Assemblage ────────────────────────────────────────────────────────
        setLayout(new BorderLayout(4, 4));
        add(controls, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        // Entrée déclenche le lancer depuis n'importe quel champ
        getRootPane().setDefaultButton(rollButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        rollButton.requestFocusInWindow();
    }

    private void roll() {
        int faces = DICE[dieBox.getSelectedIndex()];
        int count  = (int) countSpinner.getValue();
        int bonus  = (int) bonusSpinner.getValue();

        RollResult result = DiceRoller.roll(faces, count, bonus);

        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(Messages.get("result.intro"), count, faces)).append("\n");
        for (int i = 0; i < result.rolls().size(); i++) {
            sb.append(MessageFormat.format(Messages.get("result.die"), i + 1, result.rolls().get(i))).append("\n");
        }
        sb.append(MessageFormat.format(Messages.get("result.subtotal"), result.subtotal())).append("\n");
        if (bonus != 0) {
            String sign = bonus > 0 ? "+" : "";
            sb.append(MessageFormat.format(Messages.get("result.bonus"), sign + bonus)).append("\n");
        }
        sb.append(MessageFormat.format(Messages.get("result.total"), result.total())).append("\n");
        sb.append("─".repeat(36)).append("\n");

        resultsArea.append(sb.toString());
        resultsArea.setCaretPosition(resultsArea.getDocument().getLength());
    }

    private static void setSpinnerWidth(JSpinner spinner, int width) {
        Dimension d = spinner.getPreferredSize();
        spinner.setPreferredSize(new Dimension(width, d.height));
    }
}
