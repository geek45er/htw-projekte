package org.htw.prog2.lfa;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// hauptfenster, laedt config und bild und zeigt das ergebnis an
public class MainFrame extends JFrame {
    private ImagePanel imagePanel;
    private Configuration config;

    private JLabel intensityRatioLabel;
    private JLabel ratioCutoffLabel;
    private JLabel resultLabel;
    private JCheckBox correctionCheckbox;

    public MainFrame() {
        super("LFA-Analyse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initMenu();
        initImagePanel();
        initInfoPanel();

        pack();
        setLocationRelativeTo(null);

        aktualisiereErgebnis();
    }

    // baut die menueleiste mit den drei eintraegen
    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem loadConfigItem = new JMenuItem("Load configuration");
        loadConfigItem.addActionListener(e -> ladeKonfiguration());

        JMenuItem loadImageItem = new JMenuItem("Load LFA image");
        loadImageItem.addActionListener(e -> ladeBild());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(loadConfigItem);
        fileMenu.add(loadImageItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
    }

    private void initImagePanel() {
        imagePanel = new ImagePanel();
        add(imagePanel, BorderLayout.CENTER);
    }

    // panel mit den ergebnis-labels und der checkbox links im fenster
    private void initInfoPanel() {
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridy = 0;
        intensityRatioLabel = new JLabel("Intensity ratio: N/A");
        infoPanel.add(intensityRatioLabel, c);

        c.gridy = 1;
        ratioCutoffLabel = new JLabel("Ratio cutoff: N/A");
        infoPanel.add(ratioCutoffLabel, c);

        c.gridy = 2;
        resultLabel = new JLabel("Result: N/A");
        infoPanel.add(resultLabel, c);

        c.gridy = 3;
        correctionCheckbox = new JCheckBox("Background correction?");
        correctionCheckbox.addActionListener(e -> {
            if (config != null) {
                config.setBorderEnabled(correctionCheckbox.isSelected());
            }
            imagePanel.repaint();
            aktualisiereErgebnis();
        });
        infoPanel.add(correctionCheckbox, c);

        add(infoPanel, BorderLayout.WEST);
    }

    // zeigt den dateidialog zum laden der konfigurationsdatei an
    private void ladeKonfiguration() {
        JFileChooser chooser = new JFileChooser();
        int auswahl = chooser.showOpenDialog(this);
        if (auswahl == JFileChooser.APPROVE_OPTION) {
            try {
                config = new Configuration(chooser.getSelectedFile());
                config.setBorderEnabled(correctionCheckbox.isSelected());
                imagePanel.setConfig(config);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error reading config file", JOptionPane.ERROR_MESSAGE);
            } catch (FormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error reading config file", JOptionPane.ERROR_MESSAGE);
            }
        }
        aktualisiereErgebnis();
    }

    // zeigt den dateidialog zum laden eines lfa bildes an
    private void ladeBild() {
        JFileChooser chooser = new JFileChooser();
        int auswahl = chooser.showOpenDialog(this);
        if (auswahl == JFileChooser.APPROVE_OPTION) {
            try {
                LFAImage image = new LFAImage(chooser.getSelectedFile());
                imagePanel.setImage(image);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error reading image file", JOptionPane.ERROR_MESSAGE);
            }
        }
        aktualisiereErgebnis();
    }

    // rechnet das ergebnis neu aus und aktualisiert die labels
    private void aktualisiereErgebnis() {
        if (config != null) {
            ratioCutoffLabel.setText("Ratio cutoff: " + config.getRatio());
        } else {
            ratioCutoffLabel.setText("Ratio cutoff: N/A");
        }

        LFAImage image = imagePanel.getImage();
        if (config != null && image != null) {
            double controlIntensitaet;
            double testIntensitaet;

            if (config.isBorderEnabled()) {
                controlIntensitaet = image.getAverageIntensityCorrected(config.getControlCoordinates(), config.getBorderWidth());
                testIntensitaet = image.getAverageIntensityCorrected(config.getTestCoordinates(), config.getBorderWidth());
            } else {
                controlIntensitaet = image.getAverageIntensity(config.getControlCoordinates());
                testIntensitaet = image.getAverageIntensity(config.getTestCoordinates());
            }

            double intensityRatio = testIntensitaet / controlIntensitaet;
            intensityRatioLabel.setText("Intensity ratio: " + String.format("%.2f", intensityRatio));

            if (intensityRatio > config.getRatio()) {
                resultLabel.setText("Result: Positive");
                resultLabel.setForeground(Color.GREEN.darker());
            } else {
                resultLabel.setText("Result: Negative");
                resultLabel.setForeground(Color.BLACK);
            }
        } else {
            intensityRatioLabel.setText("Intensity ratio: N/A");
            resultLabel.setText("Result: N/A");
            resultLabel.setForeground(Color.BLACK);
        }
    }
}
