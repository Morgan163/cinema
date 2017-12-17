package model;

public enum AgeLimitType {
    MIN_0(0,0),
    MIN_6(1,6),
    MIN_12(2,12),
    MIN_16(3, 16),
    MIN_18(4, 18);
    private long ageLimitID;
    private int ageLimitValue;

    AgeLimitType(long ageLimitID, int ageLimitValue) {
        this.ageLimitID = ageLimitID;
        this.ageLimitValue = ageLimitValue;
    }

    public static AgeLimitType getById(long id){
        AgeLimitType[] allTypes = values();
        for (AgeLimitType type: allTypes){
            if (type.getAgeLimitID() == id)
                return type;
        }
        throw new RuntimeException("IllegalIdForAgeLimitType");
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
}
