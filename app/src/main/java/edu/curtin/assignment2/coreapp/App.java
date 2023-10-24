package edu.curtin.assignment2.coreapp;

import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;

public class App {
    static ResourceBundle bundle;
    static Locale locale;

    public static void main(String[] args) {
        List<Event> eventList = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Language lang = new Language();
        bundle = lang.getBundle();
        locale = lang.getLocale();
        FileIO file = new FileIO(args);
        file.parseFile();
        DataLoader loader = new DataLoader(file.getData(), eventList, bundle);
        loader.loadEvents();
        loader.loadPlugins();
        loader.loadScripts();
        Control control = new Control(currentDateTime, eventList, loader.getNotifyList(), bundle, locale);
        control.start();
    }
}
