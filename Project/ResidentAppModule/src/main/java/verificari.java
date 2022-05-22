
import javax.xml.crypto.Data;

import com.google.gson.stream.JsonReader;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class verificari {
    public static void main(String[] args) {

        File f= new File("F:\\testulet");

//        File f= new File("D:\\QT");
        String pathForFiles ="F:\\GitHub\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files";
        String pathForFiles2 ="F:\\GitHub\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files2";
        /// Sterg toate datele dintr-un fisier (  in caz ca se va schimba folderul pt push
        File dir= new File(pathForFiles);
//        MetadataFile.deleteFilesFromDirectory(dir);
        dir= new File(pathForFiles2);
//        MetadataFile.deleteFilesFromDirectory(dir);
        dir= new File(pathForFiles);
        try{
            System.out.println(MetadataFile.getAllData(f));
        }catch (Exception e)
        {
            System.out.println(e);
        }

        try {
            Map<String,File> fis = MetadataFile.getAllFileFromDir(f);


            System.out.println("start here");
//            MetadataFile.updateAllFiles(pathForFiles,fis, ZonedDateTime.now());
//            MetadataFile.updateAllFiles(pathForFiles2,fis, ZonedDateTime.now());
            System.out.println("done here");


            /// create all the
//            MetadataFile.exportDirToJson(fis,pathForFiles);
//            MetadataFile.exportDirToJson(fis,pathForFiles2);
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk-mm-ss");
            DatasFromJsons datasFromJsonsServ= new DatasFromJsons(pathForFiles2);/// in cel in care am mai mult
            DatasFromJsons datasFromJsons= new DatasFromJsons(pathForFiles);

            //deci eu as vrea sa fac conexiunea

//            new DatasFromJsons(pathForFiles).seeAllFileWithData();
            DatasFromJsons datasFromJsons2 = new DatasFromJsons(pathForFiles2);
//            System.out.println(datasFromJsons.exportJSon());

            System.out.println(datasFromJsons.fileShouldIGet(datasFromJsons2.exportJSon()));

//            MetadataFile.updateAFile("sample.txt",pathForFiles,fis,LocalDate.now());
            //MetadataFile.updateAFile("sample.txt",pathForFiles,fis);


//            System.out.println("Data locala este >>" +LocalDateTime.now());



//           Map<String, Map<String,String>> a= datasFromJsons.fileShouldIGet("F:\\GitHub\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files");
//            System.out.println("fisiere care sunt de modificat - ");












            //------------------------
            List<MetadataForUI> metadataForUIList=new ArrayList<>();
            //f.getPath() -> path to sync; can be modified to browse path to sync
            //now it searches in the entire folder selected to sync
            metadataForUIList=metadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath());
            //noBck=MetadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath()+"\\pictures\\template\\raccoon_title.jpg");
            //noBck=MetadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath()+"\\pictures\\template"); //-> returns all files in template folder, including template dir itself
            MetadataFile.commit(pathForFiles, f.getPath());

            System.out.println("No backup files:");
            System.out.println(metadataForUIList);
            metadataForUIList=metadataForUI.getNewFiles(pathForFiles);
            System.out.println("New files:");
            System.out.println(metadataForUIList);
            metadataForUIList=metadataForUI.getSyncedFiles(pathForFiles);
            System.out.println("Synced files:");
            System.out.println(metadataForUIList);

            System.out.println("Getter demo:");
            metadataForUIList.stream().forEach(i->System.out.println(i.getName()));

        }catch (Exception e) {
            System.out.println(e);
        }

    }

}
