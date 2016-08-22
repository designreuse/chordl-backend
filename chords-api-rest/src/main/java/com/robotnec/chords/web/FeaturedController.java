package com.robotnec.chords.web;

import com.robotnec.chords.service.SongService;
import com.robotnec.chords.web.dto.FeaturedDto;
import com.robotnec.chords.web.dto.SongDto;
import com.robotnec.chords.web.mapping.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zak <zak@robotnec.com>
 */
@RestController
@RequestMapping(value = "/featured", produces = "application/json;charset=UTF-8")
public class FeaturedController {

    @Autowired
    SongService songService;

    @Autowired
    Mapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<FeaturedDto> getFeatured() {
        List<SongDto> featuredSongs = mapper.mapAsList(songService.getSongs().subList(0, 10), SongDto.class);
        return ResponseEntity.ok(new FeaturedDto(featuredSongs));
    }
}
