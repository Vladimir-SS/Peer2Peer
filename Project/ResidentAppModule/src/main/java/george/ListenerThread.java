package george;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class ListenerThread extends FileListener implements Runnable{




    public ListenerThread(Path root) {
        super(root);

        this.root = root;
    }

    @Override
    public void run() {
        try(WatchService service = FileSystems.getDefault().newWatchService()){
            // create an instance of a hasmap class
            Map<WatchKey, Path> keyMap = new HashMap<>();

            keyMap.put(
                    root.register(service,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY),
                    root
            );
            WatchKey watchKey;
            List<File> directoriesIFound= new ArrayList<>();
            directoriesIFound.add(root.toFile());

            for(String file : root.toFile().list())
            {
                System.out.println(file);
                String root4Folders = new String(root+"\\"+file);
                System.out.println(root4Folders);
                if(new File(root4Folders).exists() && new File(root4Folders).isDirectory())
                {
                    directoriesIFound.add(new File(root4Folders));
//                                System.out.println("ar trebui thread");
                    ListenerThread thread = new ListenerThread(Paths.get(root4Folders));
                    Thread myThread = new Thread(thread);

                    myThread.start();
                }
            }


            do {


                watchKey = service.take();
                Path eventDir = keyMap.get(watchKey);
                for(WatchEvent<?> event : watchKey.pollEvents()){
//                    get the event type
                    WatchEvent.Kind<?> kind =event.kind();
//                    get the path
                    Path eventPath = (Path)event.context();

                    Path file = root.resolve((Path) event.context());


                    if(root.toFile().exists()) {
                        System.out.println(eventDir + ":" + kind + ":" + eventPath);
                        System.out.println(file);

                        switch (kind.toString()){
                            case "ENTRY_CREATE":
                                System.out.println("created");
                                createEvent(file);

                                break;
                            case "ENTRY_MODIFY":
                                System.out.println("modify");
                                modifiedEvent(file);
                                break;
                            case "ENTRY_DELETE":
                                System.out.println("delete");
                                deleteEvent(file);
                                break;

                        }

                        if (file.toFile().isDirectory()) {
                            boolean ok = true;
                            for (File fisier : directoriesIFound) {
                               if (fisier.getName().equals(file.toFile().getName())) {
                                    ok = false;

                                }
                            }

                            if (ok) {
                                directoriesIFound.add(file.toFile());
                                ListenerThread thread = new ListenerThread(file);
                                Thread myThread = new Thread(thread);

                                myThread.start();
                                break;
                            }
                        }
                    }
                }



            }while(watchKey.reset());


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
