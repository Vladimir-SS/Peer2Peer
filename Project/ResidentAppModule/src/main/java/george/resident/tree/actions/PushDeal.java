package george.resident.tree.actions;

import connectivity.connection.Connection;
import george.resident.tree.FileSystemTree;
import george.resident.tree.TreeDirectory;
import george.resident.tree.WildcardTreeDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *  PushDeal class helps to transfer data between two devices
 */
public class PushDeal implements TreeDeal {
    private final Connection connection;
    private final FileSystemTree theirSystemTree;
    private final Path root;

    public PushDeal(Connection connection, FileSystemTree theirSystemTree, Path root) {
        this.connection = connection;
        this.theirSystemTree = theirSystemTree;
        this.root = root;
    }

      /**
     * The method receives three parameters and perform synchronization between two devices starting from ourTree.
     * In a recursive way all files that are in ourTree and not in theirTree will be sent to theirTree.
     * The device that sent the request will have all the new/modified files.
     * @param path From where the method start.
     * @param theirTree The tree that needs to be synchronized.
     * @param ourTree The tree from where the synchronization is done.
     * @throws IOException This exception is thrown when the connection between the two devices does not work.
     */
    private void deal(Path path, TreeDirectory theirTree) throws IOException {
        if(path.startsWith(".peer"))
            return;
        Path absolutePath = root.resolve(path);

        File absoluteFile= absolutePath.toFile();

        File[] children = absoluteFile.listFiles();

        if (children == null)
            return;

        for (File file : children) {
            String fileName = file.getName();
            Path newPath = path.resolve(fileName);
            BasicFileAttributes basicFileAttributes = Files.readAttributes(root.resolve(newPath), BasicFileAttributes.class);

            if (basicFileAttributes.isDirectory()) {
                TreeDirectory theirNextTree = theirTree.containsDirectory(fileName)
                        ? theirTree.getSubDirectory(fileName)
                        : new WildcardTreeDirectory();

                deal(newPath, theirNextTree);
            } else {
                if (theirTree.containsFile(fileName)
                        && basicFileAttributes.lastModifiedTime().toMillis() <= theirTree.getModified(fileName))
                    continue;
                connection.sendFile(root, newPath);
            }
        }
    }

    @Override
    public void deal() throws IOException {
        var theirTree = theirSystemTree.getRoot();

        if(theirTree.containsFile(""))
        {
            var absolutePath = root.resolve(theirSystemTree.getPath());
            try {
                if(Files.isDirectory(absolutePath)){
                    theirTree = new WildcardTreeDirectory();
                }
                else if (Files.getLastModifiedTime(absolutePath).toMillis() > theirTree.getModified("")){
                    connection.sendFile(root, theirSystemTree.getPath());
                    return;
                }
            } catch (Exception ignored) {
                return;
            }
        }
        deal(theirSystemTree.getPath(), theirTree);
    }
}