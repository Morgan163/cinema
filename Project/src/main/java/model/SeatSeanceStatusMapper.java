package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SeatSeanceStatusMapper
{
    private Seat seat;
    private Seance seance;
    private SeatSeanceStatus seatSeanceStatus;
    private String bookKey;

    public SeatSeanceStatusMapper(Seat seat, Seance seance, SeatSeanceStatus seatSeanceStatus){
        this.seat = seat;
        this.seance = seance;
        this.seatSeanceStatus = seatSeanceStatus;
    }

    public Seat getSeat(){
        return seat;
    }

    public void setSeat(Seat seat){
        this.seat = seat;
    }

    public Seance getSeance(){
        return seance;
    }

    public void setSeance(Seance seance){
        this.seance = seance;
    }

    public SeatSeanceStatus getSeatSeanceStatus(){
        return seatSeanceStatus;
    }

    public void setSeatSeanceStatus(SeatSeanceStatus seatSeanceStatus){
        this.seatSeanceStatus = seatSeanceStatus;
    }

    public String getBookKey(){
        return bookKey;
    }

    public void setBookKey(String bookKey){
        this.bookKey = bookKey;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof SeatSeanceStatusMapper)) return false;
        SeatSeanceStatusMapper that = (SeatSeanceStatusMapper) o;
        return new EqualsBuilder().append(getSeat(), that.getSeat())
                .append(getSeance(), that.getSeance())
                .append(getSeatSeanceStatus(), that.getSeatSeanceStatus())
                .append(getBookKey(), that.getBookKey()).isEquals();
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(17, 37)
                .append(getSeat()).append(getSeance())
                .append(getSeatSeanceStatus()).append(getBookKey())
                .toHashCode();
    }
}
