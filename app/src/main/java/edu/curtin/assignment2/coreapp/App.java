package edu.curtin.assignment2.coreapp;

import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.API;
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
        Control control = new Control(currentDateTime, eventList, bundle, locale);
        pluginStart(eventList);
        Map<String, String> args3 = new HashMap<>();
        args3.put("query", "assi");
        LoaderAPI notify = new LoaderAPI();
        notify.configurePlugin(args3, eventList, bundle);
        control.start(notify);
    }

    public static void pluginStart(List<Event> eventList) {

        var pluginList = new ArrayList<API>();

        Map<String, String> args = new HashMap<>();
        args.put("title", "Assignment Day");
        args.put("startDate", "2023-10-17");
        // args.put("startTime", "01:00:00");
        // args.put("duration", "60");
        args.put("repeat", "3");

        Map<String, String> args2 = new HashMap<>();
        args2.put("title", "New Day");
        args2.put("startDate", "2023-10-21");
        args2.put("startTime", "11:14:00");
        args2.put("duration", "60");
        args2.put("repeat", "0");

        Map<String, String> args3 = new HashMap<>();
        args3.put("query", "assi");

        LoaderAPI pluginAPI = new LoaderAPI();
        pluginAPI.configurePlugin(args, eventList, bundle);

        LoaderAPI pluginAPI2 = new LoaderAPI();
        pluginAPI2.configurePlugin(args2, eventList, bundle);

        pluginList.add(new PluginLoader().loadPlugin("edu.curtin.calplugins.Repeat", pluginAPI));
        pluginList.add(new PluginLoader().loadPlugin("edu.curtin.calplugins.Repeat", pluginAPI2));

    }
}
