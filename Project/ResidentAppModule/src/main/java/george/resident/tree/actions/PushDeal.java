package george.resident.tree.actions;

import connectivity.connection.Connection;
import george.resident.tree.TreeDirectory;
import george.resident.tree.WildcardTreeDirectory;

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
        for (Map.Entry<String, Long> pair : ourTree.getFiles().entrySet()) {
            String name = pair.getKey();
            Path newPath = path.resolve(name);

            if (theirTree.containsFile(name) && pair.getValue() <= theirTree.getModified(name))
                continue;
            connection.sendFile(root, newPath);
        }

        var ourDirectories = ourTree.getDirectories();
        ourDirectories.remove(".peer");

        for (Map.Entry<String, TreeDirectory> pair : ourDirectories.entrySet()) {
            String nameNextDirectory = pair.getKey();
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