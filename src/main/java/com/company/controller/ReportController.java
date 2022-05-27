package com.company.controller;

import com.company.dto.ReportDTO;
import com.company.enums.ProfileRole;
import com.company.service.ReportService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/report")
@Api(tags = "Report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Create Report", notes = "Method used for Create Report USER", nickname = "Mazgi")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody ReportDTO dto,
                                    HttpServletRequest request) {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.USER);
        return ResponseEntity.ok(reportService.create(dto, pId));
    }

    @ApiOperation(value = "Update Report", notes = "Method used for Update Report USER", nickname = "Mazgi")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Integer id,
                                    HttpServletRequest request)  {
        Integer pId = JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.remove(id, pId));
    }

    @ApiOperation(value = "Report List Pagination ADMIN", notes = "Method used for Report List Pagination ADMIN", nickname = "Mazgi")
    @GetMapping("/adm/list")
    public ResponseEntity<?> listPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "3") int size,
                                  HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.paginationList(page, size));
    }

    @ApiOperation(value = "Report List Pagination USER", notes = "Method used for Report List Pagination USER", nickname = "Mazgi")
    @GetMapping("/adm/list/{id}")
    public ResponseEntity<?> list( @PathVariable("id") Integer id,
                                   HttpServletRequest request) {
        JwtUtil.getIdFromHeader(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(reportService.reportListByUserId(id));
    }
}
