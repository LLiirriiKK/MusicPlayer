package ru.msc.request;

public class ArtistAddRq {

    private String artistName;

    public ArtistAddRq() {
    }

    public ArtistAddRq(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
