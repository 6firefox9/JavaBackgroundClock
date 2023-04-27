package main.java.ru.vkwhitefox.backgroundclock;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public final class Options {
    //The main class that collects the settings
    public static String language;
    public static boolean isFirstLaunch;

    public static int height;
    public static int width;

    public static String fontType;
    public static int fontSizeClock;
    public static int fontSizeDate;

    public static Color clockColor;
    public static Color dateColor;
    public static int alpha;

    private static final String pathToFile = System.getProperty("user.home") + "/JavaBackgroundClock/config.properties";

    private static final Properties properties = new Properties();

    private Options(){}

    public static void init() {
        Logger.writeNext("Try to init options from config.properties file...");
        Logger.writeNext("Check for properties file:");
    //try to find properties
        if (new File(pathToFile).exists()){
            Logger.writeNext("File exist!");
        } else {
            Logger.writeNext("File not found! Try to create file...");
            try {
                new File(System.getProperty("user.home") + "/JavaBackgroundClock").mkdirs();
                new File(pathToFile).createNewFile();
                setDefaults();
                saveProperties();
                Logger.writeNext("Properties file successfully created!");
            } catch (IOException e) {
                Logger.writeNext("Error in Options.create() method. Failed to create properties file...");
                Logger.setStatus(Logger.ERROR);
                e.printStackTrace();
            }
        }
    //load properties
        try (InputStream fileReader = new FileInputStream(pathToFile)){
            properties.load(fileReader);

            language = properties.getProperty("language");
            isFirstLaunch = Boolean.parseBoolean(properties.getProperty("isFirstLaunch"));
            height = Integer.parseInt(properties.getProperty("height"));
            width = Integer.parseInt(properties.getProperty("width"));
            fontType = properties.getProperty("fontType");
            fontSizeClock = Integer.parseInt(properties.getProperty("fontSizeClock"));
            fontSizeDate = Integer.parseInt(properties.getProperty("fontSizeDate"));
            clockColor = new Color(Integer.decode(properties.getProperty("clockColor")));
            dateColor = new Color(Integer.decode(properties.getProperty("dateColor")));
            alpha = Integer.parseInt(properties.getProperty("alpha"));

            Logger.writeNext("Successfully init options");
        } catch (NumberFormatException | NullPointerException | IOException n){
            Logger.setStatus(Logger.ERROR);
            Logger.writeNext("Error in Options class. All values set to default");
            setDefaults();
            n.printStackTrace();
        }
    }

    public static void setDefaults(){
        language = "en";
        isFirstLaunch = true;
        height = 10;
        width = 10;
        fontType = "Verdana";
        fontSizeClock = 120;
        fontSizeDate = 24;
        clockColor = new Color(0x000000);
        dateColor = new Color(0xFFFFFF);
        alpha = 128;
    }

    public static void saveProperties(){
        Logger.writeNext("Try to set properties to file...");
        try (OutputStream outputStream = new FileOutputStream(pathToFile)){
            properties.setProperty("language", language);
            properties.setProperty("isFirstLaunch", String.valueOf(isFirstLaunch));
            properties.setProperty("height", String.valueOf(height));
            properties.setProperty("width", String.valueOf(width));
            properties.setProperty("fontType", fontType);
            properties.setProperty("fontSizeClock", String.valueOf(fontSizeClock));
            properties.setProperty("fontSizeDate", String.valueOf(fontSizeDate));
            properties.setProperty("clockColor", "0x" + Integer.toHexString(Options.clockColor.getRGB()).substring(2));
            properties.setProperty("dateColor", "0x" + Integer.toHexString(Options.dateColor.getRGB()).substring(2));
            properties.setProperty("alpha", String.valueOf(alpha));
            properties.store(outputStream, null);
            Logger.writeNext("Successfully save current properties in config.properties");
        } catch (IOException e){
            Logger.setStatus(Logger.ERROR);
            Logger.writeNext("Error in Options.setProperty() method. File config.properties broken or not found");
            e.printStackTrace();
        }
    }

}
