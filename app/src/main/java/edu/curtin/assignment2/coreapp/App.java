package edu.curtin.assignment2.coreapp;

import java.io.IOException;
import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;

/* *******************************************************************
* File:       App.java
* Author:     G.G.T.Shashen
* Created:    11/10/2023
* Modified:   25/10/2023
* Desc:       Main class to start the calendar application
***********************************************************************/
public class App {

    public static void main(String[] args) {
        // declare the main resource bundles and locale
        ResourceBundle bundle;
        Locale locale;
        if (args.length > 0) {
            List<Event> eventList = new ArrayList<>();
            LocalDateTime currentDateTime = LocalDateTime.now();
            // get and set the resource bundle and locales from the language menu
            Language lang = new Language();
            lang.languageMenu();
            lang.setLocale(lang.getLang());
            bundle = lang.getBundle();
            locale = lang.getLocale();
            try {
                // try parsing the input file
                FileIO file = new FileIO(args);
                file.parseFile();
                // load the parsed information
                DataLoader loader = new DataLoader(file.getData(), eventList, bundle, locale);
                loader.loadEvents();
                loader.loadPlugins();
                loader.loadScripts();
                // start the calendar controlling from the loaded data
                Control control = new Control(currentDateTime, eventList, loader.getNotifyList(), bundle, locale);
                control.start();
            } catch (ParseException | IOException | ReflectiveOperationException e) {
                System.out.println("Failed to parse input file! : " + e.getLocalizedMessage());
            }
        } else {
            usage();
        }

    }

    /* show the user the usage if input file is not found in the arguments */
    public static void usage() {
        System.out.println("\n\nUsage: ./gradlew run --args=\"../filename.utf_value.cal\"\n\n");
        System.out.println("Example: ./gradlew run --args=\"../filename.utf8.cal\"\n\n");

    }
}
