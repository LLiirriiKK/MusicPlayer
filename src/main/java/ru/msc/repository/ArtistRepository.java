package ru.msc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.msc.model.Artist;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findArtistById(Integer id);
}
