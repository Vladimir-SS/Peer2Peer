import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//to do:
//-getAllNoBackupData should return all not synchronised files from all devices; need list od devices with their params (

public class MetadataForUI {
    private String name;
    private String time;        //last modified time
    private String device;
    private String extension;
    private Integer multipleDevice;


    private MetadataForUI(String name, String time, String device, String extension) {
        this.name = name;
        this.time = time;
        this.device = device;
        this.extension = extension;
    }

    public MetadataForUI(String name, String time, String device, Integer multipleDevice, String extension) {
        this.name = name;
        this.time = time;
        this.device = device;
        this.extension = extension;
        this.multipleDevice = multipleDevice;
    }

    //action for [scan for files with no backup] (?)
    //currentDeviceMetadataPath: the path to all .json files
    //pathToSync: only files in this dir will be synchronised
    // for all connected devices: getNoBackupFiles(pathOfJsonMetadata); glue them all in one big list
    // if other devices will be checked, their params will be stored inside this class
    public static List<MetadataForUI> getAllNoBackupFiles(String currentDeviceMetadataPath, String pathToSync){
        List<MetadataForUI> noBackupFiles=new ArrayList<>();
        noBackupFiles=getNoBackupFiles(currentDeviceMetadataPath, pathToSync);
        return noBackupFiles;
    }

    private static String getDeviceName(){
        String name="current";
        //???????
        return name;
    }

    private static Integer findMultipleDeviceValue(){
        //?????????????
        return 1;
    }

    private static List<MetadataForUI> getRequiredFilesList(String pathForFiles, String pathToSync, Integer mode){
        List<MetadataForUI> filesList=new ArrayList<>();
        File directory = new File(pathForFiles);
        String[] jsonFileNames = directory.list();

        for(int i=0; i<jsonFileNames.length; i++) {
            if(!jsonFileNames[i].equals("allFiles.json")&&jsonFileNames[i].contains(".json")){
                JsonElement tree;
                JsonObject element=new JsonObject();
                try (Reader reader = Files.newBufferedReader(Paths.get(pathForFiles + "//" + jsonFileNames[i]))) {      //just reads all .json
                    JsonParser parser = new JsonParser();
                    tree = parser.parse(reader);
                    element=tree.getAsJsonObject();

                    switch (mode){
                        case 1:         //no backup : name, last modif. time, device name, extension
                            if((element.get("lastPush").getAsString().equals("null")||element.get("lastPush").getAsString().equals("")) && fileIsInSelectedFolderToSync(pathToSync, element.get("path").getAsString())){
                                filesList.add(new MetadataForUI(getOnlyFileName(jsonFileNames[i]), element.get("lastModifiedTime").getAsString(), getDeviceName(), getExtension(element.get("name").getAsString())));
                            }
                            break;
                        case 2:         //new files : name, last modif, device name, multiple device, ext
                            if(!element.get("lastPush").getAsString().equals("null")&&!element.get("lastPush").getAsString().equals(""))
                                if(element.get("lastPush").getAsString().compareTo(element.get("lastModifiedTime").getAsString())<0){
                                    filesList.add(new MetadataForUI(getOnlyFileName(jsonFileNames[i]), element.get("lastModifiedTime").getAsString(), getDeviceName(), findMultipleDeviceValue(), getExtension(element.get("name").getAsString())));
                                }
                            break;
                        case 3:         //synced : name, last push time, device name, ext
                            if(!element.get("lastPush").getAsString().equals("null")&&!element.get("lastPush").getAsString().equals(""))
                                if(element.get("lastPush").getAsString().compareTo(element.get("lastModifiedTime").getAsString())>0){
                                    filesList.add(new MetadataForUI(getOnlyFileName(jsonFileNames[i]), element.get("lastPush").getAsString(), getDeviceName(), getExtension(element.get("name").getAsString())));
                                }
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong when fetching info from infoFiles)");
                }
            }
        }
        return filesList;
    }

    //get properties for all files for which lastPush is empty
    //returns a list of MetadataForUI ob; use getters for attributes
    //pathForMetadataFiles: dir where all .json files are stored; is updated when this function is called
    //will have to be called for all connected devices; unable to do that rn
    public static List<MetadataForUI> getNoBackupFiles(String pathForFiles, String pathToSync){
        List<MetadataForUI> noBackupFileList=getRequiredFilesList(pathForFiles, pathToSync, 1);
        return noBackupFileList;
    }

    //gets all files for which lastPush<lastModified
    public static List<MetadataForUI> getNewFiles(String pathForFiles){
        List<MetadataForUI> newFileList=getRequiredFilesList(pathForFiles, "", 2);
        return newFileList;
    }

    public static List<MetadataForUI> getSyncedFiles(String pathForFiles){
        List<MetadataForUI> syncedFiles=getRequiredFilesList(pathForFiles, "", 3);
        return syncedFiles;
    }

    private static String getExtension(String fullFileName){
        int lastIndexOf = fullFileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fullFileName.substring(lastIndexOf);
    }

    private  static String getOnlyFileName(String fullFileName){
        int lastIndexOf = fullFileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fullFileName.substring(0,lastIndexOf);
    }

    private static boolean fileIsInSelectedFolderToSync(String pathToSync, String filePath){
        if(filePath.contains(pathToSync))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getMultipleDevice() {
        return multipleDevice;
    }

    public String getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "MetadataForUI{" +
                "name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", device='" + device + '\'' +
                ", extension='" + extension + '\'' +
                ", multipleDevice=" + multipleDevice +
                '}';
    }
}
