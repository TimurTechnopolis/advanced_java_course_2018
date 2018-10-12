package edu.technopolis.advjava;

import java.util.Scanner;

public class TestCustomString {

    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();*/

        String testString= "Реализованная специальным образом строка (аналог {@link java.lang.String}),\n" +
                " * хранящий содержимое строки кусочкам (chunks) для лучшего переиспользования памяти при активном\n" +
                " * создании подстрок.";

        CustomString customString = new CustomString(testString);
/*        System.out.println(customString.charAt(6));

        System.out.println(customString.subSequence(5,26));*/
        customString.printChunks();

        System.out.println(customString);

        System.out.println("testString length: " + testString.length());
        System.out.println("customString length: " + customString.length());

        System.out.println(customString.charAt(0));
        System.out.println(customString.charAt(18));
        System.out.println(customString.charAt(194));
    }

}
