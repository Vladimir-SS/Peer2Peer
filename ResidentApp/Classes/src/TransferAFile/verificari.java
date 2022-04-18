package TransferAFile;

import java.io.File;

import static TransferAFile.MetadataFile.getAllData;

public class verificari {
    public static void main(String[] args) {
        File f= new File("D:\\git\\GitHub\\Tw\\Animals\\index.html");

        try{
            System.out.println(getAllData(f));
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
