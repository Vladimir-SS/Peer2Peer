package george;


import java.nio.file.Path;
import java.nio.file.Paths;

//import java.io.File;
public class testingListener
{
    public static void main(String[] args) {
        Path root = Paths.get("D:\\testlistener");
        FileListener thing = new FileListener(root);
        thing.startL();

    }


}
