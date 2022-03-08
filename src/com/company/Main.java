package com.company;

import java.awt.image.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Main {
    private enum Mode{
        TRAIN,
        TEST
    }

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startTime = dtf.format(now);

        HashSet<ArrayList<Object>> markedData = DataPreparator.getMarkedData("resources/learn");

        CharReco charReco = new CharReco();

        int teachIterCount = traceSamples(charReco, markedData, Mode.TRAIN);

        //charReco.printWeights();
        System.out.println(teachIterCount);

        String endTime = dtf.format(now);
        System.out.println("Start Time" + startTime);
        System.out.println("End Time: " + endTime);

        //TEST
        System.out.println("<<<<<<<<<<<<<<<<<<<<<______________TEST____________>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        HashSet<ArrayList<Object>> testData = DataPreparator.getMarkedData("resources/test");
        int testIterCount = traceSamples(charReco, testData, Mode.TEST);

        System.out.println(testIterCount);
    }

    private static int traceSamples(CharReco charReco, HashSet<ArrayList<Object>> markedData, Mode mode){
        int iterCount = 0;
        boolean mistakeFlag;

        do{
            mistakeFlag = false;
            for (ArrayList<Object> sample : markedData){
                BufferedImage sampleBI = (BufferedImage) sample.get(0);

                int[] resEncChar = charReco.recoEncChar(sampleBI);
                String resChar = charReco.encCharToChar(resEncChar);
                String answerChar = sample.get(1).toString();

                if (!resChar.equals(answerChar)) {
                    if(mode == Mode.TRAIN){
                        charReco.train(DataPreparator.charToNum.get(answerChar));
                        mistakeFlag = true;
                    }
                }
                printReport(resChar, answerChar);

                //charReco.printWeights();
            }
            System.out.println("----------NEXT-ERA--------------------------->");
            iterCount++;
        }while (mistakeFlag);    //If there was any misrecognizing training will repeat

        return iterCount;
    }

    private static void printReport(String result, String answer){
        String equalityRes = result.equals(answer) ? "SUCCESS" : "FAIL";

        System.out.println("result: " + result);
        System.out.println("answer: " + answer + " - " + equalityRes);
    }

}
