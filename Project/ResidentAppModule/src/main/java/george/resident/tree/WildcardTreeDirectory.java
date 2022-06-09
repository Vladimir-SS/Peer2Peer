package george.resident.tree;

/**
 * Helper class to create directory
 * Only George can explain it well enough
 */
public class WildcardTreeDirectory extends TreeDirectory {
    @Override
    public boolean containsFile(String name) {
        return false;
    }
    @Override
    public boolean containsDirectory(String name) {
        return true;
    }
    @Override
    public TreeDirectory getSubDirectory(String name) {
        return this;
    }
}
