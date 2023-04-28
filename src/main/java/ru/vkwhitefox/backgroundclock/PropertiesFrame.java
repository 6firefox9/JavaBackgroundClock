package main.java.ru.vkwhitefox.backgroundclock;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class PropertiesFrame extends JFrame {
    private static final PropertiesFrame PROPERTIES_FRAME = new PropertiesFrame();

    private final Color bgColor = new Color(35,35,35);
    private final Color fgColor = new Color(255,105,0);

    private final String[] langBox = {"English", "Русский", "Deutsch", "Franzosisch"};

    private final URL iconEN = getClass().getResource("/img/formimgs/en.png");
    private final URL iconRU = getClass().getResource("/img/formimgs/ru.png");
    private final URL iconDE = getClass().getResource("/img/formimgs/de.png");
    private final URL iconFR = getClass().getResource("/img/formimgs/fr.png");

    private final ImageIcon[] flags = {
            new ImageIcon(iconEN),
            new ImageIcon(iconRU),
            new ImageIcon(iconDE),
            new ImageIcon(iconFR)
    };
    private final JTextField heightField;
    private final JTextField widthField;
    private final JCheckBox fixedCheckBox;
    private final JComboBox<String> fontMenu;
    private final JTextField clockSizeField;
    private final JTextField dateSizeField;
    private final JTextField clockColorField;
    private final JTextField dateColorField;
    private final JSlider transparencySlider;
    private final JTextField transparencyField;


    private PropertiesFrame(){
        Logger.writeNext("Try to create Properties window...");

        String[] fontsWindows = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeProperties();
            }
        });
        this.setTitle(AppLang.getString("preferences"));
        this.setResizable(false);
        this.getContentPane().setBackground(bgColor);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("img/clocklogo.png"));
        this.setIconImage(icon.getImage());

//add components
        GridLayout formLayout = new GridLayout(3,2,0,0);
        this.setLayout(formLayout);
    //logo
        ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("img/formimgs/header.png"));
        JLabel logoLabel = new JLabel(logo);
        this.add(logoLabel);
    //Language Panel
        JPanel langPanel = new JPanel();
        langPanel.setBackground(bgColor);

        GridLayout langGridLayout = new GridLayout(3,1,0,0);
        langPanel.setLayout(langGridLayout);

        JLabel langLabel = new JLabel("Language / Язык / Sprache / Langue", SwingConstants.CENTER);
        langLabel.setBackground(bgColor);
        langLabel.setForeground(fgColor);
        langPanel.add(langLabel);

        JPanel langBoxPanel = new JPanel();
        langBoxPanel.setBackground(bgColor);
        langBoxPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbcl = new GridBagConstraints();

        JLabel countryLogo = new JLabel(flags[0]);
        gbcl.fill = GridBagConstraints.HORIZONTAL;
        gbcl.weightx = 0.2;
        langBoxPanel.add(countryLogo, gbcl);

        JComboBox<String> langMenu = new JComboBox<>(langBox);
        langMenu.setSelectedItem(selectLanguage(Options.language));
        countryLogo.setIcon(flags[langMenu.getSelectedIndex()]);
        langMenu.setBackground(bgColor);
        langMenu.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setHorizontalAlignment(DefaultListCellRenderer.CENTER);
                setBackground(bgColor);
                setForeground(fgColor);
                setEnabled(false);
                super.paint(g);
            }
        });
        gbcl.insets = new Insets(0,0,0,5);
        gbcl.weightx = 0.8;
        langBoxPanel.add(langMenu, gbcl);
        langPanel.add(langBoxPanel);

        this.add(langPanel);
    //Position panel
        JPanel posPanel = new JPanel();
        TitledBorder posBorder = new TitledBorder(AppLang.getString("position"));
        posBorder.setTitleColor(fgColor);
        posPanel.setBorder(posBorder);
        posPanel.setBackground(bgColor);

        GridBagConstraints gbcp = new GridBagConstraints();
        GridLayout posGridLayout = new GridLayout(3,1,0,5);
        posPanel.setLayout(posGridLayout);

            JPanel heightPanel = new JPanel();
            heightPanel.setBackground(bgColor);
            heightPanel.setLayout(new GridBagLayout());
                JLabel heightLabel = new JLabel(AppLang.getString("height"));
                heightLabel.setForeground(fgColor);
                gbcp.fill = GridBagConstraints.HORIZONTAL;
                gbcp.insets = new Insets(0,5,0,5);
                    heightPanel.add(heightLabel, gbcp);
                heightField = new JTextField();
                heightField.setTransferHandler(null);
                heightField.setText(String.valueOf(Options.height));
                heightField.setHorizontalAlignment(SwingConstants.CENTER);
                gbcp.insets = new Insets(0,0,0,5);
                gbcp.weightx = 0.8;
                    heightPanel.add(heightField, gbcp);
            posPanel.add(heightPanel, gbcp);

            JPanel widthPanel = new JPanel();
            widthPanel.setBackground(bgColor);
            widthPanel.setLayout(new GridBagLayout());
                JLabel widthLabel = new JLabel(AppLang.getString("width"));
                widthLabel.setForeground(fgColor);
                gbcp.insets = new Insets(0,5,0,5);
                gbcp.weightx = 0.0;
                    widthPanel.add(widthLabel, gbcp);
                widthField = new JTextField();
                widthField.setTransferHandler(null);
                widthField.setText(String.valueOf(Options.width));
                widthField.setHorizontalAlignment(SwingConstants.CENTER);
                gbcp.insets = new Insets(0,0,0,5);
                gbcp.weightx = 0.8;
                    widthPanel.add(widthField, gbcp);
            posPanel.add(widthPanel, gbcp);

            fixedCheckBox = new JCheckBox(AppLang.getString("fixed"), true);
            fixedCheckBox.setBackground(bgColor);
            fixedCheckBox.setForeground(fgColor);
            fixedCheckBox.setHorizontalAlignment(SwingConstants.HORIZONTAL);
            posPanel.add(fixedCheckBox);

        this.add(posPanel);
    //Font Panel
        JPanel fontPanel = new JPanel();
        TitledBorder fontBorder = new TitledBorder(AppLang.getString("font"));
        fontBorder.setTitleColor(fgColor);
        fontPanel.setBorder(fontBorder);
        fontPanel.setBackground(bgColor);

        GridBagConstraints gbcf = new GridBagConstraints();
        GridLayout fontGridLayout = new GridLayout(3,1,0,5);
        fontPanel.setLayout(fontGridLayout);

            JPanel fontTypePanel = new JPanel();
            fontTypePanel.setBackground(bgColor);
            fontTypePanel.setLayout(new GridBagLayout());
                fontMenu = new JComboBox<>(fontsWindows);
                fontMenu.setSelectedItem(Options.fontType);
                fontMenu.setBackground(bgColor);
                fontMenu.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public void paint(Graphics g) {
                        setBackground(bgColor);
                        setForeground(fgColor);
                        setEnabled(false);
                        super.paint(g);
                    }
                });
                gbcf.fill = GridBagConstraints.HORIZONTAL;
                gbcf.insets = new Insets(0,5,0,5);
                gbcf.weightx = 1.0;
                    fontTypePanel.add(fontMenu, gbcf);
            fontPanel.add(fontTypePanel);

            JPanel timeSizePanel = new JPanel();
            timeSizePanel.setBackground(bgColor);
            timeSizePanel.setLayout(new GridBagLayout());
                JLabel clockSize = new JLabel(AppLang.getString("clockSize"));
                clockSize.setBackground(bgColor);
                clockSize.setForeground(fgColor);
                gbcf.insets = new Insets(0,5,0,5);
                gbcf.weightx = 0.0;
                gbcf.weighty = 1.0;
                    timeSizePanel.add(clockSize, gbcf);
                clockSizeField = new JTextField();
                clockSizeField.setTransferHandler(null);
                clockSizeField.setText(String.valueOf(Options.fontSizeClock));
                clockSizeField.setHorizontalAlignment(SwingConstants.CENTER);
                gbcf.insets = new Insets(0,0,0,5);
                gbcf.weightx = 0.9;
                    timeSizePanel.add(clockSizeField, gbcf);
            fontPanel.add(timeSizePanel);

            JPanel dateSizePanel = new JPanel();
            dateSizePanel.setBackground(bgColor);
            dateSizePanel.setLayout(new GridBagLayout());
                JLabel dateSize = new JLabel(AppLang.getString("dateSize"));
                dateSize.setBackground(bgColor);
                dateSize.setForeground(fgColor);
                gbcf.insets = new Insets(0,5,0,5);
                gbcf.weightx = 0.0;
                    dateSizePanel.add(dateSize, gbcf);
                dateSizeField = new JTextField();
                dateSizeField.setTransferHandler(null);
                dateSizeField.setText(String.valueOf(Options.fontSizeDate));
                dateSizeField.setHorizontalAlignment(SwingConstants.CENTER);
                gbcf.insets = new Insets(0,0,0,5);
                gbcf.weightx = 0.3;
                    dateSizePanel.add(dateSizeField, gbcf);
            fontPanel.add(dateSizePanel);

        this.add(fontPanel);
    //Color Panel
        JPanel colorPanel = new JPanel();
        TitledBorder colorBorder = new TitledBorder(AppLang.getString("colorTransparency"));
        colorBorder.setTitleColor(fgColor);
        colorPanel.setBorder(colorBorder);
        colorPanel.setBackground(bgColor);

        GridBagConstraints gbcc = new GridBagConstraints();
        GridLayout colorGridLayout = new GridLayout(3,1,0,5);
        colorPanel.setLayout(colorGridLayout);

            JPanel timeColorPanel = new JPanel();
            timeColorPanel.setBackground(bgColor);
            timeColorPanel.setLayout(new GridBagLayout());
                JLabel clockColorLabel = new JLabel(AppLang.getString("clockColor"));
                clockColorLabel.setBackground(bgColor);
                clockColorLabel.setForeground(fgColor);
                gbcc.fill = GridBagConstraints.HORIZONTAL;
                gbcc.insets = new Insets(0,5,0,5);
                gbcc.weightx = 0.0;
                    timeColorPanel.add(clockColorLabel, gbcc);
                clockColorField = new JTextField("#" + Integer.toHexString(Options.clockColor.getRGB()).substring(2));
                clockColorField.setTransferHandler(null);
                clockColorField.setHorizontalAlignment(SwingConstants.CENTER);
                clockColorField.setBackground(fgColor);
                clockColorField.setForeground(bgColor);
                gbcc.weightx = 0.5;
                    timeColorPanel.add(clockColorField, gbcc);
                JButton clockColorButton = new JButton("...");
                clockColorButton.setBackground(bgColor);
                clockColorButton.setForeground(fgColor);
                clockColorButton.setPreferredSize(new Dimension(24, 20));
                clockColorButton.setHorizontalTextPosition(SwingConstants.CENTER);
                gbcc.insets = new Insets(0,0,0,5);
                gbcc.weightx = 0.0;
                    timeColorPanel.add(clockColorButton, gbcc);
            colorPanel.add(timeColorPanel);

            JPanel dateColorPanel = new JPanel();
            dateColorPanel.setBackground(bgColor);
            dateColorPanel.setLayout(new GridBagLayout());
                JLabel dateColorLabel = new JLabel(AppLang.getString("dateColor"));
                dateColorLabel.setBackground(bgColor);
                dateColorLabel.setForeground(fgColor);
                gbcc.fill = GridBagConstraints.HORIZONTAL;
                gbcc.insets = new Insets(0,5,0,5);
                gbcc.weightx = 0.0;
                    dateColorPanel.add(dateColorLabel, gbcc);
                dateColorField = new JTextField("#" + Integer.toHexString(Options.dateColor.getRGB()).substring(2));
                dateColorField.setTransferHandler(null);
                dateColorField.setHorizontalAlignment(SwingConstants.CENTER);
                dateColorField.setBackground(fgColor);
                dateColorField.setForeground(bgColor);
                gbcc.weightx = 0.5;
                    dateColorPanel.add(dateColorField, gbcc);
                JButton dateColorButton = new JButton("...");
                dateColorButton.setBackground(bgColor);
                dateColorButton.setForeground(fgColor);
                dateColorButton.setPreferredSize(new Dimension(24, 20));
                dateColorButton.setHorizontalTextPosition(SwingConstants.CENTER);
                gbcc.insets = new Insets(0,0,0,5);
                gbcc.weightx = 0.0;
                    dateColorPanel.add(dateColorButton, gbcc);
            colorPanel.add(dateColorPanel);

            JPanel transparencyPanel = new JPanel();
            transparencyPanel.setBackground(bgColor);
            transparencyPanel.setLayout(new GridBagLayout());
                JLabel transparencyLabel = new JLabel(AppLang.getString("transparency"));
                transparencyLabel.setBackground(bgColor);
                transparencyLabel.setForeground(fgColor);
                gbcc.fill = GridBagConstraints.HORIZONTAL;
                gbcc.insets = new Insets(0,5,0,5);
                gbcc.weightx = 0.0;
                    transparencyPanel.add(transparencyLabel, gbcc);
                transparencySlider = new JSlider(0, 255);
                transparencySlider.setValue(Options.alpha);
                transparencySlider.setPreferredSize(new Dimension(60, 20));
                transparencySlider.setBackground(bgColor);
                gbcc.insets = new Insets(0,0,0,5);
                gbcc.weightx = 0.2;
                    transparencyPanel.add(transparencySlider, gbcc);
                transparencyField = new JTextField(String.valueOf(Options.alpha));
                transparencyField.setTransferHandler(null);
                transparencyField.setHorizontalAlignment(SwingConstants.CENTER);
                gbcc.fill = GridBagConstraints.HORIZONTAL;
                gbcc.weightx = 0.2;
                    transparencyPanel.add(transparencyField, gbcc);
            colorPanel.add(transparencyPanel);

        this.add(colorPanel);
    //Save Panel
        JPanel savePanel = new JPanel();
        TitledBorder savePanelBorder = new TitledBorder(AppLang.getString("applyDiscard"));
        savePanel.setBorder(savePanelBorder);
        savePanel.setBackground(bgColor);
        savePanelBorder.setTitleColor(fgColor);

        GridLayout saveGridLayout = new GridLayout(1,1,5,20);
        savePanel.setLayout(saveGridLayout);

        GridBagConstraints gbcs = new GridBagConstraints();
        gbcs.fill = GridBagConstraints.HORIZONTAL;
        gbcs.insets = new Insets(5,5,5,5);

        JPanel saveButtonsPanel = new JPanel();
        saveButtonsPanel.setBackground(bgColor);
        saveButtonsPanel.setLayout(new GridBagLayout());

        JButton updateButton = new JButton(AppLang.getString("update"));
        gbcs.gridy = 0;
        saveButtonsPanel.add(updateButton, gbcs);

        JButton resetButton = new JButton(AppLang.getString("reset"));
        saveButtonsPanel.add(resetButton, gbcs);

        JButton saveButton = new JButton(AppLang.getString("save"));
        gbcs.gridy = 1;
        saveButtonsPanel.add(saveButton, gbcs);

        JButton closeButton = new JButton(AppLang.getString("close"));
        saveButtonsPanel.add(closeButton, gbcs);
        savePanel.add(saveButtonsPanel, gbcs);

        this.add(savePanel);
    //action listeners
        langMenu.addActionListener(e -> {
            switch (langMenu.getSelectedIndex()){
                case 0:
                    Options.language = AppLang.EN;
                    break;
                case 1:
                    Options.language = AppLang.RU;
                    break;
                case 2:
                    Options.language = AppLang.DE;
                    break;
                case 3:
                    Options.language = AppLang.FR;
                    break;
            }
            AppLang.setLanguage();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    countryLogo.setIcon(flags[langMenu.getSelectedIndex()]);
                    posBorder.setTitle(AppLang.getString("position"));
                    heightLabel.setText(AppLang.getString("height"));
                    widthLabel.setText(AppLang.getString("width"));
                    fixedCheckBox.setText(AppLang.getString("fixed"));
                    fontBorder.setTitle(AppLang.getString("font"));
                    clockSize.setText(AppLang.getString("clockSize"));
                    dateSize.setText(AppLang.getString("dateSize"));
                    colorBorder.setTitle(AppLang.getString("colorTransparency"));
                    clockColorLabel.setText(AppLang.getString("clockColor"));
                    dateColorLabel.setText(AppLang.getString("dateColor"));
                    transparencyLabel.setText(AppLang.getString("transparency"));
                    savePanelBorder.setTitle(AppLang.getString("applyDiscard"));
                    updateButton.setText(AppLang.getString("update"));
                    resetButton.setText(AppLang.getString("reset"));
                    saveButton.setText(AppLang.getString("save"));
                    closeButton.setText(AppLang.getString("close"));
                    repaint();
                    TrayIcon[] trayIcons = SystemTray.getSystemTray().getTrayIcons();
                    for (TrayIcon itr : trayIcons){
                        SystemTray.getSystemTray().remove(itr);
                    }
                    TrayMenu.init();
                }
            });
        });

        fixedCheckBox.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            if (fixedCheckBox.isSelected()){
                ClockFrame.setFixed(true);
                heightField.setEnabled(true);
                heightField.setText(String.valueOf(ClockFrame.getClockHeight()));
                widthField.setEnabled(true);
                widthField.setText(String.valueOf(ClockFrame.getClockWidth()));
            } else {
                ClockFrame.setFixed(false);
                heightField.setEnabled(false);
                heightField.setText(String.valueOf(ClockFrame.getClockHeight() + 31));
                widthField.setEnabled(false);
                widthField.setText(String.valueOf(ClockFrame.getClockWidth() + 8));
            }
        }));

        clockColorButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            Color pickedColor = JColorChooser.showDialog(null, AppLang.getString("clockColor"), Color.BLACK);
            if (pickedColor != null) clockColorField.setText("#" + Integer.toHexString(pickedColor.getRGB()).substring(2));
        }));

        dateColorButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            Color pickedColor = JColorChooser.showDialog(null, AppLang.getString("dateColor"), Color.BLACK);
            if (pickedColor != null) dateColorField.setText("#" + Integer.toHexString(pickedColor.getRGB()).substring(2));
        }));

        transparencySlider.addChangeListener(e -> SwingUtilities.invokeLater(() ->
                transparencyField.setText(String.valueOf(transparencySlider.getValue()))));

        updateButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            try{
                ClockFrame.updateGUI(Integer.parseInt(widthField.getText()),
                        Integer.parseInt(heightField.getText()),
                        new Color(Integer.decode("0x" + clockColorField.getText().substring(1))),
                        new Color(Integer.decode("0x" + dateColorField.getText().substring(1))),
                        Integer.parseInt(transparencyField.getText()),
                        (String) fontMenu.getSelectedItem(),
                        Integer.parseInt(clockSizeField.getText()),
                        Integer.parseInt(dateSizeField.getText()));
            } catch (NumberFormatException | NullPointerException n){
                Logger.writeNext("Error in PropertiesFrame class.\n" +
                        "Incorrect result in action listener update button");
                n.printStackTrace();
                ClockFrame.updateGUI();
            }
        }));

        resetButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            heightField.setText("800");
            widthField.setText("1200");
            fixedCheckBox.setSelected(true);
            fontMenu.setSelectedItem("Verdana");
            clockSizeField.setText("120");
            dateSizeField.setText("24");
            clockColorField.setText("#000000");
            dateColorField.setText("#000000");
            transparencySlider.setValue(128);
            transparencyField.setText("128");
        }));

        saveButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            try {
                Options.height = Integer.parseInt(heightField.getText());
                Options.width = Integer.parseInt(widthField.getText());
                Options.fontType = (String) fontMenu.getSelectedItem();
                Options.fontSizeClock = Integer.parseInt(clockSizeField.getText());
                Options.fontSizeDate = Integer.parseInt(dateSizeField.getText());
                Options.clockColor = new Color(Integer.decode("0x" + clockColorField.getText().substring(1)));
                Options.dateColor = new Color(Integer.decode("0x" + dateColorField.getText().substring(1)));
                Options.alpha = Integer.parseInt(transparencyField.getText());
                Options.saveProperties();
                ClockFrame.updateGUI();
            } catch (NumberFormatException | NullPointerException n){
                Logger.writeNext("Error in PropertiesFrame class.\n" +
                        "Incorrect result in action listener save button");
                n.printStackTrace();
                Options.setDefaults();
            }
        }));

        closeButton.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            heightField.setText(String.valueOf(Options.height));
            widthField.setText(String.valueOf(Options.width));
            fixedCheckBox.setSelected(true);
            fontMenu.setSelectedItem(Options.fontType);
            clockSizeField.setText(String.valueOf(Options.fontSizeClock));
            dateSizeField.setText(String.valueOf(Options.fontSizeDate));
            clockColorField.setText("#" + Integer.toHexString(Options.clockColor.getRGB()).substring(2));
            dateColorField.setText("#" + Integer.toHexString(Options.dateColor.getRGB()).substring(2));
            transparencySlider.setValue(Options.alpha);
            transparencyField.setText(String.valueOf(Options.alpha));
            //PROPERTIES_FRAME.dispose();
            closeProperties();
            ClockFrame.updateGUI();
        }));
//key listeners
        heightField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || heightField.getText().length() > 9) e.consume();
            }
        });

        widthField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || widthField.getText().length() > 9) e.consume();
            }
        });

        clockSizeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || clockSizeField.getText().length() > 9) e.consume();
            }
        });

        dateSizeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || dateSizeField.getText().length() > 9) e.consume();
            }
        });

        clockColorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!clockColorField.getText().startsWith("#")) {
                    clockColorField.setText("#" + clockColorField.getText());
                }
                if ((!Character.isDigit(c) && (c < 'a' || c > 'f'))
                        || clockColorField.getText().length() > 6) e.consume();
            }
        });

        dateColorField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!dateColorField.getText().startsWith("#")) {
                    dateColorField.setText("#" + dateColorField.getText());
                }
                if ((!Character.isDigit(c) && (c < 'a' || c > 'f'))
                        || dateColorField.getText().length() > 6) e.consume();
            }
        });

        transparencyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    if (transparencyField.getText().length() > 0) {
                        transparencySlider.setValue(Integer.parseInt(transparencyField.getText()));
                    } else {
                        transparencyField.setText("0");
                        transparencySlider.setValue(0);
                    }
                } else if (Character.isDigit(c) && transparencyField.getText().length() < 3) {
                    if (Integer.parseInt(transparencyField.getText() + c) < 256){
                        transparencySlider.setValue(Integer.parseInt(transparencyField.getText() + c));
                    } else {
                        transparencyField.setText("255");
                        transparencySlider.setValue(255);
                    }
                } else e.consume();
            }
        });
    //pack and set location on screen
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        Logger.writeNext("Properties frame successfully created");
    }

    private void closeProperties(){
        if (Options.isFirstLaunch){
            Logger.writeNext("Notification is shown");
            Options.isFirstLaunch = false;
            Options.saveProperties();
            TrayIcon[] trayIcons = SystemTray.getSystemTray().getTrayIcons();
            trayIcons[0].displayMessage(AppLang.getString("notificationTitle"),
                    AppLang.getString("notificationMessage"), TrayIcon.MessageType.INFO);
        }
        this.dispose();
        Logger.writeNext("Close Properties window");
    }

    public static void init(){
        PROPERTIES_FRAME.setVisible(true);
    }

    private String selectLanguage(String value){
        switch (value) {
            case AppLang.RU:
                return langBox[1];
            case AppLang.DE:
                return langBox[2];
            case AppLang.FR:
                return langBox[3];
            default:
                return langBox[0]; //returns english local anyway
        }
    }

}