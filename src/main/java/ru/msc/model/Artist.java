package ru.msc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "artistList")
    private Set<Track> trackList = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(Set<Track> trackList) {
        this.trackList = trackList;
    }

    public void addTrack(Track track) {
        trackList.add(track);
    }
}
