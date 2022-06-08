package george;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class FileListener {
    Path root;

    protected  void modifiedEvent(Path absolutePath){};
    protected  void deleteEvent(Path absolutePath){};
    protected  void createEvent(Path absolutePath){};

    FileListener(Path root){

        this.root=root;





    }
    public void startL(){
        ListenerThread t1 = new ListenerThread(root);
        Thread myThread = new Thread(t1);
        myThread.start();

    }


}
