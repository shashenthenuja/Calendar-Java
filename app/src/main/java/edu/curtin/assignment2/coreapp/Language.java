package edu.curtin.assignment2.coreapp;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

/* *******************************************************************
* File:       Language.java
* Author:     G.G.T.Shashen
* Created:    24/10/2023
* Modified:   25/10/2023
* Desc:       Class to handle the language choice of the user
***********************************************************************/
public class Language {
    private Scanner scan = new Scanner(System.in);
    private String lang = "";
    private Locale locale;
    private ResourceBundle bundle;

    /*
     * display the language menu for the user to select any language using IETF tags
     */
    public void languageMenu() {
        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.println("Choose your language : ");
            System.out.println("[0] System Default ");
            System.out.println("[1] Custom Language (Eg. en-US, si-LK) \n");
            System.out.print("\n> ");
            String userInput = scan.next();
            // set the language choice of the user
            if (userInput.equals("0")) {
                lang = "default";
                break;
            } else if (userInput.equals("1")) {
                System.out.print("\033[H\033[2J");
                System.out.print("Enter your language (Eg. en-US, si-LK) :  ");
                lang = scan.next();
                break;
            } else {
                System.out.println("Enter a valid choice!");
            }
        }
    }

    /*
     * set the locale of the application according to the system default language or
     * selected IETF tag
     */
    public void setLocale(String lang) {
        if (lang.equals("default")) {
            Locale defaultLocale = Locale.getDefault();
            String country = defaultLocale.getCountry();
            if (country.isEmpty()) {
                defaultLocale = Locale.US;
            }
            locale = defaultLocale;
            bundle = ResourceBundle.getBundle("bundle", locale);
        } else {
            try {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", locale);
            } catch (MissingResourceException e) {
                locale = Locale.forLanguageTag(lang);
                bundle = ResourceBundle.getBundle("bundle", Locale.US);
            }
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLang() {
        return lang;
    }

}
