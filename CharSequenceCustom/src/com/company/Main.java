package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String st = "How i think.";
        CharSequenceCustom str = new CharSequenceCustom(st);
        str.print();
        System.out.println("" + str.length() + " " + str.lengthLine(0) + " " + str.lines());
        str.addLIne("LOL");
        str.print();
        System.out.println("" + str.length() + " " + str.lengthLine(0) + " " + str.lines());
        CharSequenceCustom str1 = str.subSequence(2, 13);
        System.out.println("" + str1.length() + " " + str1.lengthLine(0) + " " + str1.lines());
        str1.print();
    }
}
