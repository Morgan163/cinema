package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatType {
    private long seatTypeID;
    private String seatTypeName;

    public SeatType(long seatTypeID, String seatTypeName) {
        this.seatTypeID = seatTypeID;
        this.seatTypeName = seatTypeName;
    }

    public long getSeatTypeID() {
        return seatTypeID;
    }

    public void setSeatTypeID(long seatTypeID) {
        this.seatTypeID = seatTypeID;
    }

    public String getSeatTypeName() {
        return seatTypeName;
    }

    public void setSeatTypeName(String seatTypeName) {
        this.seatTypeName = seatTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SeatType)) return false;

        SeatType seatType = (SeatType) o;

        return new EqualsBuilder()
                .append(seatTypeID, seatType.seatTypeID)
                .append(seatTypeName, seatType.seatTypeName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(seatTypeID)
                .append(seatTypeName)
                .toHashCode();
    }
}
