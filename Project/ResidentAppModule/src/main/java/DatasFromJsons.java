import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasFromJsons extends MetadataFile {
//    private Map<String, File> allFilesFromJsons= new HashMap<>(); // nume cu extensie si File-ul propriu zis
    private Map<String, Map<String, String>> fileWithData= new HashMap<>(); // File-ul propriu-zis SI map cu Atribut valoare;
    private String pathForDirectory;
// exemplu D:\Aplications

    public String getPathForDirectory() {
        return pathForDirectory;
    }



    // Functie care creeaza un Map avand ca si cheie numele fisierului cu extensie, iar valoarea este un alt map care
    public Map<String, Map<String, String>> getFileWithData() {
        return fileWithData;
    }
    public DatasFromJsons(String pathForDirectory) {  ///
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
            assert filesList != null;
            for(File file : filesList)
            {
                if(!file.getName().equals("numberOfFiles.txt") && !file.getName().equals("allFiles.json")) {
                    try {
                        if (!file.isDirectory()) {
                            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
                            String line = reader.readLine();
                            Gson gson = new Gson();
                            Map<String, String> map = gson.fromJson(line, Map.class);
//                            allFilesFromJsons.put(map.get("name"), new File(map.get("path")));
                            fileWithData.put(map.get("name"), map);

                        } else {
                            //todo exception
                            System.out.println("Nu este un folder");
                        }
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }}

    public void seeAllFileWithData(){
        Map<String, Map<String, String>>  map = getFileWithData();
        for(String key : map.keySet())
        {
            System.out.println("key-> "+ key );
            for(String key2 :map.get(key).keySet())
                System.out.println("\t"+key2 + "->"+ map.get(key).get(key2));
        }
    }

    public String exportJSon()
    {
        Gson gson= new Gson();
        return gson.toJson(this);

    }
    private DatasFromJsons stringToClass(String jsonString)
    {
        Gson gson=new Gson();
        return gson.fromJson(jsonString, DatasFromJsons.class);
    }

//    private boolean hasToGetUpload(Map<String, String> dataClient2,Map<String, String> dataClient1 ){
//        List<String> listWithParameters = new ArrayList<>();
//        listWithParameters.add("lastPush");
//        listWithParameters.add("size");
//        listWithParameters.add("lastModifiedTime");
//        if(Integer.parseInt(dataClient2.get(listWithParameters.get(1))) > Integer.parseInt(dataClient1.get(listWithParameters.get(1)))){
//
//            if(!(dataClient1.get(listWithParameters.get(0)).equals(null) || dataClient1.get(listWithParameters.get(0)).equals("")) && !(dataClient2.get(listWithParameters.get(0)).equals(null) || dataClient2.get(listWithParameters.get(0)).equals("")))
//            {
//
//                ZonedDateTime c1 = ZonedDateTime.parse(dataClient1.get(listWithParameters.get(0)));
//                System.out.println(c1);
//                ZonedDateTime  c2 = ZonedDateTime.parse(dataClient2.get(listWithParameters.get(0)));
//                System.out.println(c2);
//                if(c2.isAfter(c1))
//                {
//                    return true;
//                }
//                else return true;
//            }
//            else
//            {
//
////                if(!(dataClient1.get(listWithParameters.get(0)).equals(null) || dataClient1.get(listWithParameters.get(0)).equals("")))
////                {
//                ZonedDateTime  c1 =ZonedDateTime.parse(dataClient1.get(listWithParameters.get(2)));
//                ZonedDateTime  c2 =ZonedDateTime.parse(dataClient2.get(listWithParameters.get(2)));
//                    if(c2.isAfter(c1))
//                    {
//                        return true;
//                    }
//                    else return true;
////                }
//            }
//        }
//        else
//        {
//            ZonedDateTime  c1 =ZonedDateTime.parse(dataClient1.get(listWithParameters.get(2)));
//            ZonedDateTime  c2 =ZonedDateTime.parse(dataClient2.get(listWithParameters.get(2)));
//            if(c2.isAfter(c1))
//            {
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean hasToGetUpload(Map<String, String> dataClient2,Map<String, String> dataClient1 )
    {
            ZonedDateTime  c1 = ZonedDateTime.parse(dataClient1.get("lastModifiedTime"));
            ZonedDateTime  c2 = ZonedDateTime.parse(dataClient2.get("lastModifiedTime"));
            if(c2.isAfter(c1))
            {
                return true;
            }
            return false;
    }


    public Map<String,Map<String,String>> fileShouldIGet(String otherClientJsonData)
    {

        Map<String,Map<String, String>> response = new HashMap<>();
        DatasFromJsons client2= stringToClass(otherClientJsonData);
        Map<String,Map<String, String>> infoClient2 = client2.getFileWithData();
//        client2.seeAllFileWithData();

        for(String name2 : infoClient2.keySet())
        {
            boolean foundIt = false;
            for(String name1 : this.fileWithData.keySet())
                if(name2.equals(name1))
                {
                    foundIt= true;
                    System.out.println(name1+" si "+name2+
                            " founded");
                    break;
                }
            if(!foundIt) response.put(name2, infoClient2.get(name2));
            else {
                Map<String, String > dataClient2= infoClient2.get(name2);
//                System.out.println("\t\t "+dataClient2);
                Map<String,String> dataClient1= fileWithData.get(name2);
//                System.out.println("\t\t "+dataClient1);
//
                System.out.println(hasToGetUpload(dataClient2,dataClient1));
                if(hasToGetUpload(dataClient2,dataClient1)) {

                    response.put(name2, infoClient2.get(name2));
                }
            }
        }
        return response;
    }
}
