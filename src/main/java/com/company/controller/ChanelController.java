package com.company.controller;

import com.company.dto.ChanelDTO;
import com.company.enums.ProfileRole;
import com.company.service.ChanelService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chanel")
@Api(tags = "Chanel")
public class ChanelController {
    @Autowired
    private ChanelService chanelService;

    @ApiOperation(value = "Create Chanel", notes = "Method used for create Chanel USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<ChanelDTO> create(@RequestBody ChanelDTO dto,
                            HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(chanelService.create(dto, pId));
    }

    @ApiOperation(value = "Update Chanel", notes = "Method used for update Chanel USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/update/{id}")
    public ResponseEntity<ChanelDTO> update(@PathVariable("id") Integer id,
                                            @RequestBody ChanelDTO dto,
                                            HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(chanelService.update(id, dto, pId));
    }

    @ApiOperation(value = "Update Chanel Photo", notes = "Method used for update Chanel photo USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/update/photo/{id}")
    public ResponseEntity<ChanelDTO> updateChanelPhoto(@PathVariable("id") Integer id,
                                            @RequestBody ChanelDTO dto,
                                            HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(chanelService.updateChanelPhoto(id, dto, pId));
    }

    @ApiOperation(value = "Update Chanel Banner", notes = "Method used for update Chanel banner USER or ADMIN", nickname = "Mazgi")
    @PutMapping("/adm/update/banner/{id}")
    public ResponseEntity<ChanelDTO> updateChanelBanner(@PathVariable("id") Integer id,
                                                       @RequestBody ChanelDTO dto,
                                                       HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(chanelService.updateChanelBanner(id, dto, pId));
    }

    @ApiOperation(value = "Pagination List Chanel", notes = "Method used for pagination List Channels ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/list")
    public ResponseEntity<?> paginationList(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(chanelService.paginationList(page, size));
    }

    @ApiOperation(value = "Update Chanel Status", notes = "Method used for update chanel status ADMIN or USER", nickname = "Mazgi")
    @PutMapping("/adm/update/status/{id}")
    public ResponseEntity<ChanelDTO> updateChanelStatus(@PathVariable("id") Integer id,
                                                        @RequestBody ChanelDTO dto,
                                                        HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER, ProfileRole.ADMIN);
        return ResponseEntity.ok(chanelService.updateChanelStatus(id, dto, pId));
    }

    @ApiOperation(value = "Get List Chanel", notes = "Method used for get List Channels USER", nickname = "Mazgi")
    @GetMapping("/adm/chanel")
    public ResponseEntity<?> getAllChanelUser(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(chanelService.getAllChanelUser(pId));
    }
}
