package com.pm.organizationservice.controller;

import com.pm.organizationservice.dto.OrganizationDto;
import com.pm.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto dto) {
        return ResponseEntity.ok(organizationService.createOrganization(dto));
    }

    @GetMapping("/registry/{registryNumber}")
    public ResponseEntity<OrganizationDto> getByRegistryNumber(@PathVariable String registryNumber) {
        return ResponseEntity.ok(organizationService.getByRegistryNumber(registryNumber));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<OrganizationDto> searchByNormalizedName(@PathVariable String name) {
        return ResponseEntity.ok(organizationService.searchByNormalizedName(name));
    }

    @GetMapping("/search")
    public ResponseEntity<List<OrganizationDto>> searchByYearAndSize(@RequestParam Integer year, @RequestParam Integer size) {
        return ResponseEntity.ok(Collections.singletonList(organizationService.searchByYearAndSize(year, size)));
    }

}
