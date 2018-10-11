package edu.technopolis.advjava;

import java.util.Scanner;

public class TestCustomString {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.print("Введите количество строк в тексте: ");
        try {
            int numOfString = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < numOfString; i++) {
                stringBuilder.append(scanner.nextLine());
                stringBuilder.append('\n');
            }
        }catch (Exception e){
            throw new NumberFormatException();
        }

        CustomString customString = new CustomString(stringBuilder.toString());
        System.out.println(customString.charAt(6));

        System.out.println(customString);
    }

}
