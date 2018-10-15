package edu.technopolis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File file = new File("src/edu/technopolis/data.txt");
        Scanner input = null;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CustomString string = new CustomString(input.nextLine());
        string.print();

        input.close();
    }
}
