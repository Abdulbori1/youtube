package com.company.controller;

import com.company.dto.VideoDTO;
import com.company.enums.ProfileRole;
import com.company.service.VideoService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/video")
@Api(tags = "Video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "Create Video", notes = "Method used for create video USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody VideoDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(videoService.create(dto, pId));
    }

    @ApiOperation(value = "Update Video", notes = "Method used for update video USER", nickname = "Mazgi")
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<?> updateDetail(@RequestBody VideoDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(videoService.updateVideoDetail(dto, id, pId));
    }

    @ApiOperation(value = "Update Video Status", notes = "Method used for update video status USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/update/status/{id}")
    public ResponseEntity<?> updateStatus(@RequestBody VideoDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(videoService.updateVideoStatus(dto, id, pId));
    }

    @ApiOperation(value = "Video List CategoryId Pagination", notes = "Method used for Video List CategoryId Pagination( all profiles )", nickname = "Mazgi")
    @GetMapping("/list/get/category/{id}")
    public ResponseEntity<?> categoryIdPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  @PathVariable("id") Integer categoryId) {
        return ResponseEntity.ok(videoService.GetVideoPaginationByCategoryId(page, size, categoryId));
    }

    @ApiOperation(value = "Video List TagId Pagination", notes = "Method used for Video List TagId Pagination( all profiles )", nickname = "Mazgi")
    @GetMapping("/list/get/tag/{id}")
    public ResponseEntity<?> tagIdPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  @PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(videoService.GetVideoPaginationByTagId(page, size, tagId));
    }

    @ApiOperation(value = "Video List Key", notes = "Method used for Video List Key ( all profiles )", nickname = "Mazgi")
    @GetMapping("/adm/get/key/{id}")
    public ResponseEntity<?> getByKey(@PathVariable("id") String key,
                                      HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(videoService.getVideoByKey(pId, key));
    }

    @ApiOperation(value = "Video List Title", notes = "Method used for Video List Title ( all profiles )", nickname = "Mazgi")
    @GetMapping("/adm/get/title/{title}")
    public ResponseEntity<?> getByTitle(@PathVariable("title") String title,
                                      HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(videoService.getVideoByTitle(pId, title));
    }

    @ApiOperation(value = "Video List ChanelId Pagination", notes = "Method used for Video List ChanelId Pagination( all profiles )", nickname = "Mazgi")
    @GetMapping("/list/get/chanel/{id}")
    public ResponseEntity<?> chanelIdPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "size", defaultValue = "3") int size,
                                             @PathVariable("id") Integer chanelId) {
        return ResponseEntity.ok(videoService.GetVideoPaginationByChanelId(page, size, chanelId));
    }

    @ApiOperation(value = "Video List Pagination", notes = "Method used for Video List Pagination( all profiles )", nickname = "Mazgi")
    @GetMapping("/list/get")
    public ResponseEntity<?> getVideoPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "3") int size) {
        return ResponseEntity.ok(videoService.GetVideoPaginationBy(page, size));
    }
}
