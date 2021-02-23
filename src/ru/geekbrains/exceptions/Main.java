package ru.geekbrains.exceptions;

public class Main {
    private static final int ARRAY_SIZE = 4;
    private static final int ARRAY_ROW_SIZE = 4;

    public static void main(String[] args) {
        int corruptSize = Math.random() >= 0.9 ? 1 : 0;
	    String [][] testArr = new String[ARRAY_SIZE + corruptSize][ARRAY_ROW_SIZE];
	    int invalidRow = (int)(Math.random() * ARRAY_SIZE);
	    int invalidColumn = (int)(Math.random() * ARRAY_ROW_SIZE);
	    boolean corruptSingleValue = Math.random() >= 0.5;
        for (int i = 0; i < testArr.length; i++) {
            for (int j = 0; j < testArr[i].length; j++) {
                if (corruptSingleValue && i == invalidRow && j == invalidColumn) {
                    testArr[i][j] = "KEK";
                } else {
                    testArr[i][j] = Integer.toString((int) (Math.random() * 20));
                }

                System.out.print(testArr[i][j] + " ");
            }
            System.out.println();
        }

        int result = 0;
        try {
            result = calculateSum(testArr);
        } catch (MyArraySizeException e) {
            System.out.println(e.getMessage());
        } catch (MyArrayDataException e) {
            result = e.currentSum;
            System.out.println(e.getMessage());
        }

        System.out.println("Result: " + result);
    }

    public static class MyArraySizeException extends Exception {
        public MyArraySizeException(int expectedSize, int actualSize) {
            super("Incorrect array length: expected " + expectedSize + ", actual size: " + actualSize);
        }
    }

    public static class MyArrayDataException extends Exception {
        public int currentSum;
        public MyArrayDataException(int invalidRow, int invalidColumn, int currentSum) {
            super("Unable to parse int in row index: " + invalidRow + ", column index: " + invalidColumn);
            this.currentSum = currentSum;
        }
    }

    public static int calculateSum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        String [][] arr = array;
        if (arr.length != ARRAY_SIZE) {
            throw new MyArraySizeException(ARRAY_SIZE, arr.length);
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != ARRAY_ROW_SIZE) {
                throw new MyArraySizeException(ARRAY_ROW_SIZE, arr[i].length);
            }
        }

        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j, sum);
                }
            }
        }
        return sum;
    }
}
