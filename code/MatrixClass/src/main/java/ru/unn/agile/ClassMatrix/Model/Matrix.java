package ru.unn.agile.ClassMatrix.Model;

public class Matrix {

    public static class FailedArrayException extends Exception {
    }

    public Matrix(final int[][] array) throws IllegalStateException {
        if (array == null) {
            throw new IllegalStateException("Input array is empty!");
        }
        row = array.length;
        column = array[0].length;
        for (int i = 1; i < row; i++) {
            if (array[i].length != column) {
                throw new IllegalStateException("Column have different number or null!");
            }
        }

        matrixData = array.clone();
    }

    public int[][] toArray() {
        return matrixData;
    }

    public boolean isQuadric() {
        return row == column;
    }

    public int calculateDeterminant() {
        int result = 0;
        if (isQuadric()) {
            result = computeDeterminant(matrixData, row);
        }
        return result;
    }

    public int getNumberRow() {
        return row;
    }

    public int getNumberColumn() {
        return column;
    }

    private static void getMatrixWithoutRowAndCol(final int[][] matrix,
                                                  final int size,
                                                  final int excludedRow,
                                                  final int excludedColumn,
                                                  final int[][] array) {

        if (matrix == null) {
            throw new IllegalStateException("Matrix is empty!");
        }

        int row = 0;
        int col = 0;
        for (int i = 0; i < size - 1; i++) {
            if (i == excludedRow) {
                row = 1;
            }
            col = 0;
            for (int j = 0; j < size - 1; j++) {
                if (j == excludedColumn) {
                    col = 1;
                }
                array[i][j] = matrix[i + row][j + col];
            }
        }
    }

    private static int computeDeterminant(final int[][] array, final int size) {
        int result;
        int signMinor;
        result = 0;
        signMinor = 1;

        if (size == 1) {
            result = array[0][0];
            return result;
        }

        if (size == 2) {
            int mainDiagonal = array[0][0] * array[1][1];
            int sideDiagonal = array[0][1] * array[1][0];
            result = mainDiagonal - sideDiagonal;
            return result;
        }

        if (size > 2) {
            int[][] p = new int[size - 1][size - 1];
            for (int j = 0; j < size; j++) {
                getMatrixWithoutRowAndCol(array, size, 0, j, p);
                int ratio = array[0][j];
                int resultEndPart = signMinor * ratio * computeDeterminant(p, size - 1);
                result = result + resultEndPart;
                signMinor = -signMinor;
            }
        }
        return result;
    }

    private int[][] matrixData;
    private int row;
    private int column;
}
