package model.comparators;

import model.Line;

import java.util.Comparator;

/**
 * Created by niict on 24.12.2017.
 */
public class LineComparator implements Comparator<Line> {
    @Override
    public int compare(Line o1, Line o2) {
        return o1.getLineNumber() - o2.getLineNumber();
    }
}
