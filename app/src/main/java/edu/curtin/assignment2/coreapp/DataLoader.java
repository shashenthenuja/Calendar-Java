package edu.curtin.assignment2.coreapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import edu.curtin.assignment2.pluginapi.Event;

public class DataLoader {
    private List<Map<String, String>> data;
    private List<LoaderAPI> notifyList = new ArrayList<>();
    private List<Event> eventList;
    private ResourceBundle bundle;

    public DataLoader(List<Map<String, String>> data, List<Event> events, ResourceBundle bundle) {
        this.data = data;
        this.eventList = events;
        this.bundle = bundle;
    }

    public void loadEvents() {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("event")) {
                    if (map.get("duration").equals("0")) {
                        String eventName = map.get("eventName");
                        int duration = 0;
                        LocalDateTime date = setDate(map.get("startDate"));
                        eventList.add(new Event(eventName, date, duration));
                    } else {
                        String eventName = map.get("eventName");
                        int duration = Integer.parseInt(map.get("duration"));
                        LocalDateTime date = setDateTime(map.get("startDate"),
                                map.get("startTime"));
                        eventList.add(new Event(eventName, date, duration));
                    }
                }
            }
        }
    }

    public void loadPlugins() {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("plugin")) {
                    String className = map.get("pluginName");
                    try {
                        LoaderAPI pluginAPI = new LoaderAPI(map, eventList, bundle);
                        if (map.get("pluginName").equals("edu.curtin.calplugins.Notify")) {
                            notifyList.add(pluginAPI);
                        } else {
                            new PluginLoader().loadPlugin(className, pluginAPI);
                            System.out.println(className + " : Plugin Loaded Sucessfully!");
                        }
                    } catch (Exception e) {
                        System.out.println(className + " : Failed to load!");
                    }
                }
            }
        }
    }

    public void loadScripts() {
        if (!data.isEmpty()) {
            for (Map<String, String> map : data) {
                if (map.get("type").equals("script")) {
                    String script = map.get("script");
                    try {
                        ScriptHandler scriptLoad = new ScriptHandler();
                        LoaderAPI api = new LoaderAPI(eventList);
                        scriptLoad.runScript(api, script);
                        System.out.println("Script Loaded Sucessfully!");
                    } catch (Exception e) {
                        System.out.println("Script Failed to load!");
                    }
                }
            }
        }
    }

    public List<LoaderAPI> getNotifyList() {
        return notifyList;
    }

    public LocalDateTime setDate(String date) {
        String dateString = date;
        String timeString = "00:00:00";

        String dateTimeString = dateString + "T" + timeString;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public LocalDateTime setDateTime(String date, String time) {
        String dateString = date;
        String dateTimeString = dateString + "T" + time;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
