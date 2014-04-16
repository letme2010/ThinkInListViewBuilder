package com.uc.framework.resources;

public class ThemeManager {

    public static ThemeManager getInstance() {
        return new ThemeManager();
    }

    public Theme getCurrentTheme() {
        return new Theme();
    }

}
