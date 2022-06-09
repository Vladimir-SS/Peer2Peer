package com.misc;

import java.awt.*;

public class ColorPallete {

    public static Color accent;
    public static Color backgroundPrimary ;
    public static Color backgroundPrimaryAlt;
    public static Color backgroundSecondary ;
    public static Color backgroundSecondaryAlt;

    public static Color text;

    ColorPallete(Color[] colors){
        accent = colors[0];
        backgroundPrimary = colors[1];
        backgroundPrimaryAlt =  colors[2];
        backgroundSecondary =  colors[3];
        backgroundSecondaryAlt =  colors[4];
        text = colors[5];

    }
    public static Color getAccent(){
        return accent;
    }
    public static Color getbackgroundPrimary(){
        return backgroundPrimary;
    }
    public static Color getbackgroundPrimaryAlt(){
        return backgroundPrimaryAlt;
    }
    public static Color getbackgroundSecondary(){
        return backgroundSecondary;
    }
    public static Color getbackgroundSecondaryAlt(){
        return backgroundSecondaryAlt;
    }
    public static Color gettext(){
        return text;
    }

}





enum ColorPalleteEnum {
    ACCENT,
    BACKGROUNDPRIMARY(ColorPallete.getbackgroundPrimary()),
    BACKGROUNDPRIMARYALT(ColorPallete.getbackgroundPrimaryAlt()),
    BACKGROUNDSECONDARY(ColorPallete.getbackgroundSecondary()),
    BACKGROUNDSECONDARYALT(ColorPallete.getbackgroundSecondaryAlt()),
    TEXT(ColorPallete.gettext());

    private static ColorPallete colorPallete;
    ColorPalleteEnum(ColorPallete colorPallete) {
        ACCENT = colorPallete.accent;
    }


}

class ColorPalleteController {

}