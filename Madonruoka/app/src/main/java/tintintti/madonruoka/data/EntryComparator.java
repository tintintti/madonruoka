package tintintti.madonruoka.data;

import java.util.Comparator;

/**
 * Class for comparing Entries
 */
public class EntryComparator implements Comparator {

    @Override
    public int compare(Object lhs, Object rhs) {
        Entry e1 = (Entry) rhs;
        Entry e2 = (Entry) lhs;

        return e1.compareTo(e2);
    }
}