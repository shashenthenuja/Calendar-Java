package edu.curtin.assignment2.coreapp;

import edu.curtin.assignment2.pluginapi.API;
import edu.curtin.assignment2.pluginapi.PluginAPI;

public class PluginLoader {

    public API loadPlugin(String pluginClassName, PluginAPI api) {
        try {
            Class<?> pluginClass = Class.forName(pluginClassName);
            API plugin = (API) pluginClass.getDeclaredConstructor().newInstance();
            plugin.start(api);

            return (API) plugin;
        } catch (ReflectiveOperationException e) {
            System.out.println("Failed to load plugin " + e.getLocalizedMessage());
            return null;
        }
    }
}
