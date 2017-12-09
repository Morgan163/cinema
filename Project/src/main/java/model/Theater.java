package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Theater {
    private long theaterID;
    private int theaterNumber;

    public Theater(long theaterID, int theaterNumber) {
        this.theaterID = theaterID;
        this.theaterNumber = theaterNumber;
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
