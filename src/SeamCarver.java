import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

/**
 * Created by MingJe on 2015/11/27.
 */
public class SeamCarver {
    private final Picture picture;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new java.lang.NullPointerException();
        this.picture = picture;
        energy = new double[picture.width()][picture.height()];

        Arrays.fill(energy[0], 1000);
        Arrays.fill(energy[energy.length - 1], 1000);
        for (int i = 1; i < energy.length - 1; i++) {
            Arrays.fill(energy[i], -1);
            energy[i][0] = 1000;
            energy[i][energy.length - 1] = 1000;

        }
    }

    // current picture
    public Picture picture() {
        Picture copyPicture = new Picture(picture);
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new java.lang.IndexOutOfBoundsException("Wrong parameters in energy fun.");

        if (energy[x][y] != -1) return energy[x][y];

        Color left = picture.get(x - 1, y),
                right = picture.get(x + 1, y),
                up = picture.get(x, y + 1),
                down = picture.get(x, y - 1);
        int deltaXSquare = Math.abs(left.getBlue() - right.getBlue()) ^ 2
                + Math.abs(left.getGreen() - right.getGreen()) ^ 2
                + Math.abs(left.getRed() - right.getRed()) ^ 2;
        int deltaYSquare = Math.abs(up.getBlue() - down.getBlue()) ^ 2
                + Math.abs(up.getGreen() - down.getGreen()) ^ 2
                + Math.abs(up.getRed() - down.getRed()) ^ 2;
        energy[x][y] = Math.sqrt(deltaXSquare + deltaYSquare);
        return energy[x][y];

    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        return new int[0];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new java.lang.NullPointerException();
        if (seam.length != width()) throw new java.lang.IllegalArgumentException();
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new java.lang.NullPointerException();
        if (seam.length != height()) throw new java.lang.IllegalArgumentException();
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new java.lang.IllegalArgumentException();

        }


    }
}
