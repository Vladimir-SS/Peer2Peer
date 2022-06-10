package george.resident.tree;

/**
 * Helper class to create directory
 * Used by {@link PushDeal} to syncronize a requester to ourTree files.
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
