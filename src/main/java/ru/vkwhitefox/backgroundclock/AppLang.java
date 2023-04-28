package main.java.ru.vkwhitefox.backgroundclock;

import java.util.Locale;
import java.util.ResourceBundle;

public class AppLang {
    //The custom locale class represents the tools for managing the language
    public static final String EN = "en";
    public static final String RU = "ru";
    public static final String DE = "de";
    public static final String FR = "fr";

    private static String language = Options.language;
    private static Locale currentLocale = new Locale(language);
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("locale", currentLocale);

    private AppLang() {}

    public static String getString(String name){
        return resourceBundle.getString(name);
    }

    public static void setLanguage(){
        language = Options.language;
        currentLocale = new Locale(language);
        resourceBundle = ResourceBundle.getBundle("locale", currentLocale);
    }

}
