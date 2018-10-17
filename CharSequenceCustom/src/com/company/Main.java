package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String st = "How i think.";
        CustomString ar = new CustomString(st);
        char a = ar.charAt(4);
        System.out.println(ar.length() + " " + a + " " + ar.charAt(9));

        String st1 = ar.toString();
        System.out.println(st1);

        char l[][] = {{'g', 'a', 'z', 't'}, {'y', 'q', 'b', 'y'}, {'z', 'g', 'y', 't'}};
        CustomString nm = new CustomString(l, 2, 1, 1, 2);
        System.out.println(nm.length() + " " + nm.charAt(2) + " " + nm.charAt(3));

        CustomString test2 = ar.subSequence(2, 8);
        System.out.println(test2.length() + " " + test2.charAt(2) + " " + test2.charAt(4));
    }
}
