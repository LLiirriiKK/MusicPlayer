package ru.msc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.msc.model.Artist;
import ru.msc.model.Track;
import ru.msc.repository.ArtistRepository;
import ru.msc.repository.TrackRepository;
import ru.msc.request.TrackAddRq;
import ru.msc.response.MessageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TrackService {
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;
    @Autowired
    public TrackService(TrackRepository trackRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    public ResponseEntity<?> getAllTracks(){
        List<Track> trackList = trackRepository.findAll();
        return ResponseEntity.ok(trackList);
    }

    public ResponseEntity<?> addTrack(TrackAddRq trackAddRq){
        try {
            Track track = new Track();
            track.setTitle(trackAddRq.getTitle());
            track.setFilepath(trackAddRq.getFilepath());
            track.setReleaseDate(new Date());
            trackRepository.save(track);
            return ResponseEntity.ok(new MessageResponse("Track was added successfully"));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(new MessageResponse("Some error while adding the track happened"));
        }
    }

    public ResponseEntity<?> assignArtistToTrack(Integer trackId, Integer artistId) {
        if (trackRepository.findTrackById(trackId).isPresent()) {
            Track track = trackRepository.findTrackById(trackId).get();
            if (artistRepository.findArtistById(artistId).isPresent()) {
                Artist artist = artistRepository.findArtistById(artistId).get();
                track.addArtist(artist);
                trackRepository.save(track);
                artist.addTrack(track);
                artistRepository.save(artist);
                return ResponseEntity.ok(new MessageResponse("Track and artists are assigned"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("No artist with this id"));

        }
        return ResponseEntity.badRequest().body(new MessageResponse("No track with this id"));
    }
}
