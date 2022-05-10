import java.io.File;
import java.time.LocalDate;
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
            MetadataFile.deleteFilesFromDirectory(dir);

           /// create all the
            MetadataFile.exportDirToJson(fis,pathForFiles);
            MetadataFile.updateAFile("cmake_install.cmake",pathForFiles,fis,LocalDate.now());
//            MetadataFile.updateAFile("cmake_install.cmake",pathForFiles,fis);
            MetadataFile.updateAllFiles(pathForFiles,fis,LocalDate.now());
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
