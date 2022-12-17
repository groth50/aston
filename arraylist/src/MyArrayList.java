import java.util.Comparator;


/**
 * Resizable-array implementation for Aston. Permits all elements, including null.
 */
public class MyArrayList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int DEFAULT_COEFFICIENT = 2;

    private int CURRENT_CAPACITY;
    private int size = 0;

    Object[] array;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
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

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the element at the specified position in this list.

     * @param index  – index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index >= size())
     */
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param elem  – element to be appended to this list
     */
    public void add(E elem) {
        add(elem, size);
    }

    /**
     * Inserts the specified element at the specified position in this list. Shifts the element currently
     * at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param elem - element to be inserted
     * @param index – index at which the specified element is to be inserted element – element to be inserted
     * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index > size())
     */
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

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index – the index of the element to be removed
     * @return - the element that was removed from the list
     * @throws - IndexOutOfBoundsException – if the index is out of range (index < 0 || index >= size()
     */
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

    /**
     * Removes all of the elements from this list. The list will be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    /**
     * Sorts the specified list into ascending order, according to the natural ordering of its elements.
     * All elements in the list must implement the Comparable interface.
     * Furthermore, all elements in the list must be mutually comparable
     * (that is, e1.compareTo(e2) must not throw a ClassCastException for any elements e1 and e2 in the list).
     * Using the Quick sort algorithm.
     * Immutable realization.
     *
     * @param list – the list to be sorted.
     * @param <T> - must be comparable
     * @return - new sorted list
     * @throws - ClassCastException – if the list contains elements that are not mutually comparable (for example, strings and integers).
     */
    public static <T extends Comparable<? super T>> MyArrayList<T> quickSort(MyArrayList<T> list) {
        T[] array = toComparableArray(list);
        quickSort(array, 0, array.length - 1);
        return new MyArrayList<>(array, array.length);
    }

    /**
     * Return sorted list according to the order induced by the specified Comparator.
     * Using the Quick sort algorithm.
     * Immutable realization.
     *
     * @param list – the list to be sorted.
     * @param comparator - used to compare list elements.
     * @return - new sorted list
     * @throws - ClassCastException – if the list contains elements that are not mutually comparable (for example, strings and integers).
     */
    public static <T> MyArrayList<T> quickSort(MyArrayList<T> list, Comparator<? super T> comparator) {
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
