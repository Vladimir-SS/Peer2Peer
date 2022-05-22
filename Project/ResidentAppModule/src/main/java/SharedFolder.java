import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SharedFolder {
    private static Map<String,String> sharedFolderPath = new HashMap<>(); // key=folder path, value=file info path

    // this function returns the path to the shared folder and the path to the info file about folder
    public static void sharedFolder(File dir) throws IOException {
        String folderPath = dir.getAbsolutePath();
        Map<String,String> infoFolder = MetadataFile.getAllData(dir);

        MetadataFile.exportDirToJson(MetadataFile.getAllFileFromDir(dir),folderPath);
        String infoFilePath = dir.getAbsolutePath() + "\\allFiles.json";
        Map<String,String> aboutFiles = new HashMap<>();
        aboutFiles.put("aboutFilesPath", infoFilePath);

        BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(folderPath + "\\" + dir.getName()+ "Info.json"))); //the file info about folder that has at the end the path to the allfiles.json
        Gson gson = new Gson();
        String json = gson.toJson(infoFolder);
        bw.write(json);
        bw.write("\n");
        String json2 = gson.toJson(aboutFiles);
        bw.write(json2);
        bw.write("\n");


        sharedFolderPath.put(folderPath, folderPath + "\\" + dir.getName() + "Info.json");
    }
}
