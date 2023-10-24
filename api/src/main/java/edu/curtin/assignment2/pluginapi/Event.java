package edu.curtin.assignment2.pluginapi;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class Event {
    private String eventName;
    private LocalDateTime eventDate;
    private int eventDuration;

    public Event(String eventName, LocalDateTime eventDate, int eventDuration) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventDuration = eventDuration;
    }

    public String getEventDetails(ResourceBundle bundle, Locale locale) {
        if (eventDuration != 0) {
            DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm:ss a", locale);
            NumberFormat nf = NumberFormat.getInstance(locale);
            try {
                return eventName + "\n " + bundle.getString("ui_time") + ": " + date.format(eventDate.toLocalTime())
                        + "\n "
                        + bundle.getString("ui_duration") + ": " + nf.format(eventDuration) + " "
                        + bundle.getString("ui_mins");
            } catch (Exception e) {
                return eventName + "\n Time: " + date.format(eventDate.toLocalTime()) + "\n Duration: "
                        + nf.format(eventDuration) + " Mins";
            }
        } else {
            return eventName;
        }
    }

    public String getSearchEventDetails(ResourceBundle bundle, Locale locale) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss a", locale);
        NumberFormat nf = NumberFormat.getInstance(locale);
        if (eventDuration != 0) {
            try {
                return bundle.getString("ui_search_name") + ": " + eventName + "\n "
                        + bundle.getString("ui_search_date") + ": " + date.format(eventDate.toLocalDate()) + "\n "
                        + bundle.getString("ui_search_time") + ": " + time.format(eventDate.toLocalTime()) + "\n "
                        + bundle.getString("ui_search_duration") + ": " + nf.format(eventDuration) + " "
                        + bundle.getString("ui_mins");
            } catch (Exception e) {
                return "Event Name: " + eventName + "\n Event Date: " + date.format(eventDate.toLocalDate())
                        + "\n Event Time: "
                        + time.format(eventDate.toLocalTime())
                        + "\n Event Duration: " + nf.format(eventDuration) + " Mins";
            }
        } else {
            try {
                return bundle.getString("ui_search_name") + ": " + eventName + "\n" + bundle.getString("ui_search_date")
                        + ": " + date.format(eventDate.toLocalDate());
            } catch (Exception e) {
                return "Event Name: " + eventName + "\n Event Date: " + date.format(eventDate.toLocalDate());
            }
        }
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public int getEventDuration() {
        return eventDuration;
    }

    public int getRowIndex() {
        return eventDate.getHour();
    }

    public int getColumnIndex(LocalDateTime currentDate) {
        return (int) ChronoUnit.DAYS.between(currentDate.toLocalDate(), eventDate.toLocalDate());
    }

    public int getEventRows() {
        return (int) Math.ceil((double) eventDuration / 60);
    }
}
