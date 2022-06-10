package george.resident.tree.actions;

import connectivity.connection.Connection;
import george.resident.tree.TreeDirectory;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class DeleteDeal implements TreeDeal {

    private final TreeDirectory theirTree;
    private final Path root;

    public DeleteDeal(TreeDirectory theirTree, Path root) {
        this.theirTree = theirTree;
        this.root = root;
    }

    private void deal(Path path, TreeDirectory theirTree) {
        var theirFiles = theirTree.getFiles();
        var theirDirectories = theirTree.getDirectories();
        theirDirectories.remove(".peer");

        if(theirFiles.size() == 0 && theirDirectories.size() == 0){
            try {
                FileUtils.deleteDirectory(path.toFile());
            } catch (Exception ignored){
            }
            return;
        }

        for (Map.Entry<String, Long> pair : theirFiles.entrySet()) {
            String name = pair.getKey();
            Path newPath = path.resolve(name);

            try {
                Files.deleteIfExists(newPath);
            } catch (Exception ignored){
            }
        }

        for (Map.Entry<String, TreeDirectory> pair : theirDirectories.entrySet()){
            String nameNextDirectory = pair.getKey();
            Path newPath = path.resolve(nameNextDirectory);
            if(Files.exists(newPath))
                deal(newPath, pair.getValue());
        }
    }

    @Override
    public void deal() throws IOException {
        deal(root, theirTree);
    }
}
