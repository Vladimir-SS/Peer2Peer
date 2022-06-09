package com.misc.color;
import com.misc.color.ColorPaletteEnum;

public class ColorPaletteController extends ColorPalette {
    // singletone
    ColorPalette colorPalette;

    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(ColorPaletteEnum color) {
        this.ColorPaletteEnum = color;
    }
}