package edu.technopolis.advjava.demo;

public class AppendPerformance {

    public static void main(String[] args) {
        String hugeHellishString = "Hello";
        for (int i = 0; i < 100; i++) {
            //+ в цикле приводит к многочисленным аллокациям.
            //правильно создать StringBuilder и вызывать append
            hugeHellishString += "#" + i + "\r\n";
        }
        System.out.println("Huge hellish string\r\n" + hugeHellishString);
    }

}
