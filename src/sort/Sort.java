package sort;

import data.structures.ArrayQueue;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by g2525_000 on 2015/7/26.
 */
public class Sort {
    public static void SelectionSort(Comparable[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[min]) < 0)
                    min = j;
            }
            swap(array, min, i);
        }
    }

    public static void InsertionSort(Comparable[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            Comparable movingElement = array[i];
            int j = i + 1;
            for (; j < array.length; j++) {
                if(movingElement.compareTo(array[j]) > 0)
                    array[j - 1] = array[j];
                else
                    break;
            }
            array[j - 1] = movingElement;
        }
    }

    public static void MergeSort(Comparable[] array) {
        Comparable[] aux = new Comparable[array.length];
        for (int i = 0; i < array.length; i++) {
            aux[i] = array[i];
        }
        Sort(array, aux, 0, array.length - 1);
    }

    private static void Sort(Comparable[] array, Comparable[] aux, int hi, int lo) {
        if (lo >= hi) return;
        int mid = (hi - lo) / 2;
        Sort(aux, array, lo, mid);
        Sort(aux, array, mid + 1, hi);
        if (array[mid].compareTo(array[mid + 1]) < 0) return;
        merge(array, aux, lo, hi, mid);
    }

    private static void merge(Comparable[] array, Comparable[] auxArray, int hi, int lo, int mid) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) auxArray[k++] = array[j++];
            else if (j > hi) auxArray[k++] = array[i++];
            else if (array[i].compareTo(array[j]) < 0) auxArray[k++] = array[i++];
            else auxArray[k++] = array[j++];
        }

    }

    public static void QuickSort(Comparable[] array) {
        if (array.length < 10)
            InsertionSort(array);
        else {
            shuffle(array);
            int index = tukeyNinther(array);
            swap(array, 0, index);
            qSort(array, 0, array.length);
        }

    }

    private static void qSort(Comparable[] array, int lo, int hi) {
        if (lo >= hi) return;

        int i = lo;
        int lt = lo, gt = hi;
        Comparable pivot = array[lo];
        while (i <= gt) {
            int cmp = array[i].compareTo(pivot);
            if (cmp < 0) swap(array, lt++, i++);
            else if (cmp > 0) swap(array, i, gt--);
            else i++;
        }

        qSort(array, lo, lt - 1);
        qSort(array, gt + 1, hi);
    }

    private static int tukeyNinther(Comparable[] array) {
        Comparable[][] median = new Comparable[3][3];

        int gap = (array.length) / 9;
        int position = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                median[i][j] = array[position];
                position += gap;
            }
        }
        int index = median(median);

        return index % 3 * gap + index / 3;

    }


    private static int median(Comparable[][] median) {
        int index = 0;
        int[] indexArray = new int[3];
        for (int i = 0; i < 3; i++) {
            if (median[i][0].compareTo(median[i][1]) < 0) {
                if (median[i][1].compareTo(median[i][2]) < 0) indexArray[i] = 1;
                else if (median[i][0].compareTo(median[i][2]) < 0) indexArray[i] = 2;
                else indexArray[i] = 0;
            } else {
                if (median[i][1].compareTo(median[i][2]) > 0) indexArray[i] = 1;
                else if (median[i][0].compareTo(median[i][2]) < 0) indexArray[i] = 0;
                else indexArray[i] = 2;
            }
        }

        if (median[0][indexArray[0]].compareTo(median[1][indexArray[1]]) < 0) {
            if (median[1][indexArray[1]].compareTo(median[2][indexArray[2]]) < 0) index = 1;
            else if (median[0][indexArray[0]].compareTo(median[2][indexArray[2]]) < 0) index = 2;
            else index = 0;
        } else {
            if (median[1][indexArray[1]].compareTo(median[2][indexArray[2]]) > 0) index = 1;
            else if (median[0][indexArray[0]].compareTo(median[2][indexArray[2]]) < 0) index = 0;
            else index = 2;
        }

        return index * 3 + indexArray[index];
    }

    public static void shuffle(Comparable[] array) {
        Random random = new Random();
        int count = array.length;
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    private static void swap(Comparable[] array, int i, int j) {
        if (i == j) return;
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[15];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * 10 + 1);
        }
        System.out.println(Arrays.toString(array));

        SelectionSort(array);
        System.out.println(Arrays.toString(array));

        shuffle(array);
        InsertionSort(array);
        System.out.println(Arrays.toString(array));

        shuffle(array);
        MergeSort(array);
        System.out.println(Arrays.toString(array));

        shuffle(array);
        //QuickSort(array);
        System.out.println(Arrays.toString(array));


    }

}
