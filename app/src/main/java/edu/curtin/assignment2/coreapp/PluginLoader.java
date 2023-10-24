package edu.curtin.assignment2.coreapp;

import edu.curtin.assignment2.pluginapi.API;
import edu.curtin.assignment2.pluginapi.PluginAPI;

/* *******************************************************************
* File:       PluginLoader.java
* Author:     G.G.T.Shashen
* Created:    16/10/2023
* Modified:   25/10/2023
* Desc:       Load the plugins/scripts using reflection
***********************************************************************/
public class PluginLoader {

    /*
     * get the plugin/script classname and api to try and load them using reflection
     */
    public API loadPlugin(String pluginClassName, PluginAPI api) {
        try {
            Class<?> pluginClass = Class.forName(pluginClassName);
            API plugin = (API) pluginClass.getDeclaredConstructor().newInstance();
            // start the plugin/script with the loaded api
            plugin.start(api);

            return (API) plugin;
        } catch (ReflectiveOperationException e) {
            System.out.println("Failed to load plugin " + e.getLocalizedMessage());
            return null;
        }
    }
}
