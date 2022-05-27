package com.company.controller;

import com.company.dto.VideoTagDTO;
import com.company.enums.ProfileRole;
import com.company.service.VideoTagService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/video_tag")
@Api(tags = "Video_Tag")
public class VideoTagController {
    @Autowired
    private VideoTagService videoTagService;

    @ApiOperation(value = "Create Tag Video", notes = "Method used for Create Tag Video USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody VideoTagDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoTagService.create(dto, pId));
    }

    @ApiOperation(value = "Delete Tag Video", notes = "Method used for Delete Tag Video USER", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoTagService.delete(id, pId));
    }

    @ApiOperation(value = "Tag Video List", notes = "Method used for Tag Video List USER", nickname = "Mazgi")
    @GetMapping("/public")
    public ResponseEntity<?> getVideoTagListBuVideoId () {
        return ResponseEntity.ok(videoTagService.getVideoTagListBuVideoId());
    }

}
