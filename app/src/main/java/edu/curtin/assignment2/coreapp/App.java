package edu.curtin.assignment2.coreapp;

import java.time.*;
import java.util.*;

import edu.curtin.assignment2.pluginapi.PluginAPI;

public class App {
    static String userInput;
    static ResourceBundle bundle;

    public static void main(String[] args) {
        List<Event> eventList = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();
        pluginStart(eventList);
        Control control = new Control(currentDateTime, eventList);
        control.start();
    }

    public static void pluginStart(List<Event> eventList) {

        var pluginList = new ArrayList<PluginAPI>();

        String[] args = { "Assignment Day", "2023-10-17", "01:00:00", "60"};
        pluginList.add(new PluginLoader().loadPlugin("edu.curtin.calplugins.Repeat", args));

        if (!pluginList.isEmpty()) {
            for (PluginAPI customPlugin : pluginList) {
                customPlugin.start();
                eventList.add(new Event(customPlugin.getEventName(), customPlugin.getEventDate(), customPlugin.getEventDuration()));
            }
        }
    }
}
