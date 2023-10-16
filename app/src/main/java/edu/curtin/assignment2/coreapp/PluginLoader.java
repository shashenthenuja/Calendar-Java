package edu.curtin.assignment2.coreapp;

import edu.curtin.assignment2.pluginapi.PluginAPI;

public class PluginLoader {
    
    public PluginAPI loadPlugin(String pluginClassName, String[] args) {
        try {
            Class<?> pluginClass = Class.forName(pluginClassName);
            PluginAPI plugin = (PluginAPI) pluginClass.getDeclaredConstructor().newInstance();
            plugin.configurePlugin(args);

            return (PluginAPI) plugin;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
