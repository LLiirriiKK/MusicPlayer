package ru.msc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msc.model.Track;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Integer> {
    Optional<Track> findTrackById(Integer trackId);

}
