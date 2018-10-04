package edu.technopolis.advjava.demo;

public class PuzzlingStrings {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            //Конкатенация инт и char может привести к сложению

            //wrong
            //System.out.println(i + ',' + i*i);

            //а с префиксом всё сработает
            System.out.println("" + i + ',' + i*i);
        }
    }
}
