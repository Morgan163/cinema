package model;

public enum  SeatType {
    VIP(0, "VIP"),
    GENERIC(1, "GENERIC");

    private long seatTypeID;
    private String seatTypeName;

    public static SeatType getById(long id){
        SeatType[] allTypes = values();
        for (SeatType type: allTypes){
            if (type.getSeatTypeID() == id)
                return type;
        }
        throw new RuntimeException("IllegalIdForAgeLimitType");
    }

    SeatType(long seatTypeID, String seatTypeName) {
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
}
