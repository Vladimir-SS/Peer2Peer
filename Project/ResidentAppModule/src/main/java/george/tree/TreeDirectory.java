package george.tree;

import java.util.*;

public class TreeDirectory {
    private final Map<String, TreeDirectory> directories = new HashMap<>();
    private final Map<String, Long> files = new HashMap<>();

    public void addDirectory(String name, TreeDirectory directory){
        directories.put(name, directory);
    }

    public void addFile(String name, long modified){
        files.put(name, modified);
    }

    public Long getModified(String name){
        return files.get(name);
    }

    public Map<String, TreeDirectory> getDirectories() {
        return directories;
    }

    public Map<String, Long> getFiles() {
        return files;
    }

    public boolean containsDirectory(String name){
        return directories.containsKey(name);
    }

    public boolean containsFile(String name){
        return files.containsKey(name);
    }

    public TreeDirectory getSubDirectory(String name){
        return directories.get(name);
    }
}
