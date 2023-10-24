package edu.curtin.assignment2.coreapp;

import java.io.IOException;
import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;

public class App {

    public static void main(String[] args) {
        ResourceBundle bundle;
        Locale locale;
        if (args.length > 0) {
            List<Event> eventList = new ArrayList<>();
            LocalDateTime currentDateTime = LocalDateTime.now();
            Language lang = new Language();
            lang.languageMenu();
            lang.setLocale(lang.getLang());
            bundle = lang.getBundle();
            locale = lang.getLocale();
            try {
                FileIO file = new FileIO(args);
                file.parseFile();
                DataLoader loader = new DataLoader(file.getData(), eventList, bundle);
                loader.loadEvents();
                loader.loadPlugins();
                loader.loadScripts();
                Control control = new Control(currentDateTime, eventList, loader.getNotifyList(), bundle, locale);
                control.start();
            } catch (ParseException | IOException | ReflectiveOperationException e) {
                System.out.println("Failed to parse input file! : " + e.getLocalizedMessage());
            }
        } else {
            usage();
        }

    }

    public static void usage() {
        System.out.println("\n\nUsage: ./gradlew run --args=\"../filename.utf_value.cal\"\n\n");
        System.out.println("Example: ./gradlew run --args=\"../filename.utf8.cal\"\n\n");

    }
}
