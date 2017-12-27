package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FilmType {
    private long filmTypeID;
    private String filmTypeName;

    public FilmType() {
    }

    public FilmType(String filmTypeName) {
        this.filmTypeName = filmTypeName;
    }

    public FilmType(long filmTypeID, String filmTypeName) {
        this.filmTypeID = filmTypeID;
        this.filmTypeName = filmTypeName;
    }

    public long getFilmTypeID() {
        return filmTypeID;
    }

    public void setFilmTypeID(long filmTypeID) {
        this.filmTypeID = filmTypeID;
    }

    public String getFilmTypeName() {
        return filmTypeName;
    }

    public void setFilmTypeName(String filmTypeName) {
        this.filmTypeName = filmTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof FilmType)) return false;

        FilmType filmType = (FilmType) o;

        return new EqualsBuilder()
                .append(filmTypeName, filmType.filmTypeName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(filmTypeName)
                .toHashCode();
    }
}
