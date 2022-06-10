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

public class PushDeal implements TreeDeal {
    private final Connection connection;
    private final FileSystemTree theirSystemTree;
    private final Path root;

    public PushDeal(Connection connection, FileSystemTree theirSystemTree, Path root) {
        this.connection = connection;
        this.theirSystemTree = theirSystemTree;
        this.root = root;
    }

    private void deal(Path path, TreeDirectory theirTree) throws IOException {
        if(path.startsWith(".peer"))
            return;
        Path absolutePath = root.resolve(path);

        if(!Files.isDirectory(absolutePath))
        {
            if (theirTree.containsFile("")
                    && Files.getLastModifiedTime(absolutePath).toMillis() > theirTree.getModified(""))
                connection.sendFile(root, path);

            return;
        }


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
        System.out.println(theirSystemTree.getPath());
        System.out.println(theirSystemTree.getRoot());
        deal(theirSystemTree.getPath(), theirSystemTree.getRoot());
    }
}