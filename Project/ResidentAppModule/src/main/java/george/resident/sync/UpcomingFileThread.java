package george.resident.sync;

import com.google.gson.Gson;
import connectivity.connection.Connection;
import george.resident.tree.FileSystemTree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class UpcomingFileThread extends Thread{
    private final ActionHandler actionHandler;
    private final PeerManager peerManager;


    public UpcomingFileThread(ActionHandler actionHandler, PeerManager peerManager) {
        this.actionHandler = actionHandler;
        this.peerManager = peerManager;
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }

    public PeerManager getPeerManager() {
        return peerManager;
    }

    @Override
    public void run() {
        while (isAlive()) {
            var entry = peerManager.getPeer()
                    .incomingFile(actionHandler.getSynchronizedDirectory().getPath());
            Connection connection = entry.getKey();
            Path relativePath = entry.getValue();

            if (relativePath == null) {
                this.peerManager.getPeer().disconnectDevice(connection);
                continue;
            }
            Path path = actionHandler.getSynchronizedDirectory().getPath().resolve(relativePath);
            String fileName = path.getFileName().toString();

            if(
                    relativePath.startsWith(".peer")
                            && fileName.startsWith("action")
                            && fileName.endsWith(".json")
            ){
                try(InputStream inputStream = new FileInputStream(path.toFile())) {
                    Gson gson = new Gson();
                    Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                    FileSystemTree fileSystemTree = gson.fromJson(reader, FileSystemTree.class);
                    actionHandler.incomingAction(connection, fileSystemTree);
                } catch (IOException ignored) {

                }
                try {
                    Files.delete(path);
                } catch (IOException ignored) {

                }
            }
        }
    }
}
