package ru.msc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.msc.model.Artist;
import ru.msc.model.Track;
import ru.msc.repository.ArtistRepository;
import ru.msc.repository.TrackRepository;
import ru.msc.request.ArtistAddRq;
import ru.msc.response.MessageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ArtistService {

    ArtistRepository artistRepository;

    TrackRepository trackRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, TrackRepository trackRepository) {
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
    }

    public ResponseEntity<?> addArtist(ArtistAddRq artistAddRq) {
        try {
            Artist artist = new Artist();
            artist.setName(artistAddRq.getArtistName());
            artistRepository.save(artist);
            return ResponseEntity.ok(new MessageResponse("Artist was added successfully"));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageResponse("Some error while adding the artist happened"));
        }

    }

    public ResponseEntity<?> assignTrackToArtist(Integer artistId, Integer trackId) {
        if (artistRepository.findArtistById(artistId).isPresent()) {
            Artist artist = artistRepository.findArtistById(artistId).get();
            if (trackRepository.findTrackById(trackId).isPresent()) {
                Track track = trackRepository.findTrackById(trackId).get();
                artist.addTrack(track);
                track.addArtist(artist);
                trackRepository.save(track);
                artistRepository.save(artist);
            }
            return ResponseEntity.ok(new MessageResponse("Track and artists assigned"));

        }
        return null;
    }

    public ResponseEntity<?> getAllTracksByArtist(Integer artistId) {
        if(artistRepository.findArtistById(artistId).isPresent()){
            Artist artist = artistRepository.findArtistById(artistId).get();
            Set<Track> trackList = artist.getTrackList();
            return ResponseEntity.ok(trackList);
        }
        return ResponseEntity.badRequest().body(new MessageResponse("No artist with this id"));
    }
}
