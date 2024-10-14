package lzw_tor;

public class BitUtils {
    public static String bytesToBits(byte[] byteArray, int length) {
        StringBuilder bits = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bits.append(String.format("%8s", Integer.toBinaryString(byteArray[i] & 0xFF)).replace(' ', '0'));
        }
        return bits.toString();
    }
}

