package george.tree.actions;

import connectivity.connection.Connection;
import george.tree.TreeDirectory;
import george.tree.WildcardTreeDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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