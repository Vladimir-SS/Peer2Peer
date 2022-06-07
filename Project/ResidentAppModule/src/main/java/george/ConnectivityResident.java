package george;

import com.google.gson.Gson;
import connectivity.Peer;
import connectivity.connection.Connection;
import george.tree.FileSystemTree;
import george.tree.TreeDirectory;
import george.tree.actions.PushDeal;
import george.tree.actions.TreeActionsEnum;
import george.tree.actions.TreeDeal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConnectivityResident extends Thread {
    private Peer peer;
    private SynchronizedDirectory synchronizedDirectory;
    private List<Connection> lastSearch = new ArrayList<>();

    public ConnectivityResident(Peer peer, SynchronizedDirectory synchronizedDirectory) {
        this.peer = peer;
        this.synchronizedDirectory = synchronizedDirectory;
    }

    public ConnectivityResident() {
        this(null, null);
    }
    public Peer getPeer() {
        return peer;
    }

    public SynchronizedDirectory getSynchronizedDirectory() {
        return synchronizedDirectory;
    }

    public void setSynchronizedDirectory(SynchronizedDirectory synchronizedDirectory) {
        this.synchronizedDirectory = synchronizedDirectory;
    }

    public List<Connection> getConnectedDevices(){
        lastSearch = peer.getConnectedDevices();

        return lastSearch;
    }

    public List<Connection> getLastSearch() {
        return lastSearch;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }



    /**
     *
     *
     * @param connection
     * @throws FileSystemException
     * @throws IOException
     */
    private void sendFileSystemTree(Connection connection, FileSystemTree fileSystemTree) throws FileSystemException, IOException {
        Path tempFile;

        try{
            tempFile = Files.createTempFile("action", ".json");
            FileWriter writer = new FileWriter(tempFile.toFile());
            fileSystemTree.toJSON(writer);
            writer.close();
        } catch (IOException e) {
            throw new FileSystemException("action.json");
        }

        connection.sendFile(tempFile, null);

        try {
            Files.delete(tempFile);
        } catch (IOException ignored) {
        }
    }

    //This exists because I have to deal with temp file not working
    private void sendAction(Connection connection, TreeActionsEnum action) throws IOException {
        TreeDirectory root = synchronizedDirectory.getTree();
        FileSystemTree fileSystemTree = new FileSystemTree(root, action);

        try {
            sendFileSystemTree(connection, fileSystemTree);
        } catch (FileSystemException ignored){
            //TODO: DEAL WITH TEMP FILE NOT WORKING
        }
    }

    public void fetchFiles(Connection connection) throws  IOException {
        sendAction(connection, TreeActionsEnum.Fetch);
    }

    public void syncFiles(Connection connection) throws IOException {
        sendAction(connection, TreeActionsEnum.Sync);
    }

    public void sendModifiedFiles(Connection connection, FileSystemTree fileSystemTree) throws IOException {
        TreeDirectory ourTree = synchronizedDirectory.getTree();
        TreeDeal action = new PushDeal(
                connection,
                ourTree,
                fileSystemTree.root(),
                synchronizedDirectory.path().resolve(fileSystemTree.getRootPath())
        );

        action.deal();
    }

    public void incomingAction(Connection connection, FileSystemTree fileSystemTree){
        try {

            switch (fileSystemTree.action()){
                case Sync:
                    sendModifiedFiles(connection, fileSystemTree);
                    fetchFiles(connection);
                    break;
                case Fetch:
                    sendModifiedFiles(connection, fileSystemTree);
                break;
                case Delete:
                break;
            }

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (isAlive()) {
            System.out.println("Aliveee");
            var entry = peer.incomingFile(synchronizedDirectory.path());
            System.out.println("got smth");
            Connection connection = entry.getKey();
            Path relativePath = entry.getValue();

            if (relativePath == null) {
                peer.disconnectDevice(connection);
                continue;
            }
            Path path = synchronizedDirectory.path().resolve(relativePath);

            String fileName = path.getFileName().toString();
            System.out.println(fileName);

            if(
                    relativePath.startsWith("peer")
                    && fileName.startsWith("action")
                    && fileName.endsWith(".json")
            ){
                try(InputStream inputStream = new FileInputStream(path.toFile())) {
                    Gson gson = new Gson();
                    Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                    FileSystemTree fileSystemTree = gson.fromJson(reader, FileSystemTree.class);
                    incomingAction(connection, fileSystemTree);
                    Files.delete(path);
                } catch (IOException ignored) {

                }

            }
        }

    }
}
