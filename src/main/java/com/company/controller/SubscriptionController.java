package com.company.controller;

import com.company.dto.SubscriptionDTO;
import com.company.enums.ProfileRole;
import com.company.service.SubscriptionService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/subscribe")
@Api(tags = "Subscribe")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @ApiOperation(value = "Create Subscribe", notes = "Method used for Create Subscribe USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody SubscriptionDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(subscriptionService.create(dto, pId));
    }

    @ApiOperation(value = "Update Subscribe", notes = "Method used for Update Subscribe USER", nickname = "Mazgi")
    @PutMapping("/adm/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Integer id,
                                          @RequestBody SubscriptionDTO dto,
                                          HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(subscriptionService.updateStatus(id, dto, pId));
    }

    @ApiOperation(value = "Update Subscribe Notification", notes = "Method used for Update Subscribe Notification USER", nickname = "Mazgi")
    @PutMapping("/adm/notification/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable("id") Integer id,
                                          @RequestBody SubscriptionDTO dto,
                                          HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(subscriptionService.updateNotification(id, dto, pId));
    }

    @ApiOperation(value = "Subscribe List", notes = "Method used for Subscribe List USER", nickname = "Mazgi")
    @GetMapping("/adm")
    public ResponseEntity<?> getSubscriptionList(HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(subscriptionService.commentList(pId));
    }

    @ApiOperation(value = "Subscribe List ProfileId ADMIN", notes = "Method used for Subscribe List ProfileId ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/{id}")
    public ResponseEntity<?> getSubscriptionList(@PathVariable("id") Integer id,
                                                 HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(subscriptionService.commentListAdmin(id));
    }
}
