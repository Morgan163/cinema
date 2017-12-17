package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class Line {
    private long lineID;
    private int lineNumber;
    private Theater theater;
    private List<Seat> seats;

    public Line(int lineNumber){
        this.lineNumber = lineNumber;
    }

    public Line(int lineNumber, Theater theater){
        this(lineNumber);
        this.theater = theater;
    }

    public Line(long lineID, int lineNumber, Theater theater) {
        this(lineNumber, theater);
        this.lineID = lineID;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public long getLineID() {
        return lineID;
    }

    public void setLineID(long lineID) {
        this.lineID = lineID;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public void addSeat(Seat seat){
        this.seats.add(seat);
    }

    public void addSeats(List<Seat> seats){
        this.seats.addAll(seats);
    }

    public void insertSeat(int index, Seat seat){
        this.seats.add(index, seat);
    }

    public void setSeat(int index, Seat seat){
        this.seats.set(index, seat);
    }

    public void deleteSeat(int index){
        this.seats.remove(index);
    }

    public Seat getSeat(int index){
        return this.seats.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Line)) return false;

        Line line = (Line) o;

        return new EqualsBuilder()
                .append(lineID, line.lineID)
                .append(lineNumber, line.lineNumber)
                .append(theater, line.theater)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lineID)
                .append(lineNumber)
                .append(theater)
                .toHashCode();
    }
}
