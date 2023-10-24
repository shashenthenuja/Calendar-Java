package edu.curtin.calplugins;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import edu.curtin.assignment2.pluginapi.PluginAPI;
import edu.curtin.assignment2.pluginapi.API;
import edu.curtin.assignment2.pluginapi.Event;

/* *******************************************************************
* File:       Repeat.java
* Author:     G.G.T.Shashen
* Created:    17/10/2023
* Modified:   25/10/2023
* Desc:       Repeat plugin implementing the main API to create
              repetitive events
***********************************************************************/
public class Repeat implements API {

    private String eventName;
    private LocalDateTime eventDate;
    private int eventDuration;
    private int repeatInterval;

    @Override
    public void start(PluginAPI api) {
        // load and set the data from the api
        setArgs(api.getArgs());
        creatEvent(api.getEvents());
    }

    /* validate and convert the string data to objects to use as Events */
    public void setArgs(Map<String, String> args) {
        eventName = args.get("title");
        eventDate = setDate(args.get("startDate"));
        eventDuration = 0;
        repeatInterval = Integer.parseInt(args.get("repeat"));
        if (args.containsKey("startTime")) {
            eventDate = setDateTime(args.get("startDate"), args.get("startTime"));
        }
        if (args.containsKey("duration")) {
            eventDuration = Integer.parseInt(args.get("duration"));
        }
    }

    /* create repetivite events and add them to the event list */
    public void creatEvent(List<Event> event) {
        if (repeatInterval == 0) {
            event.add(new Event(eventName, eventDate, eventDuration));
        } else {
            event.add(new Event(eventName, eventDate, eventDuration));

            LocalDateTime endDate = eventDate.plusYears(1);

            LocalDateTime currentDate = eventDate.plusDays(repeatInterval);
            while (currentDate.isBefore(endDate)) {
                event.add(new Event(eventName, currentDate, eventDuration));
                currentDate = currentDate.plusDays(repeatInterval);
            }
        }
    }

    /* convert and set date from string to localdatetime object */
    public LocalDateTime setDate(String date) {
        String dateString = date;
        String timeString = "00:00:00";

        String dateTimeString = dateString + "T" + timeString;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /* convert and set date and time from strings to localdatetime object */
    public LocalDateTime setDateTime(String date, String time) {
        String dateString = date;
        String dateTimeString = dateString + "T" + time;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
