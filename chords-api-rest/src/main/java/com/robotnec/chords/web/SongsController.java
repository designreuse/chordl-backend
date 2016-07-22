package com.robotnec.chords.web;

import com.robotnec.chords.model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
public class SongsController {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{songId}")
    public ResponseEntity<Song> thing(@PathVariable("songId") String songId) {
        return new ResponseEntity<>(new Song(), HttpStatus.OK);
    }

    @RequestMapping("/test")
    public String test() {
        return "OK";
    }

}