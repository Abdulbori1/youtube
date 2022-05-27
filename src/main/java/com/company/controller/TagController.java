package com.company.controller;

import com.company.dto.TagDTO;
import com.company.enums.ProfileRole;
import com.company.service.TagService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tag")
@Api(tags = "Tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @ApiOperation(value = "Create Tag", notes = "Method used for create tag ADMIN", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody TagDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        try {
            return ResponseEntity.ok(tagService.create(dto));
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Already exists: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Update Tag", notes = "Method used for update tag ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody TagDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.update(id, dto));
    }

    @ApiOperation(value = "Tag List", notes = "Method used for tag List ( all profiles )", nickname = "Mazgi")
    @GetMapping("")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(tagService.list());
    }

    @ApiOperation(value = "Delete Tag", notes = "Method used for delete tag ADMIN", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request){
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.delete(id));
    }
}
