import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileObject {
    private String localPath;                       //mandatory
    private String name;
    private String sharedFolderPath;                //mandatory
    private Integer id;                             //mandatory
    private float size;                             //an int would have given only an approximate size
    private FileInfo attachedInfo;

    public FileObject(String localPath, String name, String sharedFolderPath, Integer id) {
        this.localPath = localPath;
        this.name = name;
        this.sharedFolderPath = sharedFolderPath;
        this.id = id;
        this.size=getSize(localPath);
    }

    public FileObject(String localPath, String name, String sharedFolderPath, Integer id, FileInfo attachedInfo) {
        this.localPath = localPath;
        this.name = name;
        this.sharedFolderPath = sharedFolderPath;
        this.id = id;
        this.attachedInfo = attachedInfo;
        this.size=getSize(localPath);
    }

    public FileObject(String localPath, String sharedFolderPath, Integer id) {
        this.name=getFileNameFromPath(localPath);
        this.localPath = localPath;
        this.sharedFolderPath = sharedFolderPath;
        this.id = id;
        this.size=getSize(localPath);
    }

    public FileObject(String localPath, String sharedFolderPath, Integer id, FileInfo attachedInfo) {
        this.name=getFileNameFromPath(localPath);
        this.localPath = localPath;
        this.sharedFolderPath = sharedFolderPath;
        this.id = id;
        this.size=getSize(localPath);
        this.attachedInfo = attachedInfo;
    }

    private String getFileNameFromPath(String path){
        Path p= Paths.get(path);
        return p.getFileName().toString();
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSharedFolderPath() {
        return sharedFolderPath;
    }

    public void setSharedFolderPath(String sharedFolderPath) {
        this.sharedFolderPath = sharedFolderPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getSize(String path){
        File auxFile = new File(localPath);
        float s=(float) auxFile.length() / 1024;
        return s;
    }

    public float getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public FileInfo getAttachedInfo() {
        return attachedInfo;
    }

    public void setAttachedInfo(FileInfo attachedInfo) {
        this.attachedInfo = attachedInfo;
    }

    @Override
    public String toString() {
        return "FileObject{" +
                "localPath='" + localPath + '\'' +
                ", name='" + name + '\'' +
                ", sharedFolderPath='" + sharedFolderPath + '\'' +
                ", id=" + id +
                ", size=" + size +
                ", attachedInfo=" + attachedInfo +
                '}';
    }
}
