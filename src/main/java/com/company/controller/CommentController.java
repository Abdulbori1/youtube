package com.company.controller;

import com.company.dto.CommentDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
@Api(tags = "Comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Create Comment", notes = "Method used for create comment USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto, HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentService.create(dto, pId));
    }

    @ApiOperation(value = "Update Comment", notes = "Method used for update comment USER", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentService.update(id, dto, pId));
    }

    @ApiOperation(value = "Delete Comment", notes = "Method used for delete comment USER", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentService.delete(id, pId));
    }

    @ApiOperation(value = "Pagination List Comment ADMIN", notes = "Method used for Pagination List Comment ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.paginationList(page, size, pId));
    }

        @ApiOperation(value = "List Comment USER", notes = "Method used for List Comment USER", nickname = "Mazgi")
    @GetMapping("/adm")
    public ResponseEntity<?> commentList(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentService.commentList(pId));
    }

    @ApiOperation(value = "Profile List Comment ADMIN", notes = "Method used for profile List Comment ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/profile/{id}")
    public ResponseEntity<?> commentListByProfile(@PathVariable("id") Integer id,
                                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentService.commentListByProfile(id));
    }

    @ApiOperation(value = "List Comment By VideoId", notes = "Method used for List Comment By VideoId ( all Profiles )", nickname = "Mazgi")
    @GetMapping("/video/{id}")
    public ResponseEntity<?> commentListByVideo(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentService.commentListByVideo(id));
    }

    @ApiOperation(value = "List Comment By ReplyId", notes = "Method used for List Comment By Reply ( all Profiles )", nickname = "Mazgi")
    @GetMapping("/reply/{id}")
    public ResponseEntity<?> commentListByReply(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentService.commentListByReply(id));
    }
}
