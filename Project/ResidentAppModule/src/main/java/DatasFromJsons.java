import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatasFromJsons extends MetadataFile{
    private Map<String, File> allFilesFromJsons= new HashMap<>(); // nume cu extensie si File-ul propriu zis
    private Map<String, Map<String, String>> fileWithData= new HashMap<>(); // File-ul propriu-zis SI map cu Atribut valoare;
    private String pathForDirectory;
// exemplu D:\Aplications


    public Map<String, Map<String, String>> getFileWithData() {
        return fileWithData;
    }

    public DatasFromJsons(String pathForDirectory) {
        this.pathForDirectory = pathForDirectory;
        File folder = new File(pathForDirectory);


        if(!folder.isDirectory())
        {
            System.out.println("Nu este director, ai selectat gresit");
            //todo exception
        }
        else
        {
            File[] filesList = folder.listFiles();
            for(File file : filesList)
            {
                try {
                    if(!file.isDirectory()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                        String line = reader.readLine();
                        Gson gson = new Gson();
                        Map<String, String> map = gson.fromJson(line,Map.class);
                        allFilesFromJsons.put(map.get("name"),new File(map.get("path")));
                        fileWithData.put(map.get("name"),map);
;
//                        System.out.println(map.get("name")+" ->" +fileWithData.get(map.get("name")));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }





    }
}
