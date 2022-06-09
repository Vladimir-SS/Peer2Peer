package com.misc.color;

import java.awt.*;

public class ColorPalette {
    Color backgroundPrimary;
    Color backgroundPrimaryAlt;
    Color backgroundSecondary;
    Color BackgroundSecondaryAlt;
    Color accent;

    public ColorPalette() {}
    public ColorPalette(Color backgroundPrimary, Color backgroundPrimaryAlt, Color backgroundSecondary, Color backgroundSecondaryAlt, Color accent) {
        this.backgroundPrimary = backgroundPrimary;
        this.backgroundPrimaryAlt = backgroundPrimaryAlt;
        this.backgroundSecondary = backgroundSecondary;
        this.BackgroundSecondaryAlt = backgroundSecondaryAlt;
        this.accent = accent;
    }

    public Color getBackgroundPrimary() {
        return backgroundPrimary;
    }

    public Color getBackgroundPrimaryAlt() {
        return backgroundPrimaryAlt;
    }

    public Color getBackgroundSecondary() {
        return backgroundSecondary;
    }

    public Color getBackgroundSecondaryAlt() {
        return BackgroundSecondaryAlt;
    }

    public Color getAccent() {
        return accent;
    }

    // constructor pentru toate
// get functions for  backgroundPrimary, backgroundPrimaryAlt, backgroundSecondary, backgroundSecondaryAlt, accent
// toate get-urile returneaza clasa javafx.scene.paint.Color
// NO set functions
}

