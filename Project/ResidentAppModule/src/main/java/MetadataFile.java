import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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
            fileData.put("lasPush","");
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
        if(dates.get("isDirectory")=="true") {
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
    public static void updateAFile(String nameWithExtension,String pathForFiles, Map<String, File> data, LocalDate whenPushed)throws  IOException{

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
    public static void updateAllFiles(String pathForFiles, Map<String, File> data, LocalDate whenPushed)throws  IOException{
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
        if(!(fileA.get("name").equals(fileB.get("name"))
                ||fileA.get("size").equals(fileB.get("size"))||
                fileA.get("lastModifiedTime").equals(fileB.get("lastModifiedTime"))))
            return false;
        return true;
    }



}
