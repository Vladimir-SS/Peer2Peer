package TransferAFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class MetadataFile {

    public Map<String, String> getAllData(File f) throws IOException {
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
            fileData.put("creationTime",String.valueOf(view.creationTime()));
            fileData.put("creationTime",String.valueOf(view.creationTime()));
            fileData.put("creationTime",String.valueOf(view.creationTime()));
            fileData.put("creationTime",String.valueOf(view.creationTime()));

        }
        else{
            System.out.println("File doesn't exist");
        }
        return fileData;

    }

    public static void main(String[] args) {
        File f= new File("D:\\git\\GitHub\\Tw\\Animals\\index.html");
        System.out.println(getAllData(f));
    }
}
