package ru.unn.agile.ClassMatrix.Model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class MatrixTest {

    @Test
    public void whenMatrixNull() {
        try {
            Matrix testMatrix = new Matrix(null);
            Assert.fail();
        } catch (IllegalStateException ex) {
        }
    }

    @Test
    public void whenMatrix2x2IsCreatedSuccessful() {
        int[][] array = {{1, 2},
                {3, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertTrue(testMatrix.getNumberColumn() == 2);
        assertTrue(testMatrix.getNumberRow() == 2);
    }

    @Test
    public void whenMatrixWithWrongRowsAndColumns() {
        int[][] array = {{1, 2},
                {3, 5, 3},
                {5}
        };

        try {
            Matrix testMatrix = new Matrix(array);
            Assert.fail();
        } catch (IllegalStateException ex) {
        }
    }

    @Test
    public void whenMatrix2x2WhenSomeIntNumbers() {
        int[][] array = {{1, 2},
                {3, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertArrayEquals(testMatrix.toArray(), array);
    }

    @Test
    public void whenMatrix2x2IsQuadriq() {
        int[][] array = {{1, 2},
                {3, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertTrue(testMatrix.isQuadric());
    }


    @Test
    public void whenMatrix2x3NotQuadriq() {
        int[][] array = {{1, 2, 3},
                {3, 5, 9}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertTrue(!testMatrix.isQuadric());
    }


    @Test
    public void whenMatrix3x2NotQuadriq() {
        int[][] array = {{1, 2},
                {3, 5},
                {1, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertFalse(testMatrix.isQuadric());
    }

    @Test
    public void whenInitializationMatrix3x3SomeIntNumbersIsCorrect() {
        int[][] array = {{1, 2, 3},
                {3, 5, 4},
                {1, 5, 4}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertArrayEquals(array, testMatrix.toArray());
    }

    @Test
    public void whenInitializationMatrix3x2SomeIntNumbersIsCorrect() {
        int[][] array1 = {{1, 2},
                {3, 5},
                {5, 5}
        };

        int[][] array2 = {{1, 2},
                {3, 5},
                {5, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array1);
        assertArrayEquals(testMatrix.toArray(), array2);
    }

    @Test
    public void whenMatrixWith1ElementCalculateDeterminant() {
        int[][] array = {{777}};

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertEquals(777, testMatrix.calculateDeterminant());
    }

    @Test
    public void whenCalculateDeterminantIntMatrix2x2WithResult18() {
        int[][] array = {{6, 4},
                {3, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertEquals(18, testMatrix.calculateDeterminant());
    }

    @Test
    public void whenTrueCalculateDeterminantIntMatrix3x2() {
        int[][] array = {{6, 4},
                {3, 5},
                {6, 5}
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertTrue(testMatrix.calculateDeterminant() == 0);
    }

    @Test
    public void whenCalculateDeterminantIntMatrix3x3WithResult1() {
        int[][] array = {{1, 0, 1},
                {0, 1, 0},
                {0, 0, 1},
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertEquals(1, testMatrix.calculateDeterminant());
    }

    @Test
    public void whenCalculateDeterminantIntDegenerateMatrix3x3WithResult1() {
        int[][] array = {{1, 2, 4},
                {1, 1, 1},
                {1, 1, 1},
        };

        Matrix testMatrix = getTestMatrixFromValidArray(array);
        assertEquals(testMatrix.calculateDeterminant(), 0);
    }

    private Matrix getTestMatrixFromValidArray(final int[][] array) {
        Matrix testMatrix = null;
        try {
            testMatrix = new Matrix(array);
        } catch (IllegalStateException ex) {
            Assert.fail();
        }
        return testMatrix;
    }
}
