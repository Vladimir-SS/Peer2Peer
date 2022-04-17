import java.util.ArrayList;
import java.util.List;

public class SharedFolderInfo {
    private String sharedFolderPath;
    private List<FileObject> presentFiles = new ArrayList<>();

    public SharedFolderInfo(String sharedFolderPath, List<FileObject> presentFiles) {
        this.sharedFolderPath = sharedFolderPath;
        this.presentFiles = presentFiles;
    }

    public boolean addFileObject(FileObject fileObject) {
        if(!presentFiles.contains(fileObject)) {
            presentFiles.add(fileObject);
            return true;
        }
        return false;
    }

    public String getSharedFolderPath() {
        return sharedFolderPath;
    }

    public void setSharedFolderPath(String sharedFolderPath) {
        this.sharedFolderPath = sharedFolderPath;
    }

    public List<FileObject> getPresentFiles() {
        return presentFiles;
    }

    public void setPresentFiles(List<FileObject> presentFiles) {
        this.presentFiles = presentFiles;
    }

    @Override
    public String toString() {
        return "SharedFolderInfo{" +
                "sharedFolderPath='" + sharedFolderPath + '\'' +
                ", presentFiles=" + presentFiles +
                '}';
    }
}
