package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Film {
    private long filmID;
    private String filmName;
    private FilmType filmType;
    private AgeLimitType ageLimitType;

    public Film(long filmID, String filmName, FilmType filmType, AgeLimitType ageLimitType) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.filmType = filmType;
        this.ageLimitType = ageLimitType;
    }

    public long getFilmID() {
        return filmID;
    }

    public void setFilmID(long filmID) {
        this.filmID = filmID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public FilmType getFilmType() {
        return filmType;
    }

    public void setFilmType(FilmType filmType) {
        this.filmType = filmType;
    }

    public AgeLimitType getAgeLimitType() {
        return ageLimitType;
    }

    public void setAgeLimitType(AgeLimitType ageLimitType) {
        this.ageLimitType = ageLimitType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Film)) return false;

        Film film = (Film) o;

        return new EqualsBuilder()
                .append(filmID, film.filmID)
                .append(filmName, film.filmName)
                .append(filmType, film.filmType)
                .append(ageLimitType, film.ageLimitType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(filmID)
                .append(filmName)
                .append(filmType)
                .append(ageLimitType)
                .toHashCode();
    }
}
