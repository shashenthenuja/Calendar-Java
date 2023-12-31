package edu.curtin.calplugins;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import edu.curtin.assignment2.pluginapi.API;
import edu.curtin.assignment2.pluginapi.Event;
import edu.curtin.assignment2.pluginapi.PluginAPI;

/* *******************************************************************
* File:       Notify.java
* Author:     G.G.T.Shashen
* Created:    17/10/2023
* Modified:   25/10/2023
* Desc:       Notify plugin implementing the main API to notify about events
***********************************************************************/
public class Notify implements API {

    private String query;
    private List<Event> eventList;
    private ResourceBundle bundle;
    private Locale locale;

    @Override
    public void start(PluginAPI api) {
        // get the data from the api
        this.query = api.getArgs().get("query");
        this.eventList = api.getEvents();
        this.bundle = api.getBundle();
        this.locale = api.getLocale();
        notifyEvents();
    }

    /*
     * validate the notify user about the event details according
     * to the notify query and current locale
     */
    public void notifyEvents() {
        if (!eventList.isEmpty()) {
            for (Event event : eventList) {
                if (LocalDateTime.now().toLocalDate().equals(event.getEventDate().toLocalDate())) {
                    if (LocalDateTime.now().isAfter(event.getEventDate()) || event.getEventDuration() == 0) {
                        if (event.getEventName().toLowerCase().trim().contains(query.toLowerCase().trim())) {
                            try {
                                System.out.println("\n" + bundle.getString("ui_today_events") + " :");
                                System.out.println(event.getSearchEventDetails(bundle, locale) + "\n");
                            } catch (Exception e) {
                                System.out.println("\n" + "Today's Events :");
                                System.out.println(event.getSearchEventDetails(bundle, locale) + "\n");
                            }
                        }
                    }
                }
            }
        }
    }

}
