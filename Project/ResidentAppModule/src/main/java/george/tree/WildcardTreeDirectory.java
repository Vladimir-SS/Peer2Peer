package george.tree;

public class WildcardTreeDirectory extends TreeDirectory {

    @Override
    public boolean containsDirectory(String name) {
        return true;
    }

    @Override
    public boolean containsFile(String name) {
        return true;
    }

    @Override
    public TreeDirectory getSubDirectory(String name) {
        return this;
    }
}
