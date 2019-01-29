package edu.technopolis.advjava.demo;

public class Equals {
    private static final String HELLO = "Hello";
    private static final String WORLD = "World";
    private static final String HELLO_WORLD = "Hello, World";
    private static final String HELLO_WORLD_DERIVED = HELLO + ", " + WORLD;

    public static void main(String[] args) {
        String hello = "Hello";
        String world = "World";
        String helloWorld = "Hello, World";
        //Сравнение двух одинаковых литералов
        System.out.println(helloWorld == HELLO_WORLD);
        //сравнение литерала и интернированной строки
        System.out.println(helloWorld == (hello + ", " + world).intern());
        //сравнение литерала и... другого литерала (спасибо компилятору)
        System.out.println(HELLO_WORLD == HELLO_WORLD_DERIVED);

    }
}
