package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Line {
    private long lineID;
    private int lineNumber;
    private Theater theater;

    public Line(long lineID, int lineNumber, Theater theater) {
        this.lineID = lineID;
        this.lineNumber = lineNumber;
        this.theater = theater;
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
