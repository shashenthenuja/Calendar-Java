package edu.curtin.assignment2.coreapp;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.curtin.assignment2.pluginapi.Event;
import edu.curtin.assignment2.pluginapi.PluginAPI;

public class LoaderAPI implements PluginAPI {
    private Map<String, String> args;
    private List<Event> eventList;
    private ResourceBundle bundle;

    public LoaderAPI(Map<String, String> args, List<Event> eventList, ResourceBundle bundle) {
        this.args = args;
        this.eventList = eventList;
        this.bundle = bundle;
    }

    public LoaderAPI(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public List<Event> getEvents() {
        return eventList;
    }

    @Override
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public ResourceBundle getBundle() {
        return bundle;
    }

}
