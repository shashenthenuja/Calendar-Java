package edu.curtin.assignment2.coreapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;

/* *******************************************************************
* File:       DataLoader.java
* Author:     G.G.T.Shashen
* Created:    24/10/2023
* Modified:   25/10/2023
* Desc:       Class to load the parse data from the input file
***********************************************************************/
public class DataLoader {
    private List<Map<String, String>> data;
    private List<LoaderAPI> notifyList = new ArrayList<>();
    private List<Event> eventList;
    private ResourceBundle bundle;
    private Locale locale;

    public DataLoader(List<Map<String, String>> data, List<Event> events, ResourceBundle bundle, Locale locale) {
        this.data = data;
        this.eventList = events;
        this.bundle = bundle;
        this.locale = locale;
    }

    /* validate and load the events from the input file */
    public void loadEvents() {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("event")) {
                    // add all day events to the list
                    if (map.get("duration").equals("0")) {
                        String eventName = map.get("eventName");
                        int duration = 0;
                        LocalDateTime date = configDate(map.get("startDate"));
                        eventList.add(new Event(eventName, date, duration));
                    } else {
                        String eventName = map.get("eventName");
                        int duration = Integer.parseInt(map.get("duration"));
                        LocalDateTime date = configDateTime(map.get("startDate"),
                                map.get("startTime"));
                        eventList.add(new Event(eventName, date, duration));
                    }
                }
            }
        }
    }

    /* validate and load the plugins from the input file */
    public void loadPlugins() throws ReflectiveOperationException {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("plugin")) {
                    String className = map.get("pluginName");
                    LoaderAPI pluginAPI = new LoaderAPI(map, eventList, bundle, locale);
                    // add notify plugins seperatly to load them in the controller
                    if (map.get("pluginName").equals("edu.curtin.calplugins.Notify")) {
                        notifyList.add(pluginAPI);
                    } else {
                        // load the parsed plugins from the input file
                        new PluginLoader().loadPlugin(className, pluginAPI);
                        System.out.println(className + " : Plugin Loaded Sucessfully!");
                    }
                }
            }
        }
    }

    /* validate and load the scripts from the input file */
    public void loadScripts() throws ReflectiveOperationException {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("script")) {
                    String script = map.get("script");
                    ScriptHandler scriptLoad = new ScriptHandler();
                    LoaderAPI api = new LoaderAPI(eventList);
                    scriptLoad.runScript(api, script);
                    System.out.println("Script Loaded Sucessfully!");
                }
            }
        }
    }

    /* get the notify plugins list to load them in the controller */
    public List<LoaderAPI> getNotifyList() {
        return notifyList;
    }

    /* convert the date from string to local date object */
    public LocalDateTime configDate(String date) {
        String dateString = date;
        String timeString = "00:00:00";

        String dateTimeString = dateString + "T" + timeString;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /* convert the date and time from string to local date time object */
    public LocalDateTime configDateTime(String date, String time) {
        String dateString = date;
        String dateTimeString = dateString + "T" + time;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
