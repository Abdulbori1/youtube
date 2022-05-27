package com.company.controller;

import com.company.dto.PlaylistVideoDTO;
import com.company.enums.ProfileRole;
import com.company.repository.PlaylistVideoRepository;
import com.company.service.PlaylistVideoService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/playlist/video")
@Api(tags = "Playlist_video")
public class PlaylistVideoController {
    @Autowired
    private PlaylistVideoService playlistVideoService;

    @ApiOperation(value = "Create Video Playlist", notes = "Method used for Create Video Playlist USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody PlaylistVideoDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(playlistVideoService.create(dto, pId));
    }

    @ApiOperation(value = "Update Video Playlist", notes = "Method used for Update Video Playlist USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody PlaylistVideoDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistVideoService.update(id, dto, pId));
    }

    @ApiOperation(value = "Delete Video Playlist", notes = "Method used for Delete Video Playlist USER or ADMIN", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistVideoService.delete(id, pId));
    }

    @ApiOperation(value = "Video Playlist List Playlist By id", notes = "Method used for Update Video Playlist USER", nickname = "Mazgi")
    @GetMapping("/{playlistId}")
    public ResponseEntity<?> getPlayListId(@PathVariable("playlistId") Integer id) {
        return ResponseEntity.ok(playlistVideoService.getPlaylistId(id));
    }
}
