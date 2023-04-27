package main.java.ru.vkwhitefox.backgroundclock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockFrame extends JFrame {
    //The basic graphic class what represents transparent frame with clock
    public static volatile boolean inProgress = true;

    private static final ClockFrame CLOCK_FRAME = new ClockFrame();

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d MMMM y");
    private final JLabel timeLabel;
    private final JLabel dateLabel;

    private final URL iconURL = getClass().getResource("/img/clocklogo.png");

    private ClockFrame(){
        Logger.writeNext("Try to get instance of clock frame...");

        //set clock frame
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("Clock");
        this.setResizable(false);
        this.setUndecorated(true);
        this.setFocusable(false);
        this.setFocusableWindowState(true);
        this.setLocation(Options.width, Options.height);
        this.setBackground(new Color(0, 0, 0, 0));
        ImageIcon image = new ImageIcon(iconURL);
        this.setIconImage(image.getImage());
        this.setLayout(null);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                onExit();
            }
        });

        timeLabel = new JLabel("00:00:00", SwingConstants.RIGHT);
        timeLabel.setFont(new Font(Options.fontType, Font.PLAIN, Options.fontSizeClock));
        timeLabel.setForeground(new Color(Options.clockColor.getRed(), Options.clockColor.getGreen(),
                Options.clockColor.getBlue(), Options.alpha));

        dateLabel = new JLabel("EEEEEEEEEEE DD MMMMMMMM YYYY", SwingConstants.RIGHT);
        dateLabel.setFont(new Font(Options.fontType, Font.PLAIN, Options.fontSizeDate));
        dateLabel.setForeground(new Color(Options.dateColor.getRed(), Options.dateColor.getGreen(),
                Options.dateColor.getBlue(), Options.alpha));
        dateLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        this.add(timeLabel);
        this.add(dateLabel);

        resolveSize();

        Logger.writeNext("Clock frame successfully created");
    }

    public static void updateValues(){
        SwingUtilities.invokeLater(() -> {
            CLOCK_FRAME.timeLabel.setText(timeFormat.format(Calendar.getInstance().getTime()));
            CLOCK_FRAME.dateLabel.setText(dateFormat.format(Calendar.getInstance().getTime()));
        });
    }

    public static void updateGUI(){
        CLOCK_FRAME.setLocation(Options.width, Options.height);
        CLOCK_FRAME.timeLabel.setForeground(new Color(Options.clockColor.getRed(), Options.clockColor.getGreen(),
                Options.clockColor.getBlue(), Options.alpha));
        CLOCK_FRAME.timeLabel.setFont(new Font(Options.fontType, Font.PLAIN, Options.fontSizeClock));
        CLOCK_FRAME.dateLabel.setForeground(new Color(Options.dateColor.getRed(), Options.dateColor.getGreen(),
                Options.dateColor.getBlue(), Options.alpha));
        CLOCK_FRAME.dateLabel.setFont(new Font(Options.fontType, Font.PLAIN, Options.fontSizeDate));
        CLOCK_FRAME.resolveSize();
        CLOCK_FRAME.repaint();
    }

    public static void updateGUI(int width, int height, Color clockColor, Color dateColor, int alpha,
                                 String fontType, int fontSizeClock, int fontSizeDate){
        CLOCK_FRAME.setLocation(width, height);
        CLOCK_FRAME.timeLabel.setForeground(new Color(clockColor.getRed(), clockColor.getGreen(),
                clockColor.getBlue(), alpha));
        CLOCK_FRAME.timeLabel.setFont(new Font(fontType, Font.PLAIN, fontSizeClock));
        CLOCK_FRAME.dateLabel.setForeground(new Color(dateColor.getRed(), dateColor.getGreen(),
                dateColor.getBlue(), alpha));
        CLOCK_FRAME.dateLabel.setFont(new Font(fontType, Font.PLAIN, fontSizeDate));
        CLOCK_FRAME.resolveSize();
        CLOCK_FRAME.repaint();
    }

    public static void setFixed(boolean value){
        if (value){
            CLOCK_FRAME.dispose();
            CLOCK_FRAME.setUndecorated(true);
            CLOCK_FRAME.setBackground(new Color(0,0,0,0));
            CLOCK_FRAME.setLocation(CLOCK_FRAME.getX() + 8, CLOCK_FRAME.getY() + 31);
            CLOCK_FRAME.resolveSize();
            CLOCK_FRAME.setVisible(true);
            CLOCK_FRAME.repaint();
        } else {
            CLOCK_FRAME.dispose();
            CLOCK_FRAME.setBackground(null);
            CLOCK_FRAME.setUndecorated(false);
            CLOCK_FRAME.setLocation(CLOCK_FRAME.getX() - 8, CLOCK_FRAME.getY() - 31);
            CLOCK_FRAME.resolveSize();
            CLOCK_FRAME.setSize(CLOCK_FRAME.getWidth() + 20, CLOCK_FRAME.getHeight() + 40);
            CLOCK_FRAME.setVisible(true);
            CLOCK_FRAME.repaint();
        }
    }

    public static int getClockHeight() {
        return CLOCK_FRAME.getY();
    }

    public static int getClockWidth() {
        return CLOCK_FRAME.getX();
    }

    private void onExit() {
        if (!Logger.getStatus().equals(Logger.ERROR)) Logger.setStatus(Logger.OK);
        System.err.println("Exit from app thru clock frame");
        TrayMenu.trayClose();
        inProgress = false;
    }

    public static void init() {
        updateValues();
        CLOCK_FRAME.setVisible(true);
    }

    public void resolveSize(){
        int frameMaxWidth = Math.max(this.timeLabel.getMaximumSize().width, this.dateLabel.getMaximumSize().width);
        this.timeLabel.setBounds(0, 0, frameMaxWidth, this.timeLabel.getMaximumSize().height);
        this.dateLabel.setBounds(0, 0, frameMaxWidth,
                this.timeLabel.getMaximumSize().height + this.dateLabel.getMaximumSize().height -
                        (this.timeLabel.getMaximumSize().height / 6));
        this.setSize(Math.max(this.timeLabel.getWidth(), this.dateLabel.getWidth()),
                Math.max(this.timeLabel.getHeight(), this.dateLabel.getHeight()));
    }

}
