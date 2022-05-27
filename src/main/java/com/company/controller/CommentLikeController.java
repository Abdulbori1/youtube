package com.company.controller;

import com.company.dto.CommentLikeDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentLikeService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/coment_Like")
@Api(tags = "Comment_Like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @ApiOperation(value = "Create Like Comment", notes = "Method used for Create Like Comment USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CommentLikeDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.create(dto, pId));
    }

    @ApiOperation(value = "Remove Like Comment", notes = "Method used for Remove Like Comment USER", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.remove(id, pId));
    }

    @ApiOperation(value = "Profile Like Comment List", notes = "Method used for Profile Like Comment List USER", nickname = "Mazgi")
    @GetMapping("/adm")
    public ResponseEntity<?> getUserCommentLikeList(HttpServletRequest request) {
        Integer pId =JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(commentLikeService.userLikedCommentList(pId));
    }

    @ApiOperation(value = "Profile Like Comment List ADMIN", notes = "Method used for Profile Like Comment List ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getUserCommentLikeListAdm(@PathVariable("id") Integer id,
                                                       HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(commentLikeService.userLikedCommentListAdm(id));
    }
}
