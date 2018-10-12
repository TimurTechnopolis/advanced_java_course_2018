package edu.technopolis.advjava;

import java.util.Scanner;

public class TestCustomString {

    public static void main(String[] args) {

        String testString = "Реализованная специальным образом строка (аналог {@link java.lang.String}),\n" +
                " * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном\n" +
                " * создании подстрок.";

        char[][] testChunks = new char[][]{{'v', 'h', '\n',}, {'\t', 'r', 'y'}, {'y', 'i', 'e'}};

        CustomString customString1 = new CustomString(testString);
        CustomString customString2 = new CustomString(testString, 2);
        CustomString customString3 = new CustomString(testChunks);

        System.out.println("customString1 toString():");
        System.out.println(customString1);
        System.out.println("Chunks:");
        customString1.printChunks();

        System.out.println("testString length: " + testString.length());
        System.out.println("customString1 length: " + customString1.length());

        System.out.println(customString1.charAt(0));
        System.out.println(customString1.charAt(18));
        System.out.println(customString1.charAt(194));

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println();

        System.out.println("customString2 toString():");
        System.out.println(customString2);
        System.out.println("Chunks:");
        customString2.printChunks();

        System.out.println("testString length: " + testString.length());
        System.out.println("customString2 length: " + customString2.length());

        System.out.println(customString2.charAt(0));
        System.out.println(customString2.charAt(18));
        System.out.println(customString2.charAt(194));

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println();

        System.out.println("customString3 toString():");
        System.out.println(customString3);
        System.out.println("Chunks:");
        customString3.printChunks();

        System.out.println("customString3 length: " + customString3.length());

        System.out.println(customString3.charAt(0));
        System.out.println(customString3.charAt(5));
        System.out.println(customString3.charAt(8));

    }

}
