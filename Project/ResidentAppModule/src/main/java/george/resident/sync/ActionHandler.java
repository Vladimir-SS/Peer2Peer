package george.resident.sync;

import connectivity.connection.Connection;
import george.resident.SynchronizedDirectory;
import george.resident.tree.FileSystemTree;
import george.resident.tree.TreeDirectory;
import george.resident.tree.actions.DeleteDeal;
import george.resident.tree.actions.PushDeal;
import george.resident.tree.actions.TreeActionsEnum;
import george.resident.tree.actions.TreeDeal;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ActionHandler {

    protected SynchronizedDirectory synchronizedDirectory;

    public ActionHandler(SynchronizedDirectory synchronizedDirectory) {
        this.synchronizedDirectory = synchronizedDirectory;
    }

    public SynchronizedDirectory getSynchronizedDirectory() {
        return synchronizedDirectory;
    }

    private void sendFileSystemTree(Connection connection, FileSystemTree fileSystemTree) throws FileNotFoundException, IOException {
        Path root = synchronizedDirectory.getPath();
        Path tempFile;

        try {
            tempFile = Files.createTempFile(
                    root.resolve(".peer"),
                    "action",
                    ".json"
            );
            FileWriter fileWriter = new FileWriter(tempFile.toFile());
            fileSystemTree.toJSON(fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            throw new FileNotFoundException(".peer/action.json");
        }

        connection.sendFile(root, root.relativize(tempFile));

        try {
            Files.delete(tempFile);
        } catch (IOException ignored) {
        }
    }

    //This exists because I have to deal with temp file not working
    public void sendAction(Connection connection, TreeActionsEnum action, Path ...paths) throws IOException {
        Path path = Arrays.stream(paths).reduce(Paths.get(""), Path::resolve);

        TreeDirectory root = synchronizedDirectory.getTree(path);
        FileSystemTree fileSystemTree = new FileSystemTree(root, action);
        fileSystemTree.setPath(path);


        Path absolutePath = synchronizedDirectory.getPath().resolve(path);

        if(!Files.exists(absolutePath)){
            root.addFile("", 0);
        } else if(!Files.isDirectory(absolutePath)){
            root.addFile("", Files.getLastModifiedTime(absolutePath).toMillis());
        }

        if(action == TreeActionsEnum.Delete){
            if(!Files.isSameFile(synchronizedDirectory.getPath(), absolutePath)) {
                if (Files.isDirectory(absolutePath)){
                    root.getFiles().clear();
                    root.getDirectories().clear();
                }
            }

            new DeleteDeal(root, absolutePath).deal();
        }

        try {
            sendFileSystemTree(connection, fileSystemTree);
        } catch (FileSystemException ignored) {
            //TODO: DEAL WITH TEMP FILE NOT WORKING
        }
    }

    private void sendModifiedFiles(Connection connection, FileSystemTree fileSystemTree) throws IOException {
        TreeDeal action = new PushDeal(
                connection,
                fileSystemTree,
                synchronizedDirectory.getPath().resolve(fileSystemTree.getPath())
        );

        action.deal();
    }

    public void incomingAction(Connection connection, FileSystemTree fileSystemTree) {
        try {

            switch (fileSystemTree.getAction()) {
                case Sync -> {
                    sendModifiedFiles(connection, fileSystemTree);
                    sendAction(connection, TreeActionsEnum.Fetch, fileSystemTree.getPath());
                }
                case Fetch -> {
                    sendModifiedFiles(connection, fileSystemTree);
                }
                case Delete -> {
                    new DeleteDeal(
                            fileSystemTree.getRoot(),
                            synchronizedDirectory.getPath().resolve(fileSystemTree.getPath())
                    ).deal();
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
