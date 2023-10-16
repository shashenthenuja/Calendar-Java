package edu.curtin.calplugins;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.curtin.assignment2.pluginapi.PluginAPI;

public class Repeat implements PluginAPI {

    private String[] args;
    private LocalDateTime eventDate;
    private String eventName;
    private int eventDuration;

    @Override
    public void configurePlugin(String[] args) {
        this.args = args;
    }

    @Override
    public void start() {
        System.out.println("Name: " + args[0]);
        System.out.println("Date: " + args[1]);
        try {
            for (int i = 2; i < args.length; i++) {
                System.out.println("Args : " + args[i]);
            }
        } catch (Exception e) {
        }
        eventName = args[0];

        String dateString = args[1];
        String timeString = "00:00:00";
        int duration = 0;
        try {
            timeString = args[2];
            duration = Integer.parseInt(args[3]);
        } catch (Exception e) {
        }
    
        String dateTimeString = dateString + "T" + timeString;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        eventDate = LocalDateTime.parse(dateTimeString, formatter);
        eventDuration = duration;

    }

    @Override
    public LocalDateTime getEventDate() {
        return eventDate;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public int getEventDuration() {
        return eventDuration;
    }

}
