package SyncFiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SyncFiles {
    static List<String> syncedFiles;
    static List<String> directoryPaths;

    static private void sendFileToAll(List<String> targets, String source, String fileName) throws IOException {
        Path sourceDir = Paths.get(source);
        for (String target : targets) {
            Path targetDir = Paths.get(target + "\\" + fileName);
            Files.copy(sourceDir, targetDir, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    static private File pickVersion(List<File> versions) {
        File lastModif = versions.get(0);
        for (File vers : versions) {
            if (lastModif.lastModified() < vers.lastModified()) {
                lastModif = vers;
            }
        }
        return lastModif;
    }

    static private File getFileFromDir(File file, String dirPath) {
        return new File(dirPath + "\\" + file.getName());
    }

    static private boolean findFile(File file, String path) {
        //System.out.println("Searching " + file.getName() + " in " + path);
        File appDirectory = new File(path);
        File[] appDirectoryFiles = appDirectory.listFiles();
        String filename = file.getName().substring(0, file.getName().length() - 4);
        String filesearch = filename + ".json";
        //System.out.println("Searching " + filesearch);
        if (appDirectoryFiles != null) {
            for (File appFile : appDirectoryFiles) {
                if (appFile.getName().equals(filesearch)) {
                    //System.out.println("Found " + filesearch);
                    return true;
                }
            }
        }
        return false;
    }

    static private void SyncDevice(String path) {
        //iterate through device app folder
        String filesPath = path + "\\files";
        File appDirectory = new File(path);
        File[] appDirectoryFiles = appDirectory.listFiles();
        if (appDirectoryFiles != null) {
            for (File appFile : appDirectoryFiles) {
                String name = appFile.getName();
                if (!name.contains(".json") && !name.equals("files")) {
                    if (findFile(appFile, filesPath)) {
                        //update info files
                        System.out.println("Found " + name);
                    } else {
                        //upload to info file + add separate info file
                        System.out.println(name + " not found");
                    }
                }
            }
        } else {
            System.out.println("Directory not found.");
        }
    }

    static private void SyncAllDevices() throws IOException {
        //iterate through all devices
        directoryPaths = new ArrayList<>();
        syncedFiles = new ArrayList<>();
        //for each connected device : get directory path
        //add to directory paths
        //adding two dirs for demo
        directoryPaths.add("");//this device folder
        directoryPaths.add("");//other device
        //for each file not in syncedFiles iterate through other devices
        for (int i = 0; i < directoryPaths.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.println("Searching in " + directoryPaths.get(i));
            String crtDir = directoryPaths.get(i);
            File appDirectory = new File(crtDir);
            File[] appDirectoryFiles = appDirectory.listFiles();
            if (appDirectoryFiles != null) {
                for (File appFile : appDirectoryFiles) {
                    System.out.println("File " + appFile.getName() + " : ");
                    if (!syncedFiles.contains(appFile.getName()) && !appFile.getName().equals("files")) {//a file in this dir is not synced
                        System.out.println("--not synced");
                        List<File> fileVersions = new ArrayList<>();//getting all versions
                        fileVersions.add(appFile);
                        for (int j = 0; j < directoryPaths.size(); j++)//searching for it in other devices
                        {
                            if (i != j)//avoiding current device folder
                            {
                                if (findFile(appFile, directoryPaths.get(j) + "\\files"))//found file in other device
                                    fileVersions.add(getFileFromDir(appFile, directoryPaths.get(j)));//adding to versions
                                else System.out.println(appFile.getName() + " not in both");
                            }
                        }
                        //searching for the most recent version
                        //then sending it to all devices
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        System.out.println("VERSIONS:");
                        for (File vers : fileVersions) {
                            System.out.println("VERSION: " + vers.getName() + " " + sdf.format(vers.lastModified()));
                        }
                        //choosing the last version
                        File lastVersion = pickVersion(fileVersions);
                        //sending the file to all devices to copy/replace with the last version
                        System.out.println("Sending version: " + lastVersion.getName() + " " + sdf.format(lastVersion.lastModified()));
                        //this file is synced in all devices therefore ignoring it in the other devices directories
                        sendFileToAll(directoryPaths, lastVersion.getAbsolutePath(), lastVersion.getName());
                        syncedFiles.add(appFile.getName());
                    } else System.out.println("--is synced");
                }
            }
        }
    }

    public static void Sync() throws IOException {
        System.out.println("Starting syncronization.");
        System.out.println("Syncronizing files on this device...");
        //get this device info
        SyncDevice("");//get path of folder to be synced on this device
        System.out.println("Syncronizing files on connected devices...");
        //for(devices in connected devices)
        //get device info
        SyncDevice("");//call this function for each device
        System.out.println("Syncronizing all devices...");
        SyncAllDevices();
        System.out.println("Finished syncronization");
    }
}