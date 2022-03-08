package com.company;

import java.awt.image.*;
import java.io.*;
import java.util.*;

public class DataPreparator {

    public static final int NON_CHAR_CD = -1;
    public static final int VAR_CHAR_CD = 202;

    public static final Map<String, Integer> charToNum;
    static {
        charToNum = new HashMap<>();
        charToNum.put("A", 1);
        charToNum.put("B", 2);
        charToNum.put("C", 3);
        charToNum.put("D", 4);
        charToNum.put("E", 5);
        charToNum.put("F", 6);
        charToNum.put("G", 7);
        charToNum.put("H", 8);
        charToNum.put("I", 9);
        charToNum.put("J", 10);
        charToNum.put("K", 11);
        charToNum.put("L", 12);
        charToNum.put("M", 13);
        charToNum.put("N", 14);
        charToNum.put("O", 15);
        charToNum.put("P", 16);
        charToNum.put("Q", 17);
        charToNum.put("R", 18);
        charToNum.put("S", 19);
        charToNum.put("T", 20);
        charToNum.put("U", 21);
        charToNum.put("V", 22);
        charToNum.put("W", 23);
        charToNum.put("X", 24);
        charToNum.put("Y", 25);
        charToNum.put("Z", 26);

        charToNum.put("0", NON_CHAR_CD);
        charToNum.put("2", VAR_CHAR_CD);
    }

    public static final Map<Integer, String> numToChar;
    static {
        numToChar = new HashMap<>();
        numToChar.put(1,"A");
        numToChar.put(2,"B");
        numToChar.put(3,"C");
        numToChar.put(4,"D");
        numToChar.put(5,"E");
        numToChar.put(6,"F");
        numToChar.put(7,"G");
        numToChar.put(8,"H");
        numToChar.put(9,"I");
        numToChar.put(10,"J");
        numToChar.put(11,"K");
        numToChar.put(12,"L");
        numToChar.put(13,"M");
        numToChar.put(14,"N");
        numToChar.put(15,"O");
        numToChar.put(16,"P");
        numToChar.put(17,"Q");
        numToChar.put(18,"R");
        numToChar.put(19,"S");
        numToChar.put(20,"T");
        numToChar.put(21,"U");
        numToChar.put(22,"V");
        numToChar.put(23,"W");
        numToChar.put(24,"X");
        numToChar.put(25,"Y");
        numToChar.put(26,"Z");

        numToChar.put(NON_CHAR_CD, "0");
        numToChar.put(VAR_CHAR_CD, "2");
    }

    private static ArrayList<Object> generateLearnMapEntry(File fileEntry){
        String filePath = fileEntry.getPath();
        BufferedImage bufferedImage = ImgReader.readImg(filePath);
        String fileNameAnswer = parseFileName(filePath);

        return new ArrayList<>(){
            {
                add(bufferedImage);
                add(fileNameAnswer);
            }
        };
    }

    public static HashSet<ArrayList<Object>> getMarkedData(final File directory) {
        HashSet<ArrayList<Object>> markedData = new HashSet<>();
        for (final File fileEntry : Objects.requireNonNull(directory.listFiles())) {
            if (fileEntry.isDirectory()) {
                getMarkedData(fileEntry);
            } else {
                markedData.add(generateLearnMapEntry(fileEntry));
            }
        }
        return markedData;
    }

    public static HashSet<ArrayList<Object>> getMarkedData(String dirPath) {
        File dirFile = new File(dirPath);
        return getMarkedData(dirFile);
    }

    private static String parseFileName(String fileName){
        int len = fileName.length();
        int nameIndex = fileName.lastIndexOf('\\');
        String nameNumber = fileName.substring(nameIndex+1 ,len-4);

        int underscoreIndex = nameNumber.indexOf('_');
        if(underscoreIndex != -1){
            nameNumber = nameNumber.substring(0, underscoreIndex);
        }

        return nameNumber;
    }

    private static int getCharNum(char character){
        return charToNum.get(String.valueOf(character));
    }
}
