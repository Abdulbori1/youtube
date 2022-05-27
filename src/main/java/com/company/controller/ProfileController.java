package com.company.controller;

import com.company.dto.AttachDTO;
import com.company.dto.ProfileDTO;
import com.company.enums.ProfileRole;
import com.company.service.ProfileService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
@Api(tags = "Profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @ApiOperation(value = "Create Profile", notes = "Method used for Create Profile ADMIN", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO dto,
                                           HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto));
    }

    @ApiOperation(value = "Get By ProfileId", notes = "Method used for Get By ProfileId ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getProfileById(@PathVariable("id") Integer id,
                                            HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getById(id));
    }

    @ApiOperation(value = "Update Profile", notes = "Method used for Update Profile ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ProfileDTO dto,
                                           HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @ApiOperation(value = "Delete Profile", notes = "Method used for Delete Profile ADMIN", nickname = "Mazgi")
    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.delete(id));
    }

    @ApiOperation(value = "Update Profile Email", notes = "Method used for Update Profile Email USER", nickname = "Mazgi")
    @GetMapping("/adm/email")
    public ResponseEntity<?> updateEmail(@RequestBody ProfileDTO dto,
                                         HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        profileService.updateEmail(dto, pId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Profile Password", notes = "Method used for Update Profile Password USER", nickname = "Mazgi")
    @GetMapping("/adm/password")
    public ResponseEntity<?> updatePassword(@RequestBody ProfileDTO dto,
                                            HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        profileService.updatePassword(dto, pId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Profile Profile Detail", notes = "Method used for Update Profile Profile Detail USER", nickname = "Mazgi")
    @GetMapping("/adm/ProfileDetail")
    public ResponseEntity<?> updateProfileDetail(@RequestBody ProfileDTO dto,
                                                 HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        profileService.updateProfileDetail(dto, pId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Update Profile Attach", notes = "Method used for Update Profile Attach USER", nickname = "Mazgi")
    @GetMapping("/adm/ProfileAttach")
    public ResponseEntity<?> updateProfileAttach(@RequestBody ProfileDTO dto,
                                                 HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        profileService.updateProfileAttach(dto, pId);
        return ResponseEntity.ok().build();
    }
}
