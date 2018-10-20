package edu.technopolis.advjava;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void customStringTests() {
        CustomString sEmpty = new CustomString();
        System.out.println("Empty: " + sEmpty);

        CustomString s1 = new CustomString("Some-text-here");
        System.out.println("Some text: " + s1);

        System.out.println("Subsequences: ");
        for (int i = 0; i < s1.length(); i++) {
            System.out.println(s1.subSequence(0, s1.length() - i));
        }

        System.out.println("Char at: ");
        for (int i = 0; i < s1.length(); i++) {
            System.out.println(s1.charAt(i));
        }
    }

    public static void speedLargeTests() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 10e6; i++) {
            strBuilder.append("A");
        }

        String s = strBuilder.toString();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String s2 = s.substring(i * 10, 1000000 - 1 - i * 10);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("String substrings: " + elapsedTime);

        CustomString str = new CustomString(strBuilder.toString());
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            CharSequence s2 = (str.subSequence(i * 10, 1000000 - 1 - i * 10));
        }
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Custom string subsequences: " + elapsedTime);
    }

    public static void speedSmallTests() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 10e6; i++) {
            strBuilder.append("A");
        }

        String s = strBuilder.toString();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            String s2 = s.substring(i * 1000, 1000000 - 1 - i * 1000);
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("String substrings: " + elapsedTime);

        CustomString str = new CustomString(strBuilder.toString());
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            CustomString s2 = str.subSequence(i * 1000, 1000000 - 1 - i * 1000);
        }
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Custom string subsequences: " + elapsedTime);
    }

    public static void anotherTest() {

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 500000; i++) {
            strBuilder.append("MOYA OBORONA");
        }

        String s = strBuilder.toString();
        CustomString cs = new CustomString(strBuilder.toString());

        long startStrTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            s = s.substring(i * 1000, s.length());
        }
        long endStrTime = System.currentTimeMillis();

        long startCSTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            cs = cs.subSequence(i * 1000, cs.length());
            //System.out.println(cs.length());
        }
        long endCSTime = System.currentTimeMillis();

        System.out.println("Str: " + (endStrTime - startStrTime));
        System.out.println("CStr: " + (endCSTime - startCSTime));

    }

    public static void main(String[] args) {

        //customStringTests();
        //speedLargeTests();
        //speedSmallTests();
        anotherTest();

    }
}
