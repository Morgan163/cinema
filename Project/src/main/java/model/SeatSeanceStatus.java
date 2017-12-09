package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatSeanceStatus {
    private long statusID;
    private String statusName;

    public SeatSeanceStatus(long statusID, String statusName) {
        this.statusID = statusID;
        this.statusName = statusName;
    }

    public long getStatusID() {
        return statusID;
    }

    public void setStatusID(long statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SeatSeanceStatus)) return false;

        SeatSeanceStatus that = (SeatSeanceStatus) o;

        return new EqualsBuilder()
                .append(statusID, that.statusID)
                .append(statusName, that.statusName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(statusID)
                .append(statusName)
                .toHashCode();
    }
}
