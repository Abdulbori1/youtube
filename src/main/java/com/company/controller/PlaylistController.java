package com.company.controller;

import com.company.dto.PlaylistDTO;
import com.company.enums.ProfileRole;
import com.company.service.PlaylistService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/playlist")
@Api(tags = "Playlist")
public class PlaylistController {
    @Autowired
    private PlaylistService playlistService;

    @ApiOperation(value = "Create Playlist", notes = "Method used for Create Playlist USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto,
                                             HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(playlistService.create(dto, pId));
    }

    @ApiOperation(value = "Update Playlist", notes = "Method used for Update Playlist USER", nickname = "Mazgi")
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<PlaylistDTO> update(@PathVariable("id") Integer id,
                                              @RequestBody PlaylistDTO dto,
                                              HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER , ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.update(id, dto, pId));
    }

    @ApiOperation(value = "Update Playlist Status", notes = "Method used for Update Playlist Status USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/update/status/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylistStatus(@PathVariable("id") Integer id,
                                              @RequestBody PlaylistDTO dto,
                                              HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER , ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.updatePlaylistStatus(id, dto, pId));
    }

    @ApiOperation(value = "Delete Playlist", notes = "Method used for Update Playlist USER or ADMIN", nickname = "Mazgi")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable("id") Integer id,
                                                      HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER , ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.deletePlaylist(id, pId));
    }

    @ApiOperation(value = "Playlist List ADMIN", notes = "Method used for Playlist List ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/list")
    public ResponseEntity<?> paginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "3") int size,
                                            HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.paginationList(page, size));
    }

    @ApiOperation(value = "Playlist List ProfileId ADMIN", notes = "Method used for Playlist List ProfileId ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/list/{id}")
    public ResponseEntity<?> paginationList(@PathVariable("id") Integer userId,
                                            HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.getAllChanelUserAdm(userId));
    }

    @ApiOperation(value = "Playlist List USER", notes = "Method used for Playlist List USER", nickname = "Mazgi")
    @GetMapping("/adm/list/pagination")
    public ResponseEntity<?> paginationList(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(playlistService.getAllChanelUser(pId));
    }

    @ApiOperation(value = "Playlist List ChanelId", notes = "Method used for Playlist List ChanelId USER or ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getAllPlaylistChanel(@PathVariable("id") Integer id,
                                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.USER , ProfileRole.ADMIN);
        return ResponseEntity.ok(playlistService.channelPlaylist(id));
    }

    @ApiOperation(value = "Playlist Get By Id", notes = "Method used for PlaylistId get( all Profiles )", nickname = "Mazgi")
    @GetMapping("/playlist/{id}")
    public ResponseEntity<?> getByPlaylist(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(playlistService.getByPlaylist(id));
    }
}
