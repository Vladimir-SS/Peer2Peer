package com.misc.color;

public class ColorPalleteController {
    ColorPallete colorPallete;
    public void setColorPallete(ColorPalleteEnum color) {
        if(color == ColorPalleteEnum.DarkThemeColorPalette){
            colorPallete = new DarkThemeColorPallete("","","","","","");
        }
        else {
            colorPallete  = new LightThemeColorPallete("#DC965A","#FFFFFF","#CCDDE2","#69A2B8","#D4BA9E","#04080F");
        }

    }
}
