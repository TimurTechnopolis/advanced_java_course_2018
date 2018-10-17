package edu.technopolis;

import java.util.Arrays;

public class CustomString implements CharSequence {
    private final char[][] arrayOfChars;
    private int arrayLength = 0;
    private int numOfRows;

    public CustomString(String inputData) {
        int rows = 0;

        for (String word : inputData.split(" ")) {
            rows++;
            if (word.length() > 0) arrayLength += word.length();

        }

        numOfRows = rows;

        arrayOfChars = new char[rows][];
        
        int j = 0;

        for (String word : inputData.split(" ")) {
            arrayOfChars[j] = word.toCharArray();
            j++;
        }
    }

    private CustomString(char[][] inputArray) {
        this.arrayOfChars = inputArray;
    }

    public char[][] GetArrayOfChars() {
        return arrayOfChars;
    }

    @Override
    public int length() {
        return arrayLength;
    }

    @Override
    public char charAt(int index) {
        int j;
        int row = 0;
        int column = 0;
        for (int i = 0; i < numOfRows; i++) {
            j = arrayOfChars[i].length;
            if (index < j) {
                row = i;
                column = index;
                break;
            } else {
                index -= j;
            }
        }
        return arrayOfChars[row][column];
    }

    @Override
    public CustomString subSequence(int start, int end) {
        char[][] copiedCharArray;
        int startRow = 0;
        int endRow = 0;
        int rowLength;
        for (int i = 0; i < numOfRows; i++) {
            rowLength = arrayOfChars[i].length;
            if (start > 0 && start > rowLength) {
                startRow++;
                start -= rowLength;
            }

            if (end > 0 && end > rowLength) {
                endRow ++;
                end -= rowLength;
            }

        }

        copiedCharArray = new char[endRow - startRow + 1][];

        for (int i = 0; i < endRow - startRow + 1; i++) {
            if (i == 0) {
                copiedCharArray[i] = Arrays.copyOfRange(arrayOfChars[startRow + i], start, arrayOfChars[i].length);
            } else if (i == endRow - startRow - 1) {
                copiedCharArray[i] = Arrays.copyOfRange(arrayOfChars[startRow + i], 0, end);
            } else {
                copiedCharArray[i] = Arrays.copyOfRange(arrayOfChars[startRow + i], 0, arrayOfChars[i].length);
            }
        }

        return new CustomString(copiedCharArray);
    }

    public static void main(String[] args) {
        String test = "hi, I'm Andrew!";
        CustomString charArr = new CustomString(test);
        System.out.println(Arrays.deepToString(charArr.GetArrayOfChars()));
        System.out.println(charArr.length());
        System.out.println(charArr.charAt(12));
        CustomString charArr_test  = charArr.subSequence(4, 8);
        System.out.println(Arrays.deepToString(charArr_test.GetArrayOfChars()));
        System.out.println(Arrays.deepToString(charArr.GetArrayOfChars()));
    }
}
