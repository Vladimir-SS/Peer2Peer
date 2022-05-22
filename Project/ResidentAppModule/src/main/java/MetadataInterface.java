import java.util.List;

public interface MetadataInterface {
    public List<MetadataForUI> getAllNoBackupFiles(String currentDeviceMetadataPath, String pathToSync);
    public List<MetadataForUI> getRequiredFilesList(String pathForFiles, String pathToSync, Integer mode);
    public List<MetadataForUI> getNoBackupFiles(String pathForFiles, String pathToSync);
    public List<MetadataForUI> getNewFiles(String pathForFiles);
    public List<MetadataForUI> getSyncedFiles(String pathForFiles);

    public String getDeviceName();
    public Integer findMultipleDeviceValue();
    public String getExtension(String fullFileName);
    public String getOnlyFileName(String fullFileName);
    public boolean fileIsInSelectedFolderToSync(String pathToSync, String filePath);

}
