package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AgeLimitType {
    private long ageLimitID;
    private int ageLimitValue;

    public AgeLimitType(long ageLimitID, int ageLimitValue) {
        this.ageLimitID = ageLimitID;
        this.ageLimitValue = ageLimitValue;
    }

    public long getAgeLimitID() {
        return ageLimitID;
    }

    public void setAgeLimitID(long ageLimitID) {
        this.ageLimitID = ageLimitID;
    }

    public int getAgeLimitValue() {
        return ageLimitValue;
    }

    public void setAgeLimitValue(int ageLimitValue) {
        this.ageLimitValue = ageLimitValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AgeLimitType)) return false;

        AgeLimitType that = (AgeLimitType) o;

        return new EqualsBuilder()
                .append(ageLimitID, that.ageLimitID)
                .append(ageLimitValue, that.ageLimitValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(ageLimitID)
                .append(ageLimitValue)
                .toHashCode();
    }
}
