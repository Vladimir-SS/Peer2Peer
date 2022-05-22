import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;


public class MetadataFile {

    public static Map<String, String> getAllData(File f) throws IOException {
        Map<String, String> fileData=new HashMap<>();

        if(f.exists())
        {
            fileData.put("name",f.getName());
            fileData.put("path",f.getAbsolutePath());
            fileData.put("size",String.valueOf(f.length()));
            fileData.put("writeable",String.valueOf(f.canWrite()));
            fileData.put("readable",String.valueOf(f.canRead()));
            Path file= Paths.get(f.getAbsolutePath());
            BasicFileAttributes view= Files.readAttributes(file, BasicFileAttributes.class);
            fileData.put("creationTime",String.valueOf(view.creationTime()));
            fileData.put("lastAccessTime",String.valueOf(view.lastAccessTime()));
            fileData.put("lastModifiedTime",String.valueOf(view.lastModifiedTime()));
            fileData.put("isDirectory",String.valueOf(view.isDirectory()));
            fileData.put("isOther",String.valueOf(view.isOther()));
            fileData.put("size2",String.valueOf(view.size()));
            fileData.put("lastPush","");
        }
        else{
            System.out.println("File doesn't exist");
        }
        return fileData;

    }
    public static Map<String,File> getAllFileFromDir(File dir) throws IOException
    {
        Map<String,String> dates = getAllData(dir);
        String[] pathnames;
        if(Objects.equals(dates.get("isDirectory"), "true")) {
            Map<String,File> allFiles= new HashMap<>();
            pathnames=dir.list();
            for (String pathname : pathnames ){
                String path = dates.get("path") + "\\"+pathname;
                File fis= new File(path);
                allFiles.put(pathname,fis);

                Map<String, String> aux = getAllData(fis);
                if(aux.get("isDirectory")=="true")
                {
                    allFiles.putAll(getAllFileFromDir(fis));
                }
            }
            return allFiles;
        }
        else
        {
            System.out.println("nu este un director");
            return null;
        }


    }

    public static boolean isDir(File dir) throws IOException
    {
//     directory = path catre folder-ul unde se vor salva fisierele
        Path file= Paths.get(dir.getAbsolutePath());
        BasicFileAttributes view= Files.readAttributes(file, BasicFileAttributes.class);
        return view.isDirectory();
    }

    public static void exportDirToJson( Map<String, File> mapWithInfo, String path) throws IOException
    {
//     directory = path catre folder-ul unde se vor salva fisierele
        try {
            int contor=0;
            BufferedWriter bw = null;
            for(String key:mapWithInfo.keySet())
            {
                // crearea fisierului
                StringBuilder numeFisier=new StringBuilder();
                numeFisier.append(path);numeFisier.append("\\");
                StringTokenizer st = new StringTokenizer(key,".");
                numeFisier.append(st.nextToken());
                numeFisier.append(".json");
                bw = new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));

                //Scriere format json
                Gson gson = new Gson();
                String json = gson.toJson(MetadataFile.getAllData(mapWithInfo.get(key)));
                bw.write(json);
                bw.write("\n");
                contor++;

                bw.close();
            }

            StringBuilder numeFisier=new StringBuilder();
            numeFisier.append(path);numeFisier.append("\\");
            numeFisier.append("numberOfFiles.txt");
            bw= new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));
            bw.write(String.valueOf(contor));
            bw.close();
            numeFisier=new StringBuilder();
            numeFisier.append(path);numeFisier.append("\\");
            numeFisier.append("allFiles.json");
            bw= new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));
            for(String key:mapWithInfo.keySet())
            {
                //Scriere format json
                Gson gson = new Gson();
                String json = gson.toJson(MetadataFile.getAllData(mapWithInfo.get(key)));
                bw.write(json);
                bw.write("\n");



            }
            bw.close();

        }catch (Exception e)
        {
            System.out.println(e);
        }

    }




    public static void deleteFilesFromDirectory(File directory){
        File filesList[] = directory.listFiles();
        for(File file : filesList)
        {
            file.delete();
        }
    }
    public static void updateAFile(String nameWithExtension,String pathForFiles, Map<String, File> data)throws  IOException {updateAFile(nameWithExtension,pathForFiles,data,null);

    }
    public static void updateAFile(String nameWithExtension,String pathForFiles, Map<String, File> data, LocalDateTime whenPushed)throws  IOException{

        System.out.println(data);
        File fisier= data.get(nameWithExtension);
        if(fisier.isFile()) {
            System.out.println("Update started");
            StringBuilder numeFisier=new StringBuilder();
            numeFisier.append(pathForFiles);numeFisier.append("\\");
            StringTokenizer st = new StringTokenizer(nameWithExtension,".");
            numeFisier.append(st.nextToken());
            numeFisier.append(".json");

            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));
            //Scriere format json

            Gson gson = new Gson();
            Map<String,String> aux = new HashMap<>();

            aux= MetadataFile.getAllData(data.get(nameWithExtension));
            System.out.println("Before\n"+aux);
            aux.put("lastPush",String.valueOf(whenPushed));
            System.out.println("After\n"+aux);
            String json = gson.toJson(aux);
            bw.write(json);
            bw.write("\n");
            bw.close();


            numeFisier=new StringBuilder();
            numeFisier.append(pathForFiles);numeFisier.append("\\");
            numeFisier.append("allFiles.json");
            bw= new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));
            for(String key:data.keySet())
            {
                //Scriere format json
                 gson = new Gson();
                 if(key.equals(nameWithExtension))
                 {
                     aux = MetadataFile.getAllData(data.get(key));
                     aux.put("lastPush",String.valueOf(whenPushed));
                     json = gson.toJson(aux);
                 }
                 else json = gson.toJson(MetadataFile.getAllData(data.get(key)));
                bw.write(json);
                bw.write("\n");



            }
            bw.close();



        }
        //TODO
        else System.out.println("Something is wrong");

    }
    public static void updateAllFiles(String pathForFiles, Map<String, File> data, ZonedDateTime whenPushed)throws  IOException{
        //     directory = path catre folder-ul unde se vor salva fisierele

        try {
            int contor=0;
            BufferedWriter bw = null;
            for(String key:data.keySet())
            {
                // crearea fisierului
                StringBuilder numeFisier=new StringBuilder();
                numeFisier.append(pathForFiles);numeFisier.append("\\");
                StringTokenizer st = new StringTokenizer(key,".");
                numeFisier.append(st.nextToken());
                numeFisier.append(".json");
                bw = new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));

                //Scriere format json
                Gson gson = new Gson();
                Map<String,String> aux = new HashMap<>();
                aux= MetadataFile.getAllData(data.get(key));
//                System.out.println("Before\n"+aux);
                aux.put("lastPush",String.valueOf(whenPushed));
                String json = gson.toJson(aux);
                bw.write(json);
                bw.write("\n");
                contor++;

                bw.close();
            }

            StringBuilder numeFisier=new StringBuilder();
            numeFisier=new StringBuilder();
            numeFisier.append(pathForFiles);numeFisier.append("\\");
            numeFisier.append("allFiles.json");
            bw= new BufferedWriter(new FileWriter(String.valueOf(numeFisier)));
            for(String key:data.keySet())
            {
                //Scriere format json
                Gson gson = new Gson();
                Map<String,String> aux = new HashMap<>();

                aux= MetadataFile.getAllData(data.get(key));
//                System.out.println("Before\n"+aux);
                aux.put("lastPush",String.valueOf(whenPushed));

                String json = gson.toJson(aux);
                bw.write(json);
                bw.write("\n");



            }
            bw.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
    public static void updateAllFiles(String pathForFiles, Map<String, File> data)throws  IOException{
        updateAllFiles(pathForFiles,data, null);
    }
    public static boolean isFilesEquals(File firstFile, File secondFile)throws IOException {
        Map<String,String> fileA = new HashMap<>();
        fileA=getAllData(firstFile);
        Map<String,String> fileB = new HashMap<>();
        fileB=getAllData(secondFile);
//        boolean isEq=true;
        return fileA.get("name").equals(fileB.get("name"))
                || fileA.get("size").equals(fileB.get("size")) ||
                fileA.get("lastModifiedTime").equals(fileB.get("lastModifiedTime"));
    }


    //pathForFiles: path to .json files
    //pathToSharedDir: path to the dir that is being shared on this device
    // reface fis. cu .json:
    //      -- fis vechi sunt sterse
    //      -- fis noi sunt adaugate
    //      -- pt fis care deja existau sunt "refacute" toate prop in afara de lastPush
    public static void commit(String pathForFiles, String pathToSharedDir) throws IOException {
        File directory = new File(pathForFiles);
        String[] jsonFileNames = directory.list();
        Map<String, String> lastPushValues = new HashMap<>();

        // luam lastPush pt fiecare fis
        for(int i=0; i<jsonFileNames.length; i++) {
            if(!jsonFileNames[i].equals("allFiles.json")&&jsonFileNames[i].contains(".json")){
                JsonElement tree;
                JsonObject element=new JsonObject();
                try (Reader reader = Files.newBufferedReader(Paths.get(pathForFiles + "//" + jsonFileNames[i]))) {      //just reads all .json
                    JsonParser parser = new JsonParser();
                    tree = parser.parse(reader);
                    element=tree.getAsJsonObject();
                    if(!element.get("lastPush").getAsString().equals(""))
                        lastPushValues.put(element.get("path").getAsString(), element.get("lastPush").getAsString());       // in lastPushValues vom avea toate fis care au lastPush setat
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong when fetching info from infoFiles)");
                }
            }
        }

        //stergem toate fis json + numberOfFiles
        deleteFilesFromDirectory(directory);

        // refacem fis json (fis. care aveau lastPush setat il au acum ""
        File f = new File(pathToSharedDir);
        Map<String, File> sharedFiles = getAllFileFromDir(f);
        exportDirToJson(sharedFiles, pathForFiles);

        // pt toate fisierele care deja existau, rescriem val lui lastPush
        for(Map.Entry<String, String> lastPush: lastPushValues.entrySet()){     //toate val lui lastPush inainte sa refacem fis cu json
                //System.out.println(lastPush.getKey());
                Path p=Paths.get(lastPush.getKey());
                String fileN=new String(p.getFileName().toString());
                if(sharedFiles.containsKey(fileN))          //verif. daca fis inca exista
                    if(sharedFiles.get(fileN).getPath().equals(lastPush.getKey()))      //verifica path-ul sa fie identic
                        updateAFile(fileN, pathForFiles, sharedFiles, LocalDateTime.from(LocalDate.parse(lastPush.getValue())));    //update lastPush
        }
    }
}
