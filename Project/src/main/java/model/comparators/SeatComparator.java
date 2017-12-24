package model.comparators;

import model.Seat;

import java.util.Comparator;

/**
 * Created by niict on 24.12.2017.
 */
public class SeatComparator implements Comparator<Seat> {
    @Override
    public int compare(Seat o1, Seat o2) {
        return o1.getNumber() - o2.getNumber();
    }
}
