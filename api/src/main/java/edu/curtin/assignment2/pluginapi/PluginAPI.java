package edu.curtin.assignment2.pluginapi;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public interface PluginAPI {
    void configurePlugin(Map<String, String> args, List<Event> eventList, ResourceBundle bundle);

    List<Event> getEvents();

    Map<String, String> getArgs();

    ResourceBundle getBundle();

}
