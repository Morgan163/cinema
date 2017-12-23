package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class FilmType {
    private long filmTypeID;
    private String folmTypeName;

    public FilmType() {
    }

    public FilmType(long filmTypeID, String folmTypeName) {
        this.filmTypeID = filmTypeID;
        this.folmTypeName = folmTypeName;
    }

    public long getFilmTypeID() {
        return filmTypeID;
    }

    public void setFilmTypeID(long filmTypeID) {
        this.filmTypeID = filmTypeID;
    }

    public String getFolmTypeName() {
        return folmTypeName;
    }

    public void setFolmTypeName(String folmTypeName) {
        this.folmTypeName = folmTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof FilmType)) return false;

        FilmType filmType = (FilmType) o;

        return new EqualsBuilder()
                .append(filmTypeID, filmType.filmTypeID)
                .append(folmTypeName, filmType.folmTypeName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(filmTypeID)
                .append(folmTypeName)
                .toHashCode();
    }
}
