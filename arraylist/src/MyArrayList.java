import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_COEFFICIENT = 2;

    private int CURRENT_CAPACITY;
    private int size = 0;

    Object[] array;

    public MyArrayList() {
        CURRENT_CAPACITY = DEFAULT_CAPACITY;
        array = new Object[DEFAULT_CAPACITY];
    }

    private MyArrayList(Object[] array, int size) {
        this.array = array;
        this.size = size;
    }

    public static void main(String[] args) {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(100);
        for (int i = 0; i < 22; i++) {
            list.add(i, i);
            System.out.println(list.get(i));
        }

        list.add(999, 10);
        list.add(99, 10);
        list.add(9, 10);
        list.add(-1);
        list.add(1000);

        MyArrayList<Integer> sortedList = MyArrayList.quickSort(list);
        for (int i = 0; i < sortedList.size(); i++) {
            System.out.print(sortedList.get(i));
            System.out.print(" ");
        }

        System.out.println();

        MyArrayList<Integer> sortedListDesc = MyArrayList.quickSort(list, (i1, i2) -> i2 - i1);
        for (int i = 0; i < sortedListDesc.size(); i++) {
            System.out.print(sortedListDesc.get(i));
            System.out.print(" ");
        }
//        list.remove(10);
//        list.remove(22);
//        list.clear();


//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(10);
//        list.set(1,20);
//        System.out.println(list);
    }

    public int size() {
        return size;
    }

    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    public void add(E elem) {
        add(elem, size);
    }

    public void add(E elem, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("size is " + size);
        }

        if (CURRENT_CAPACITY <= size + 1) {
            CURRENT_CAPACITY = CURRENT_CAPACITY * DEFAULT_COEFFICIENT;
            Object[] growArray = new Object[CURRENT_CAPACITY];
            for (int i = 0; i < size; i++) {
                growArray[i] = array[i];
            }
            array = growArray;
        }

        if (index < size) {
            Object[] copy = new Object[CURRENT_CAPACITY];
            int j = 0;
            for (int i = 0; i <= size; i++) {
                if (i == index) {
                    j++;
                }
                copy[j] = array[i];
                j++;
            }
            array = copy;
        }
        array[index] = elem;
        size++;
    }

    public E remove(int index) {
        checkIndex(index);

        E result = (E) array[index];

        if (index < size - 1) {
            Object[] copy = new Object[CURRENT_CAPACITY];
            for (int i = 0; i < index; i++) {
                copy[i] = array[i];
            }

            for (int i = index + 1; i < size; i++) {
                copy[i - 1] = array[i];
            }
            array = copy;
        } else {
            array[index] = null;
        }
        size--;
        return result;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public static <T extends Comparable<? super T>> MyArrayList<T> quickSort(MyArrayList<T> list) {
        T[] array = toComparableArray(list);
        quickSort(array, 0, array.length - 1);
        return new MyArrayList<>(array, array.length);
    }

    public static <T> MyArrayList<T> quickSort(MyArrayList<T> list, Comparator<T> comparator) {
        T[] array = toArray(list);
        quickSort(array, 0, array.length - 1, comparator);
        return new MyArrayList<>(array, array.length);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("size is " + size);
        }
    }

    private static <T extends Comparable<? super T>> T[] toComparableArray(MyArrayList<T> list) {
        Comparable[] array = new Comparable[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return (T[]) array;
    }

    private static <T> T[] toArray(MyArrayList<T> list) {
        Object[] array = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return (T[]) array;
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] array, int low, int high) {
        if (array.length < 2)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        T pivot = array[middle];

        int i = low, j = high;
        while (i <= j) {
            while (array[i].compareTo(pivot) < 0) {
                i++;
            }

            while (array[j].compareTo(pivot) > 0) {
                j--;
            }

            if (i <= j) {
                T swap = array[i];
                array[i] = array[j];
                array[j] = swap;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(array, low, j);

        if (high > i)
            quickSort(array, i, high);
    }

    private static <T> void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        if (array.length < 2)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        T pivot = array[middle];

        int i = low, j = high;
        while (i <= j) {
            while (comparator.compare(array[i], pivot) < 0) {
                i++;
            }

            while (comparator.compare(array[j], pivot) > 0) {
                j--;
            }

            if (i <= j) {
                T swap = array[i];
                array[i] = array[j];
                array[j] = swap;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(array, low, j, comparator);

        if (high > i)
            quickSort(array, i, high, comparator);
    }

}
