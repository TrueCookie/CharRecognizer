package com.company;

import java.awt.image.*;
import java.util.*;

public class CharReco {
    private static final double DEFAULT_WEIGHT = 0;
    private static final double DEFAULT_DELTA = 1;
    private static final double DEFAULT_TETTA = 30;

    private final int inBitCount = 64;
    private final int outBitCount = 26;

    private final int wCount = inBitCount*outBitCount;

    private final double[][] weights = new double[64][26];  //i - pixel iterator, j - result iterator
    private final double corrDelta;
    private final double tetta;

    int[] lastXValues = new int[inBitCount];

    int[] resArr = new int[outBitCount];

    public CharReco(double initWeight, double corrDelta, double tetta){
        this.corrDelta = corrDelta;
        this.tetta = tetta;
        for (int i = 0; i < inBitCount; i++) {
            for (int j = 0; j < outBitCount; j++) {
                weights[i][j] = initWeight;
            }
        }
    }

    public CharReco(){
        this.corrDelta = DEFAULT_DELTA;
        this.tetta = DEFAULT_TETTA;
        for (int i = 0; i < inBitCount; i++) {
            for (int j = 0; j < outBitCount; j++) {
                weights[i][j] = DEFAULT_WEIGHT;
            }
        }
    }

    public int[] recoEncChar(BufferedImage bi){
        int[] imgArr = ImgReader.initImgArr(bi);
        lastXValues = imgArr;

        for (int j = 0; j < outBitCount; j++) {
            double sum = 0;
            for (int i = 0; i < inBitCount; i++) {
                sum += imgArr[i]*weights[i][j];
            }

            resArr[j] = sum >= 0 ? 1 : 0;
        }

        return resArr;
    }

    public void train(int correctCharNum) {
        int[] correctEncChar = new int[outBitCount];
        correctEncChar[correctCharNum] = 1;

        //error vector init
        int[] errVector = new int[outBitCount];
        for (int j = 0; j < outBitCount; j++) {
            errVector[j] = correctEncChar[j] - resArr[j];
        }

        //reweight
        for (int j = 0; j < outBitCount; j++) {
            for (int i = 0; i < inBitCount; i++) {
                int deltaW = errVector[j] * lastXValues[i];
                weights[i][j] += deltaW;
            }
        }
    }

    public String encCharToChar(int[] encodedChar){
        int foundChar = -1;
        for (int i = 0; i < outBitCount; i++) {
            if(encodedChar[i] == 1 & foundChar == -1){
                foundChar = i;
            }else if(encodedChar[i] == 1 & foundChar != -1){
                return DataPreparator.numToChar.get(DataPreparator.VAR_CHAR_CD);
            }
        }

        return DataPreparator.numToChar.get(foundChar);
    }

    public void printWeights(int charNum){
        for (int i = 0; i < inBitCount; i++) {
            if (i % 8 == 0){ System.out.print("\n");}
            System.out.printf("%2.0f ", weights[i][charNum]);
        }
    }
}
