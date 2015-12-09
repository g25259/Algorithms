import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;


/**
 * Created by MingJe on 2015/11/27.
 */
public class SeamCarver {
    private double[][] energy;
    private boolean isTranspose;
    private boolean isModified;
    private int width, height;

    private int[][] colors;
    private final int R = 255 << 16;
    private final int G = 255 << 8;
    private final int B = 255;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new java.lang.NullPointerException();
        energy = new double[picture.height()][picture.width()];
        colors = new int[picture.height()][picture.width()];
        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[0].length; j++) {

                Color tmp = picture.get(j, i);
                colors[i][j] |= tmp.getRed();
                colors[i][j] = colors[i][j] << 8;
                colors[i][j] |= tmp.getGreen();
                colors[i][j] = colors[i][j] << 8;
                colors[i][j] |= tmp.getBlue();

            }
        }
        width = picture.width();
        height = picture.height();
        buildEnergy();

    }

    // current picture
    public Picture picture() {
        Picture newPic;
        //if (isModified) {
            if (isTranspose) {
                transpose();
                isTranspose = false;
            }

            newPic = new Picture(colors[0].length, colors.length);
            for (int i = 0; i < colors.length; i++) {
                for (int j = 0; j < colors[0].length; j++) {
                    int colorRGB = colors[i][j];
                    Color tmp = new Color((R & colorRGB) >> 16, (G & colorRGB) >> 8, B & colorRGB);
                    newPic.set(j, i, tmp);
                }
            }
            isModified = false;

        //}
        //newPic = new Picture(picture);
        return new Picture(newPic);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new java.lang.IndexOutOfBoundsException("Wrong parameters in energy fun.");
        double reEnergy;
        if (!isTranspose) reEnergy = energy[y][x];
        else reEnergy = energy[x][y];

        return reEnergy;

    }

    private void buildEnergy() {
        Arrays.fill(energy[0], 1000);
        Arrays.fill(energy[energy.length - 1], 1000);
        for (int i = 1; i < energy.length - 1; i++) {
            energy[i][0] = 1000;
            energy[i][energy[i].length - 1] = 1000;

        }
        for (int i = 1; i < energy.length - 1; i++) {
            for (int j = 1; j < energy[0].length - 1; j++) {
                computeEnergy(energy, colors, i, j);
            }
        }
    }

    private void computeEnergy(double[][] energy, int[][] colors, int i, int j) {

        if (j < 0 || j >= energy[0].length) return;
        if (j == 0 || j == energy[0].length - 1) {
            energy[i][j] = 1000;
            return;
        }
        int r = ((colors[i][j - 1] & R) - (colors[i][j + 1] & R)) >> 16;
        int g = ((colors[i][j - 1] & G) - (colors[i][j + 1] & G)) >> 8;
        int b = (colors[i][j - 1] & B) - (colors[i][j + 1] & B);
        double deltaXSquare = b * b + g * g + r * r;

        r = ((colors[i - 1][j] & R) - (colors[i + 1][j] & R)) >> 16;
        g = ((colors[i - 1][j] & G) - (colors[i + 1][j] & G)) >> 8;
        b = (colors[i - 1][j] & B) - (colors[i + 1][j] & B);

        double deltaYSquare = b * b + g * g + r * r;

        energy[i][j] = Math.sqrt(deltaXSquare + deltaYSquare);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!isTranspose) {
            transpose();
            isTranspose = true;
        }
        return findSeam();
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (isTranspose) {
            transpose();
            isTranspose = false;
        }
        return findSeam();
    }

    private int[] findSeam() {
        int[] path = new int[energy.length];
        double[][] distTo = new double[energy.length][energy[0].length];
        int[][] edgeTo = new int[energy.length][energy[0].length];
        Arrays.fill(distTo[0], 1000);

        for (int i = 1; i < distTo.length; i++) {
            Arrays.fill(distTo[i], Double.POSITIVE_INFINITY);
            Arrays.fill(edgeTo[i], -1);
        }

        double minDist = Double.POSITIVE_INFINITY;
        int minIdx = -1;
        for (int column = 0; column < energy[0].length; column++) {
            int row = 0;
            int tmpColumn = column;
            while (true) {
                visit(row, tmpColumn, distTo, edgeTo);
                if (row + 1 <= energy.length - 1 && tmpColumn - 1 >= 0) {
                    row += 1;
                    tmpColumn -= 1;
                } else {
                    if (row == energy.length - 1) {
                        if (minDist > distTo[row][tmpColumn]) {
                            minDist = distTo[row][tmpColumn];
                            minIdx = tmpColumn;
                        }
                    }
                    break;
                }
            }
        }
        for (int row = 1; row < energy.length; row++) {
            int column = energy[0].length - 1;
            int tmpRow = row;
            while (true) {
                visit(tmpRow, column, distTo, edgeTo);
                if (tmpRow + 1 <= energy.length - 1 && column - 1 >= 0) {
                    tmpRow += 1;
                    column -= 1;
                } else {
                    if (tmpRow == energy.length - 1) {
                        if (minDist > distTo[tmpRow][column]) {
                            minDist = distTo[tmpRow][column];
                            minIdx = column;
                        }
                    }
                    break;
                }
            }
        }
        for (int i = path.length - 1; i > -1; i--) {
            path[i] = minIdx;
            minIdx = edgeTo[i][minIdx];
        }
        return path;
    }

    private void visit(int row, int column, double[][] distTo, int[][] edgeTo) {

        if (row + 1 <= energy.length - 1 && column - 1 >= 0) {
            if (distTo[row + 1][column - 1] > distTo[row][column] + energy[row + 1][column - 1]) {
                distTo[row + 1][column - 1] = distTo[row][column] + energy[row + 1][column - 1];
                edgeTo[row + 1][column - 1] = column;
            }
        }
        if (row + 1 <= energy.length - 1) {
            if (distTo[row + 1][column] > distTo[row][column] + energy[row + 1][column]) {
                distTo[row + 1][column] = distTo[row][column] + energy[row + 1][column];
                edgeTo[row + 1][column] = column;
            }
        }
        if (row + 1 <= energy.length - 1 && column + 1 <= energy[0].length - 1) {
            if (distTo[row + 1][column + 1] > distTo[row][column] + energy[row + 1][column + 1]) {
                distTo[row + 1][column + 1] = distTo[row][column] + energy[row + 1][column + 1];
                edgeTo[row + 1][column + 1] = column;
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (!isTranspose) {
            transpose();
            isTranspose = true;
        }
        removeSeam(seam);


    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (isTranspose) {
            transpose();
            isTranspose = false;
        }
        removeSeam(seam);
    }

    private void removeSeam(int[] seam) {
        if (seam == null) throw new java.lang.NullPointerException();
        if (seam.length != colors.length || colors[0].length <= 1) throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < seam.length; i++) {
            if (i != 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new java.lang.IllegalArgumentException();
            else if (isTranspose && (seam[i] < 0 || seam[i] > height - 1))
                throw new java.lang.IllegalArgumentException();
            else if (!isTranspose && (seam[i] < 0 || seam[i] > width - 1))
                throw new java.lang.IllegalArgumentException();

        }

        if (isTranspose && height <= 1)
            throw new java.lang.IllegalArgumentException();
        else if (!isTranspose && width <= 1)
            throw new java.lang.IllegalArgumentException();


        int[][] newColor = new int[colors.length][colors[0].length - 1];
        double[][] newEnergy = new double[energy.length][energy[0].length - 1];
        for (int i = 0; i < colors.length; i++) {
            System.arraycopy(colors[i], 0, newColor[i], 0, seam[i]);
            System.arraycopy(colors[i], seam[i] + 1, newColor[i], seam[i], colors[i].length - seam[i] - 1);
            System.arraycopy(energy[i], 0, newEnergy[i], 0, seam[i]);
            System.arraycopy(energy[i], seam[i] + 1, newEnergy[i], seam[i], energy[i].length - seam[i] - 1);

        }
        for (int i = 1; i < colors.length - 1; i++) {
            computeEnergy(newEnergy, newColor, i, seam[i]);
            computeEnergy(newEnergy, newColor, i, seam[i] - 1);
        }
        if (isTranspose)
            height--;
        else width--;
        isModified = true;
        colors = newColor;
        energy = newEnergy;
    }

    private void transpose() {
        int[][] transColor = new int[colors[0].length][colors.length];
        double[][] tranEnergy = new double[energy[0].length][energy.length];
        for (int i = 0; i < transColor.length; i++) {
            for (int j = 0; j < transColor[0].length; j++) {
                tranEnergy[i][j] = energy[j][i];
                transColor[i][j] = colors[j][i];

            }
        }
        colors = transColor;
        energy = tranEnergy;
    }

}
