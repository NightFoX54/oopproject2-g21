import java.util.Comparator;

/**
 * A comparator for comparing two integers in ascending order.
 */
public class IntegerAscendingComparator implements Comparator<Integer> {

    /**
     * Compares two {@link Integer} objects to determine their order.
     * 
     * @param i1 the first Integer to compare
     * @param i2 the second Integer to compare
     * @return a negative integer if i1 is less than i2, 
     *         zero if i1 is equal to i2, 
     *         or a positive integer if i1 is greater than i2
     */
    @Override
    public int compare(Integer i1, Integer i2) {
        return i1 - i2;
    }
}
