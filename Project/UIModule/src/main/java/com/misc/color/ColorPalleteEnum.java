package com.misc.color;

public enum ColorPalleteEnum {
    DarkThemeColorPalette(1), LightThemeColorPalette(2);

    int colorThemeCode;

    ColorPalleteEnum(int colorThemeCode) {
        this.colorThemeCode = colorThemeCode;
    }

}
