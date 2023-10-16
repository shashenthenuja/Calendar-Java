package edu.curtin.assignment2.pluginapi;

import java.time.LocalDateTime;

public interface PluginAPI {
    void configurePlugin(String[] args);

    void start();

    LocalDateTime getEventDate();

    String getEventName();

    int getEventDuration();
}
