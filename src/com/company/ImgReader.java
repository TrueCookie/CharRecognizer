package com.company;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImgReader {

    private static int NONE = 0;
    private static int BLACK = -16777216;

    public static BufferedImage readImg(String path){
        File img = new File(path);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }

    public static int isBlack(BufferedImage bi, int x, int y){
        if(bi.getRGB(x, y) == BLACK){
            return 1;
        }else{
            return 0;
        }
    }

    public static int[] initImgArr(BufferedImage bi){
        int[] pixels = new int[64];
        int k = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pixels[k] = isBlack(bi, j, i);
                k++;
            }
        }

        return pixels;
    }

    public static void printImgArr(int[] imgArr){
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0){ System.out.print("\n");}
            System.out.print(imgArr[i] + " ");
        }
    }

    public static void printImgArr(BufferedImage bi){
        printImgArr(initImgArr(bi));
    }
}
