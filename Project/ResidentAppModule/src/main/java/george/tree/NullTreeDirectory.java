package george.tree;

public class NullTreeDirectory extends WildcardTreeDirectory {
    @Override
    public boolean containsFile(String name) {
        return false;
    }
}
