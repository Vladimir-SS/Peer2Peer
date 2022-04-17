import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileInfo {
    private FileObject refersTo;
    private List<User> sharedToUsers=new ArrayList<>();             //changed from User[]
    private Date lastModified;
    private Date lastSynchronized;

    public FileInfo(FileObject refersTo) {
        this.refersTo = refersTo;
    }

    public FileInfo(FileObject refersTo, List<User> sharedToUsers) {
        this.refersTo = refersTo;
        this.sharedToUsers = sharedToUsers;
    }

    public FileInfo(FileObject refersTo, List<User> sharedToUsers, Date lastModified, Date lastSynchronized) {
        this.refersTo = refersTo;
        this.sharedToUsers = sharedToUsers;
        this.lastModified = lastModified;
        this.lastSynchronized = lastSynchronized;
    }

    public void addSharedUser(User user){
        this.sharedToUsers.add(user);
    }

    public void removeSharedUser(User user){
        this.sharedToUsers.remove(user);
    }

    public FileObject getRefersTo() {
        return refersTo;
    }

    public void setRefersTo(FileObject refersTo) {
        this.refersTo = refersTo;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getLastSynchronized() {
        return lastSynchronized;
    }

    public void setLastSynchronized(Date lastSynchronized) {
        this.lastSynchronized = lastSynchronized;
    }

    public List<User> getSharedToUsers() {
        return sharedToUsers;
    }

    public void setSharedToUsers(List<User> sharedToUsers) {
        this.sharedToUsers = sharedToUsers;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "refersTo=" + refersTo +
                ", sharedToUsers=" + sharedToUsers +
                ", lastModified=" + lastModified +
                ", lastSynchronized=" + lastSynchronized +
                '}';
    }
}
