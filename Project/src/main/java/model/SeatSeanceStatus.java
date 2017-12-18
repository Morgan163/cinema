package model;

public enum SeatSeanceStatus
{
    FREE(0, "FREE"), RESERVED(1, "RESERVED"), SOLD_OUT(2, "SOLD OUT");
    private long statusID;
    private String statusName;

    SeatSeanceStatus(long statusID, String statusName){
        this.statusID = statusID;
        this.statusName = statusName;
    }

    public static SeatSeanceStatus getById(long id){
        SeatSeanceStatus[] allTypes = values();
        for (SeatSeanceStatus type : allTypes)
        {
            if (type.getStatusID() == id) return type;
        }
        throw new RuntimeException("IllegalIdForAgeLimitType");
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
}
