package edu.technopolis.advjava;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void warAndPieceTests() {
        CustomString s = new CustomString();

        try (FileReader reader = new FileReader("war_and_peace/lt1.txt")) {

            Scanner sc = new Scanner(reader);

            StringBuilder builder = new StringBuilder();
            while (sc.hasNext()) {
                builder.append(sc.nextLine() + "\n");
            }

            s = new CustomString(builder.toString());

        } catch (IOException e) {

        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            System.out.println(s.subSequence(i * 50000, (i + 1) * 50000));
        }
        System.out.println(s.subSequence(50000, 100000));
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Subsequences: " + elapsedTime);

    }

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
            CharSequence s2 = str.subSequence(i * 1000, 1000000 - 1 - i * 1000);
        }
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Custom string subsequences: " + elapsedTime);
    }

    public static void main(String[] args) {

        //warAndPieceTests();
        //customStringTests();
        //speedLargeTests();
        //speedSmallTests();

    }
}
