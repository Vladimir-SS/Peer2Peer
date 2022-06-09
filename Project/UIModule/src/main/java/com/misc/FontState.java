package com.misc;

import java.awt.*;
import java.util.List;


public final class FontState{


    private static final List<Integer> baseDimensions = List.of(12, 16, 20, 24, 28, 32);
    private static List<Font> fonts;

    static {
        setDimensions(0);
    }

    public static Font getFont(int index) {

        return fonts.get(index);
    }

    public static void setDimensions(int dimension) {
        fonts = baseDimensions.stream()
                .map(v -> new Font( "Comic sans",Font.PLAIN,v + dimension ))
                .toList();
    }

}
