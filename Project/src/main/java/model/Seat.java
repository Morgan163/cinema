package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Seat {
    private long seatID;
    private SeatType seatType;
    private Line line;

    public Seat(long seatID, SeatType seatType, Line line) {
        this.seatID = seatID;
        this.seatType = seatType;
        this.line = line;
    }

    public long getSeatID() {
        return seatID;
    }

    public void setSeatID(long seatID) {
        this.seatID = seatID;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        return new EqualsBuilder()
                .append(seatID, seat.seatID)
                .append(seatType, seat.seatType)
                .append(line, seat.line)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(seatID)
                .append(seatType)
                .append(line)
                .toHashCode();
    }
}