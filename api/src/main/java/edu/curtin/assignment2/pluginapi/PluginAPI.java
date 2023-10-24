package edu.curtin.assignment2.pluginapi;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/* *******************************************************************
* File:       PluginAPI.java
* Author:     G.G.T.Shashen
* Created:    16/10/2023
* Modified:   25/10/2023
* Desc:       PluginAPI interface to get the required information
*             to the plugins/scripts
***********************************************************************/
public interface PluginAPI {

    List<Event> getEvents();

    Map<String, String> getArgs();

    ResourceBundle getBundle();

    Locale getLocale();

}
