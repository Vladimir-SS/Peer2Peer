package george.resident;

import george.resident.exceptions.BadSyncDirectory;
import george.resident.tree.TreeDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class  SynchronizedDirectory {

    protected Path path;

    /**
     * Method to create the .peer folder starting from the path
     * @param path
     * @throws BadSyncDirectory This exception is thrown when the peer can not be created.
     */
    public SynchronizedDirectory(Path path) throws BadSyncDirectory {
        this.path = path;

        Path peerPath = path.resolve(".peer");

        try {
            Files.createDirectory(peerPath);
        }
        catch (FileAlreadyExistsException ignored){}
        catch (IOException e) {
            throw new BadSyncDirectory(e);
        }
        //TODO: hide this folder
    }

    public Path getPath() {
        return path;
    }

    /**
     * Private recursive method that returns a tree for a directory given as a parameter
     */
    private static TreeDirectory getTreeFromFile(File directory) {

        TreeDirectory treeDirectory = new TreeDirectory();
        File[] children = directory.listFiles();

        if (children == null)
            return treeDirectory;

        for (File file : children) {
            String fileName = file.getName();

            if (file.isDirectory()) {
                TreeDirectory directoryChild = getTreeFromFile(file);
                treeDirectory.addDirectory(fileName, directoryChild);
            } else
                treeDirectory.addFile(fileName, file.lastModified());
        }

        return treeDirectory;
    }

    public TreeDirectory getTree(Path relativePath) {
        return getTreeFromFile(path.resolve(relativePath).toFile());
    }
}
