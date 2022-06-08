package george.tree;

import com.google.gson.Gson;
import george.tree.actions.TreeActionsEnum;

import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemTree{
    protected TreeDirectory root;
    protected TreeActionsEnum action;
    protected String rootPath;

    public FileSystemTree(TreeDirectory root, TreeActionsEnum action) {
        this.root = root;
        this.action = action;
        this.rootPath = "";
    }

    public void setPath(Path path) {
        this.rootPath = path.toString();
    }

    public Path getRootPath() {
        return Paths.get(rootPath);
    }

    public TreeActionsEnum getAction() {
        return action;
    }

    public TreeDirectory getRoot() {
        return root;
    }

    //TODO: remove this... they're here from the record (no final variables with gson i guess)

    public TreeActionsEnum action() {
        return action;
    }

    public TreeDirectory root() {
        return root;
    }

    public void toJSON(FileWriter writer){
        Gson gson = new Gson();
        gson.toJson(this, writer);
    }
}