package tintintti.madonruoka.data;

import java.util.Comparator;

/**
 * Class for comparing Infos
 */
public class EntryComparator implements Comparator<Entry> {

    @Override
    public int compare(Entry lhs, Entry rhs) {
        return rhs.compareTo(lhs);
    }
}