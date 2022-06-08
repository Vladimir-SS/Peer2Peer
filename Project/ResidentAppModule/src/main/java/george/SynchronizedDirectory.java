package george;

import george.tree.TreeDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class  SynchronizedDirectory {

    protected Path path;

    public SynchronizedDirectory(Path path) {
        this.path = path;

        Path peerPath = path.resolve(".peer");

        try {
            Files.createDirectory(peerPath);
            //TODO: hide this folder
        } catch (FileAlreadyExistsException ignored){

        }
        catch (IOException e) {
            System.err.println("Can't create \".peer\" directory");
            throw new RuntimeException(e);
        }
    }

    public Path getPath() {
        return path;
    }

    //The old File API would do just fine
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

    public TreeDirectory getTree() {
        return getTreeFromFile(path.toFile());
    }
}
