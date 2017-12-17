package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private long theaterID;
    private int theaterNumber;
    private List<Line> lines;

    public Theater(int theaterNumber) {
        this.theaterNumber = theaterNumber;
        lines = new ArrayList<Line>();
    }

    public Theater(long theaterID, int theaterNumber) {
        this(theaterNumber);
        this.theaterID = theaterID;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    public long getTheaterID() {
        return theaterID;
    }

    public void setTheaterID(long theaterID) {
        this.theaterID = theaterID;
    }

    public int getTheaterNumber() {
        return theaterNumber;
    }

    public void setTheaterNumber(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    public void addLine(Line line){
        this.lines.add(line);
    }

    public void addLines(List<Line> lines){
        this.lines.addAll(lines);
    }

    public void insertLine(int index, Line line){
        this.lines.add(index, line);
    }

    public void setLine(int index, Line line){
        this.lines.set(index, line);
    }

    public void deleteLine(int index){
        this.lines.remove(index);
    }

    public Line getLine(int index){
        return this.lines.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Theater)) return false;

        Theater theater = (Theater) o;

        return new EqualsBuilder()
                .append(theaterID, theater.theaterID)
                .append(theaterNumber, theater.theaterNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(theaterID)
                .append(theaterNumber)
                .toHashCode();
    }
}
