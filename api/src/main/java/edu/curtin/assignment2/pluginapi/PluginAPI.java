package edu.curtin.assignment2.pluginapi;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public interface PluginAPI {

    List<Event> getEvents();

    Map<String, String> getArgs();

    ResourceBundle getBundle();

    Locale getLocale();

}
