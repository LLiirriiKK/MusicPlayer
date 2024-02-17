package ru.msc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.msc.model.Artist;
import ru.msc.request.TrackAddRq;
import ru.msc.request.UserLoginRq;
import ru.msc.request.UserRegisterRq;
import ru.msc.request.ArtistAddRq;
import ru.msc.service.ArtistService;
import ru.msc.service.TrackService;
import ru.msc.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainController {

    UserService userService;

    TrackService trackService;

    ArtistService artistService;

    @Autowired
    public MainController(UserService userService, TrackService trackService, ArtistService artistService) {
        this.userService = userService;
        this.trackService = trackService;
        this.artistService = artistService;
    }


    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRq userRegisterRq){
        return userService.registerUser(userRegisterRq);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRq userLoginRq){
        return userService.authUser(userLoginRq);
    }


    @CrossOrigin
    @PostMapping("/addArtist")
    public ResponseEntity<?> addArtist(@RequestBody ArtistAddRq artistAddRq) {
        return artistService.addArtist(artistAddRq);
    }

    @CrossOrigin
    @PostMapping("/addTrack")
    public ResponseEntity<?> addTrack(@RequestBody TrackAddRq trackAddRq) {
        return trackService.addTrack(trackAddRq);
    }

    @CrossOrigin
    @PostMapping("/{trackId}/artists/{artistId}")
    public ResponseEntity<?> assignArtistToTrack(@PathVariable Integer trackId, @PathVariable Integer artistId){
        return trackService.assignArtistToTrack(trackId, artistId);
    }


    @CrossOrigin
    @GetMapping("/getAllTracks")
    public ResponseEntity<?> getAllTracks(){
        return trackService.getAllTracks();
    }

    @CrossOrigin
    @GetMapping("/getAllTracksByArtist/{artistId}")
    public ResponseEntity<?> getAllTracksByArtist(@PathVariable Integer artistId){
        return artistService.getAllTracksByArtist(artistId);
    }

}
