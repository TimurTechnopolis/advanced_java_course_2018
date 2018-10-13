package edu.technopolis.CustomString;

public class Main {
    public static void main(String[] args){
        CustomString cString= new CustomString("Hello, World, this is me, and I am Java-Developer!");
        System.out.println(cString);
        System.out.println(cString.charAt(30));
        System.out.println(cString.length());
        System.out.println(cString.subSequence(35,49));
        System.out.println(cString.length());
        System.out.println(cString.charAt(4));
    }
}
