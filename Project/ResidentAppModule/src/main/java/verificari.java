import Connectivity.ConnectionsManager;
import Connectivity.Peer;
import com.misc.DataController;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class verificari {

    public static void main(String[] args) {

        // foldere pe care vrea sa le actualizez
         File f = new File("D:\\files1");
         File f2= new File("D:\\files2");


        // imediat ce se alege folderul care ar trebui sa fie dat update  ar trebui sa se face delete la tot ce este in fisierul deconfiguratie

        final String pathForFirstFolder= new String("D:\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files");
        final String pathForSecondFolder = new String("D:\\IP\\IPProjectB2\\Project\\ResidentAppModule\\src\\main\\java\\Files2");


        File dirForFirstFolder = new File(pathForFirstFolder);
        File dirForSecondFolder = new File(pathForSecondFolder);

//        MetadataFile.deleteFilesFromDirectory(dirForFirstFolder);
//        MetadataFile.deleteFilesFromDirectory(dirForSecondFolder);

        /// used for the first time when we select a folder to get synced
        Map<String, File> dataFolder1=new HashMap<>();
        Map<String,File> dataFolder2= new HashMap<>();
        try {
            dataFolder1  = MetadataFile.getAllFileFromDir(f);
           dataFolder2 = MetadataFile.getAllFileFromDir(f2);


//            MetadataFile.exportDirToJson(dataFolder1,pathForFirstFolder);
//            MetadataFile.exportDirToJson(dataFolder2,pathForSecondFolder);

        } catch (IOException e) {
            e.printStackTrace();
        }


        DatasFromJsons datasFromJsonsServ = new DatasFromJsons(pathForSecondFolder);
        DatasFromJsons datasFromJsons = new DatasFromJsons(pathForFirstFolder);



        // Luam toate datele de la fisierele
        try {
            MetadataFile.updateAllFiles(pathForFirstFolder,dataFolder1);
            MetadataFile.updateAllFiles(pathForSecondFolder,dataFolder2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println( datasFromJsons.getFileWithData());
        System.out.println(datasFromJsonsServ.getFileWithData());

        Map<String, Map<String, String>> data = datasFromJsons.fileShouldIGet(datasFromJsonsServ.exportJSon());

        System.out.println(data);


        System.out.println(DataController.getConnectedDevices());
        System.out.println(DataController.getLastFoundDevices().size());

    }

}
