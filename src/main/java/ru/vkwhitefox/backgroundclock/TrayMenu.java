package main.java.ru.vkwhitefox.backgroundclock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class TrayMenu{
    //Basic tray class represents instance of MSWindows System Tray

    private static final URL iconURL = TrayMenu.class.getResource("/img/clocktray.png");
    private static final URL logoURL = TrayMenu.class.getResource("/img/aboutLogo.png");

    private TrayMenu(){}

    public static void init(){
        Logger.writeNext("Try to create Tray Menu...");

        if (SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon image = new ImageIcon(iconURL);
            Icon aboutIcon = new ImageIcon(logoURL);
            ActionListener aboutListener = e -> JOptionPane.showMessageDialog(
                    null, AppLang.getString("aboutMessage"),
                    AppLang.getString("about"), JOptionPane.PLAIN_MESSAGE, aboutIcon);
            ActionListener preferencesListener = e -> PropertiesFrame.init();
            ActionListener exitListener = e -> {
                if (!Logger.getStatus().equals(Logger.ERROR)) Logger.setStatus(Logger.OK);
                System.err.println("Exit from app thru tray");
                trayClose();
                ClockFrame.inProgress = false;
            };

            PopupMenu popup = new PopupMenu();
            MenuItem preferencesItem = new MenuItem(AppLang.getString("preferences"));
            preferencesItem.addActionListener(preferencesListener);
            MenuItem aboutItem = new MenuItem(AppLang.getString("about"));
            aboutItem.addActionListener(aboutListener);
            MenuItem exitItem = new MenuItem(AppLang.getString("exit"));
            exitItem.addActionListener(exitListener);
            popup.add(preferencesItem);
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(exitItem);

            TrayIcon trayIcon = new TrayIcon(image.getImage(), AppLang.getString("backgroundClock"), popup);
            trayIcon.setImageAutoSize(true);

            if (Options.isFirstLaunch) PropertiesFrame.init();

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                Logger.writeNext("Error in Tray Menu initialization. TrayIcon could not be added.");
            }
            Logger.writeNext("Tray successfully start");
        } else {
            Logger.writeNext("Tray is not support");
            JOptionPane.showMessageDialog(new JFrame(),
                    AppLang.getString("traySupport"),
                    AppLang.getString("traySupportDialog"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void trayClose(){
        TrayIcon[] trayIcons = SystemTray.getSystemTray().getTrayIcons();
        for (TrayIcon itr : trayIcons){
            SystemTray.getSystemTray().remove(itr);
        }
    }

}
