package main.java.edu.technopolis.advjava;

public class Demo {
    public static void main(String[] args) {
        CustomString warAndPeace = new CustomString("To change this license header, choose License Headers in Project Properties. To change this template file, choose Tools | Templates and open the template in the editor.", 20);
        CustomString warAndPeaceSecondPart = warAndPeace.subSequence(16, 160);
        
        System.out.println(warAndPeace);
        System.out.println("*******************");
        System.out.println(warAndPeaceSecondPart);
        System.out.println("*******************");
        
        System.out.println("Joint chunks array: " + (warAndPeace.getChunks() == warAndPeaceSecondPart.getChunks()));
        
        System.out.println("*******************");
        System.out.println(warAndPeace);
        
        int symbNumber = 75;
        System.out.println(symbNumber + " symbol: " + warAndPeace.charAt(symbNumber));
        System.out.println(symbNumber + " symbol: " + warAndPeaceSecondPart.charAt(symbNumber));
    }
}
