
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {
    MyArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
    }

    @AfterEach
    void tearDown() {
        list = null;
    }

    @Test
    void size() {
        assertEquals(0, list.size());
    }

    @Test
    void sizeAfterAddElement() {
        list.add(1);
        assertEquals(1, list.size());
    }

    @Test
    void get() {
        list.add(100);
        assertEquals(100, list.get(0));
    }

    @Test
    void checkPositionAddedElements() {
        list.add(1);
        list.add(10, 0);
        assertEquals(10, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    void remove() {
        list.add(1);
        assertEquals(1, list.size());
        list.remove(0);
        assertEquals(0, list.size());
    }

    @Test
    void clear() {
        list.add(1);
        assertEquals(1, list.size());
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void quickSortComparable() {
        for (int i = 10; i >= 0; i--) {
            list.add(i);
        }

        MyArrayList<Integer> sortedList = MyArrayList.quickSort(list);

        for (int i = 0; i <= 10; i++) {
            assertEquals(i, sortedList.get(i));
        }
    }

    @Test
    void quickSortComparator() {
        list.add(3);
        list.add(1);
        list.add(2);

        MyArrayList<Integer> sortedListDesc = MyArrayList.quickSort(list, (i1, i2) -> i2 - i1);

        for (int i = 3; i > 0; i--) {
            assertEquals(i, sortedListDesc.get(3 - i));
        }
    }
}