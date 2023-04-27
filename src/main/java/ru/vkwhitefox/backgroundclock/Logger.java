package main.java.ru.vkwhitefox.backgroundclock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.nio.file.StandardOpenOption.*;

public class Logger {
    //Custom logger class represents tools to create and manage log.txt file
    public static final String OK = "ok";
    public static final String ERROR = "er";
    public static final String NOT_DEFINED = "nd";

    private static final Path pathToLOG = Paths.get(System.getProperty("user.home") + "/JavaBackgroundClock/log.txt");
    private static final Path pathToERR = Paths.get(System.getProperty("user.home") + "/JavaBackgroundClock/error.txt");


    private Logger(){}

    public static void init() {
        System.out.println("Try to create log.txt...");
        if (new File(pathToLOG.toString()).exists()) {
            if (!getStatus().equals(Logger.OK)) {
                copyLogFile();
            }
        } else {
            new File(System.getProperty("user.home") + "/JavaBackgroundClock").mkdirs();
            try {
                new File(pathToLOG.toString()).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.writeNext("Can't create log.txt");
            }
        }
        initLogFile();
    }

    public static void writeNext(String message){
        System.out.println(message);
        byte[] data = (Calendar.getInstance().getTime() + " " + message + "\n").getBytes();
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(pathToLOG, APPEND))) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            System.err.println("Error in Logger class - Logger.writeNext() broken or file not found");
            e.printStackTrace();
        }
    }

    public static void initLogFile(){
        try(FileWriter fileWriter = new FileWriter(pathToLOG.toString())) {
            fileWriter.write("status=" + Logger.NOT_DEFINED +
                            "\nLog file created " + LocalDateTime.now() + "\n");
            System.out.println("Successfully created log.txt");
        } catch (IOException e){
            System.err.println("Error in Logger.createLogFile()");
            e.printStackTrace();
        }
    }

    public static void copyLogFile(){
        try{
            Files.copy(pathToLOG, pathToERR, REPLACE_EXISTING);
            Logger.writeNext("Successfully copy to error.txt");
        } catch (IOException e){
            System.err.println("Error in Logger.copyLogFile()");
            e.printStackTrace();
        }
    }

    public static String getStatus(){
        try(FileReader fileReader = new FileReader(pathToLOG.toString())) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String status = bufferedReader.readLine();
            if (status == null || status.length() <= 7) initLogFile();
            else return status.substring(7);
        } catch (IOException e){
            System.err.println("File not found or broken. Creating new file log.txt");
            initLogFile();
            e.getStackTrace();
        }
        return Logger.ERROR;
    }

    public static void setStatus(String status){
        byte[] data = ("status=" + status).getBytes();
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(pathToLOG, WRITE))) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
