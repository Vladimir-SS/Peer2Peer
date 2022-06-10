package george.resident.tree;

import com.google.gson.Gson;
import george.resident.tree.actions.TreeActionsEnum;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemTree{
    protected TreeDirectory root;
    protected TreeActionsEnum action;
    protected String path;

    public FileSystemTree(TreeDirectory root, TreeActionsEnum action) {
        this.root = root;
        this.action = action;
        this.path = "";
    }   

    public Path getPath() {
        return Paths.get(path);
    }

    public void setPath(Path path) {
        this.path = path.toString();
    }

    public TreeActionsEnum getAction() {
        return action;
    }

    public TreeDirectory getRoot() {
        return root;
    }

    public void toJSON(FileWriter writer){
        Gson gson = new Gson();
        gson.toJson(this, writer);
    }
}