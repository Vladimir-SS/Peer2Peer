package george.resident.tree.actions;

import connectivity.connection.Connection;
import george.resident.tree.TreeDirectory;
import george.resident.tree.WildcardTreeDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 *  PushDeal class helps to transfer data between two devices
 */
public class PushDeal implements TreeDeal {
    private final Connection connection;
    private final TreeDirectory ourTree;
    private final TreeDirectory theirTree;
    private final Path root;

    public PushDeal(Connection connection, TreeDirectory ourTree, TreeDirectory theirTree, Path root) {
        this.connection = connection;
        this.ourTree = ourTree;
        this.theirTree = theirTree;
        this.root = root;
    }

    /**
     * The method receives three parameters and perform synchronization between two devices starting from ourTree.
     * In a recursive way all files that are in theirTree and not in ourTree will be sent to ourTree
     * In the end both trees will have the same files
     * @param path From where the method start.
     * @param ourTree The tree that needs to be synchronized.
     * @param theirTree The tree from where the synchronization is done.
     * @throws IOException This exception is thrown when the connection between the two devices does not work.
     */
    private void deal(Path path, TreeDirectory ourTree, TreeDirectory theirTree) throws IOException {
        System.out.println("DEAL DIRECTORY: " + path);

        for (Map.Entry<String, Long> pair : ourTree.getFiles().entrySet()) {
            String name = pair.getKey();
            Path newPath = path.resolve(name);

            if (theirTree.containsFile(name) && pair.getValue() <= theirTree.getModified(name))
                continue;
            connection.sendFile(root, newPath);
            System.out.println("DEAL SENT " + newPath);
        }

        var ourDirectories = ourTree.getDirectories();
        ourDirectories.remove(".peer");

        for (Map.Entry<String, TreeDirectory> pair : ourDirectories.entrySet()) {
            String nameNextDirectory = pair.getKey();
            System.out.println("DEAL NEXT DIRECTORY: " + nameNextDirectory);

            TreeDirectory theirNextTree = theirTree.containsDirectory(nameNextDirectory)
                    ? theirTree.getSubDirectory(nameNextDirectory)
                    : new WildcardTreeDirectory();

            Path newPath = path.resolve(pair.getKey());
            deal(newPath, pair.getValue(), theirNextTree);
        }
    }

    @Override
    public void deal() throws IOException {
        deal(Paths.get(""), ourTree, theirTree);
    }
}