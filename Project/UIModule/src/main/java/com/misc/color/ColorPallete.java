package com.misc.color;

import java.awt.*;

public class ColorPallete {

    public Color accent;
    public Color backgroundPrimary ;
    public Color backgroundPrimaryAlt;
    public Color backgroundSecondary ;
    public Color backgroundSecondaryAlt;

    public Color text;

    ColorPallete(String accent, String backgroundPrimary, String backgroundPrimaryAlt, String backgroundSecondary, String backgroundSecondaryAlt, String text){
        this.accent = Color.decode(accent);
        this.backgroundPrimary = Color.decode(backgroundPrimary);
        this.backgroundPrimaryAlt = Color.decode(backgroundPrimaryAlt);
        this.backgroundSecondary = Color.decode(backgroundSecondary);
        this.backgroundSecondaryAlt = Color.decode(backgroundSecondaryAlt);
        this.text = Color.decode(text);

    }
    public Color getAccent(){
        return accent;
    }
    public Color getbackgroundPrimary(){
        return backgroundPrimary;
    }
    public Color getbackgroundPrimaryAlt(){
        return backgroundPrimaryAlt;
    }
    public Color getbackgroundSecondary(){
        return backgroundSecondary;
    }
    public Color getbackgroundSecondaryAlt(){
        return backgroundSecondaryAlt;
    }
    public Color gettext(){
        return text;
    }

}


