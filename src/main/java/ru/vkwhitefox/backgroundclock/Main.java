/**
 * 2023 CREATED BY WHITEFOX ^_^
 * PLEASE DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * If you use this code in your project (without GNU General Public License version 2),
 * please indicate the name of the author - "whitefox",
 * or so "the original code is taken from "whitefox background clock",
 * or something else
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *
 * The text above was honestly partially stolen from the Oracle Company License Agreement of the String class
 *
 * Thank you for your attention. See you on http:/www.vkwhitefox.ru
 * Have a nice day and enjoy ^_^ Yours, whitefox ^_^
 */

package main.java.ru.vkwhitefox.backgroundclock;

public class Main {
    public static void main(String[] args) {
        Logger.init();
        Options.init();
        TrayMenu.init();
        ClockFrame.init();
        Logger.writeNext("Clock loop started...");
        //Busy-wait loop
        while (ClockFrame.inProgress){
            ClockFrame.updateValues();
            try{
                Thread.sleep(1000);
            } catch (RuntimeException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Logger.writeNext("Exit from Clock.");
        System.out.println("Good Bye! Have a nice day!");
        System.exit(0);
    }
}
