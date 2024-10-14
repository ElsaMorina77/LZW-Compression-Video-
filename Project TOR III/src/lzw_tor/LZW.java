package lzw_tor;

import java.util.HashMap;
import java.util.ArrayList;

public class LZW {

    public String lzw_compress(String input) {
        HashMap<String, Integer> dictionary = new HashMap<>();
        int dictSize = 256;

        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }

        String w = "";
        ArrayList<Integer> result = new ArrayList<>();

        for (char c : input.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc)) {
                w = wc;
            } else {
                result.add(dictionary.get(w));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        if (!w.equals("")) {
            result.add(dictionary.get(w));
        }

        StringBuilder bitString = new StringBuilder();
        for (int code : result) {
            bitString.append(String.format("%16s", Integer.toBinaryString(code)).replace(' ', '0'));
        }

        // Add padding to make the bit string length a multiple of 16
        int paddingLength = (16 - (bitString.length() % 16)) % 16;
        if (paddingLength > 0) {
            bitString.append("0".repeat(paddingLength));
        }

        return bitString.toString();
    }

    public String lzw_extract(String input) {
        HashMap<Integer, String> dictionary = new HashMap<>();
        int dictSize = 256;

        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }

        if (input.length() % 16 != 0) {
            throw new IllegalArgumentException("Input bit string length must be a multiple of 16");
        }

        int code = Integer.parseInt(input.substring(0, 16), 2);
        String w = "" + (char) code;
        StringBuilder result = new StringBuilder(w);

        int k;
        String entry;
        for (int i = 16; i < input.length(); i += 16) {
            k = Integer.parseInt(input.substring(i, i + 16), 2);
            if (dictionary.containsKey(k)) {
                entry = dictionary.get(k);
            } else if (k == dictSize) {
                entry = w + w.charAt(0);
            } else {
                throw new IllegalArgumentException("Bad compressed k: " + k);
            }

            result.append(entry);

            dictionary.put(dictSize++, w + entry.charAt(0));
            w = entry;
        }

        // Remove any trailing padding bits
        int paddingIndex = result.indexOf("0");
        if (paddingIndex != -1) {
            result.setLength(paddingIndex);
        }

        return result.toString();
    }
}
