package lzw_tor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    public static byte[] readFileToByteArray(String filePath, int bufferSize) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[bufferSize];
        int bytesRead = fis.read(buffer);
        fis.close();
        return buffer;
    }
}
