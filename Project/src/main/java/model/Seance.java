package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Calendar;

public class Seance {
    private long seanceID;
    private Film film;
    private double priceValue;
    private Calendar seanceStartDate;

    public Seance() {
    }

    public Seance(long seanceID, Film film, double priceValue, Calendar seanceStartDate) {
        this.seanceID = seanceID;
        this.film = film;
        this.priceValue = priceValue;
        this.seanceStartDate = seanceStartDate;
    }

    public long getSeanceID() {
        return seanceID;
    }

    public void setSeanceID(long seanceID) {
        this.seanceID = seanceID;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public double getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(double priceValue) {
        this.priceValue = priceValue;
    }

    public Calendar getSeanceStartDate() {
        return seanceStartDate;
    }

    public void setSeanceStartDate(Calendar seanceStartDate) {
        this.seanceStartDate = seanceStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Seance)) return false;

        Seance seance = (Seance) o;

        return new EqualsBuilder()
                .append(seanceID, seance.seanceID)
                .append(priceValue, seance.priceValue)
                .append(film, seance.film)
                .append(seanceStartDate, seance.seanceStartDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(seanceID)
                .append(film)
                .append(priceValue)
                .append(seanceStartDate)
                .toHashCode();
    }
}
