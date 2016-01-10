package tintintti.madonruoka.data;

import java.util.Comparator;

/**
 * Class for comparing Infos
 */
public class InfoComparator implements Comparator<Info> {

    @Override
    public int compare(Info lhs, Info rhs) {
        return rhs.compareTo(lhs);
    }
}