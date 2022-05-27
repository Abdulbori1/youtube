package com.company.controller;

import com.company.dto.VideoLikeDTO;
import com.company.enums.ProfileRole;
import com.company.service.VideoLikeService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/video_like")
@Api(tags = "Video_Like")
public class VideoLikeController {
    @Autowired
    private VideoLikeService videoLikeService;

    @ApiOperation(value = "Create Like Video", notes = "Method used for Create Like Video USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody VideoLikeDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoLikeService.create(dto, pId));
    }

    @ApiOperation(value = "Delete Like Video", notes = "Method used for Delete Like Video USER", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoLikeService.remove(id, pId));
    }

    @ApiOperation(value = "Like Video List", notes = "Method used for Like Video List USER", nickname = "Mazgi")
    @GetMapping("/adm")
    public ResponseEntity<?> likeList(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoLikeService.commentList(pId));
    }

    @ApiOperation(value = "Like Video List ProfileId", notes = "Method used for Like Video List ProfileId ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> likeListAdmin(@PathVariable("id") Integer id,
                                           HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(videoLikeService.commentListAdmin(id));
    }
}
