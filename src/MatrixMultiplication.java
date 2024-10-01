import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class MatrixMultiplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Determine if the user input is through command line or prompt
        if (args.length == 2) {
            // Case 1: Two file names provided via command line
            handleFiles(args[0], args[1]);
        } else if (args.length == 1 && isInteger(args[0])) {
            // Case 2: Integer provided via command line
            int size = Integer.parseInt(args[0]);
            handleRandomMatrices(size);
        } else {
            // Get user input with scanner
            System.out.println("Enter two file names or an integer:");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                // Case 2: Integer input
                int size = Integer.parseInt(input);
                handleRandomMatrices(size);
            } else {
                // Case 1: File names input
                String[] files = input.split("\\s+");
                if (files.length == 2) {
                    handleFiles(files[0], files[1]);
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }

        scanner.close();
    }

    // Handle random matrix generation and multiplication
    public static void handleRandomMatrices(int size) {
        int[][] matrix1 = generateRandomMatrix(size);
        int[][] matrix2 = generateRandomMatrix(size);

        writeMatrixToFile("matrix1.txt", matrix1);
        writeMatrixToFile("matrix2.txt", matrix2);

        int[][] result = multiplyMatrices(matrix1, matrix2);
        writeMatrixToFile("matrix3.txt", result);

        System.out.println("Random matrices generated and saved to matrix1.txt, matrix2.txt, and matrix3.txt.");
    }

    // Handle reading matrices from files and performing multiplication
    public static void handleFiles(String file1, String file2) {
        int[][] matrix1 = readMatrixFromFile(file1);
        int[][] matrix2 = readMatrixFromFile(file2);

        if (matrix1[0].length != matrix2.length) {
            System.out.println("Matrix multiplication cannot be performed due to dimension mismatch.");
            return;
        }

        int[][] result = multiplyMatrices(matrix1, matrix2);
        writeMatrixToFile("matrix3.txt", result);

        System.out.println("Matrices multiplied and result saved to matrix3.txt.");
    }

    // Read a matrix from a file
    public static int[][] readMatrixFromFile(String filename) {
        try (Scanner sc = new Scanner(new File(filename))) {
            int rows = sc.nextInt();
            int cols = sc.nextInt();
            int[][] matrix = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }
            return matrix;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return new int[0][0];
        }
    }

    // Write a matrix to a file
    public static void writeMatrixToFile(String filename, int[][] matrix) {
        try (PrintWriter pw = new PrintWriter(filename)) {
            int rows = matrix.length;
            int cols = matrix[0].length;

            pw.println(rows + " " + cols);
            for (int[] row : matrix) {
                for (int val : row) {
                    pw.print(val + " ");
                }
                pw.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    // Multiply two matrices
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    // Generate a random square matrix
    public static int[][] generateRandomMatrix(int size) {
        Random rand = new Random();
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(10); // Generate random numbers between 0 and 9
            }
        }

        return matrix;
    }

    // Check if a string is an integer
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}