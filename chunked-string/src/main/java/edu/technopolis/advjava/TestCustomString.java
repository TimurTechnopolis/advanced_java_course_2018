package edu.technopolis.advjava;

import java.util.Scanner;

public class TestCustomString {

    public static void main(String[] args) {

        String testString = "Реализованная специальным образом строка (аналог {@link java.lang.String}),\n" +
                " * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном\n" +
                " * создании подстрок.";

        String testStringSubstring = "* хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном\n" +
                " * созда";


        CustomString customString1 = new CustomString(testString);
        CustomString customString2 = new CustomString(testString, 10);

        char[][] testChunks = new char[][]{{'v', 'h', '\n',}, {'\t', 'r', 'y'}, {'y', 'i', 'e'}};
        CustomString customString3 = new CustomString(testChunks);
        CustomString customString4 = new CustomString("vh\n\tryyie",3);

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

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println();

        System.out.println("customString4 toString():");
        System.out.println(customString4);
        System.out.println("Chunks:");
        customString4.printChunks();

        System.out.println("customString4 length: " + customString4.length());

        System.out.println(customString4.charAt(0));
        System.out.println(customString4.charAt(5));
        System.out.println(customString4.charAt(8));

        System.out.println();
        System.out.println("customString1 = customString2 ? " + customString1.equals(customString2));
        System.out.println("customString1 hashCode: " + customString1.hashCode());
        System.out.println("customString2 hashCode: " + customString2.hashCode());

        System.out.println();
        System.out.println("customString3 = customString4 ? " + customString3.equals(customString4));
        System.out.println("customString3 hashCode: " + customString3.hashCode());
        System.out.println("customString4 hashCode: " + customString4.hashCode());

        System.out.println();
        System.out.println("==========================================================================");
        System.out.println();

        CharSequence customString5 = new CustomString(testString, 15).subSequence(77,182);
        CustomString customString6 = new CustomString(testStringSubstring,15);
        CharSequence customString7 = new CustomString(testString, 15).subSequence(77,182);
        System.out.println("customString5 toString():");
        System.out.println(customString5);
        System.out.println("Chunks:");
        CustomString cS = (CustomString)customString5;
        cS.printChunks();
        System.out.println("Offset: " + cS.getOffset() + "\tFlang: " + cS.getFlang());
        System.out.println("customString5 length: " + customString5.length());

        System.out.println("customString6 toString():");
        System.out.println(customString6);
        System.out.println("Chunks:");
        customString6.printChunks();
        System.out.println("Offset: " + customString6.getOffset() + "\tFlang: " + customString6.getFlang());
        System.out.println("customString6 length: " + customString6.length());



        String str = testString.substring(77,182);
        System.out.println("testString.substring().length() = " + str.length());
        System.out.println("Эталон = " + str.charAt(0) + "\t Моё = " + customString5.charAt(0));
        System.out.println("Эталон = " + str.charAt(16) + "\t Моё = " + customString5.charAt(16));
        System.out.println("Эталон = " + str.charAt(27) + "\t Моё = " + customString5.charAt(27));
        System.out.println("Эталон = " + str.charAt(104) + "\t Моё = " + customString5.charAt(104));

        System.out.println();
        System.out.println("customString4 = customString5 ? " + customString4.equals(customString5));
        System.out.println("customString4 hashCode: " + customString4.hashCode());
        System.out.println("customString5 hashCode: " + customString5.hashCode());


        System.out.println();
        System.out.println("(CustomString)customString5 = customString6 ? " + customString5.equals(customString6));
        System.out.println("(CustomString)customString5 hashCode: " + customString5.hashCode());
        System.out.println("customString6 hashCode: " + customString6.hashCode());

        System.out.println();
        System.out.println("customString5 = customString7 ? " + customString5.equals(customString7));
        System.out.println("customString5 hashCode: " + customString5.hashCode());
        System.out.println("customString7 hashCode: " + customString7.hashCode());

    }

}
