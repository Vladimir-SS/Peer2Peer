
import java.utils.LinkedList;


public final class FontState{



    private static List<Int> dimensions = {12, 16, 20, 24, 28, 6, 32};

    public static FontState getFont(String index) {

        return new Font("comic sans", Font.PLAIN, setDimensions(index));


    }

    public static FontState setDimensions(String index) {
        return dimensions.get(index) + dimensions.get(index) * (index/10);
    }

}