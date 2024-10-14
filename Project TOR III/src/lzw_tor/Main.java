package lzw_tor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            String mkvFilePath = "C:\\Users\\Desktop\\src\\lzw_tor\\videoplayback.mkv";
            String mp4FilePath = "C:\\Users\\Desktop\\src\\lzw_tor\\videoplayback.mp4";

            byte[] fileBytes = readFileToByteArray(mkvFilePath);

            StringBuilder bitStringBuilder = new StringBuilder();
            for (byte b : fileBytes) {
                bitStringBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }
            String bitString = bitStringBuilder.toString();

            LZW lzw = new LZW();
            String compressedBitString = lzw.lzw_compress(bitString);
            String decompressedBitString = lzw.lzw_extract(compressedBitString);


            long originalSize = fileBytes.length;
            long compressedSize = compressedBitString.length() / 8;

            System.out.println("Original MKV Size: " + originalSize + " bytes");
            System.out.println("Compressed Size: " + compressedSize + " bytes");

            File mp4File = new File(mp4FilePath);
            long mp4Size = mp4File.length();

            System.out.println("MP4 Size: " + mp4Size + " bytes");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readFileToByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }
}
