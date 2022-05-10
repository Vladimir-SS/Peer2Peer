import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class verificari {
    public static void main(String[] args) {

        File f= new File("D:\\git");
//        File f= new File("D:\\QT");
        try{
            System.out.println(MetadataFile.getAllData(f));
        }catch (Exception e)
        {
            System.out.println(e);
        }

        try {
           Map<String,File> fis = MetadataFile.getAllFileFromDir(f);
            String pathForFiles ="D:\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files";

           /// Sterg toate datele dintr-un fisier (  in caz ca se va schimba folderul pt push
            File dir= new File(pathForFiles);
           // MetadataFile.deleteFilesFromDirectory(dir);

           /// create all the
            MetadataFile.exportDirToJson(fis,pathForFiles);
            MetadataFile.updateAFile("sample.txt",pathForFiles,fis,LocalDate.now());
            //MetadataFile.updateAFile("sample.txt",pathForFiles,fis);
            //MetadataFile.updateAllFiles(pathForFiles,fis,LocalDate.now());

            //------------------------
            List<MetadataForUI> metadataForUIList=new ArrayList<>();
            //f.getPath() -> path to sync; can be modified to browse path to sync
            //now it searches in the entire folder selected to sync
            metadataForUIList=MetadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath());
            //noBck=MetadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath()+"\\pictures\\template\\raccoon_title.jpg");
            //noBck=MetadataForUI.getAllNoBackupFiles(pathForFiles, f.getPath()+"\\pictures\\template"); //-> returns all files in template folder, including template dir itself
            System.out.println("No backup files:");
            System.out.println(metadataForUIList);
            metadataForUIList=MetadataForUI.getNewFiles(pathForFiles);
            System.out.println("New files:");
            System.out.println(metadataForUIList);

        }catch (Exception e) {
            System.out.println(e);
        }

    }
}
