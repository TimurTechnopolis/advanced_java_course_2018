package edu.technopolis.advjava.demo;

public class ObjectConcatenationMagic {

    public static void main(String[] args) {
        //как сложить два Object'а?
        //поставить "префикс" в виде пустой строки.
        String a = "" + (new Object()) + (new Object());
        System.out.println(a);
    }

}
