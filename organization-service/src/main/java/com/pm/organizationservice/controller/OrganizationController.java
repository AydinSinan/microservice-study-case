package com.pm.organizationservice.controller;

import com.pm.organizationservice.dto.OrganizationDto;
import com.pm.organizationservice.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @PostMapping
    public ResponseEntity<OrganizationDto> createOrganization(
            @RequestBody OrganizationDto dto,
            @RequestHeader(value = "X-User", required = false) String createdBy) {

        return ResponseEntity.ok(service.createOrganization(dto, createdBy));
    }

    @GetMapping("/registry/{registryNumber}")
    public ResponseEntity<OrganizationDto> getByRegistryNumber(@PathVariable String registryNumber) {
        return ResponseEntity.ok(service.getByRegistryNumber(registryNumber));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrganizationDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage) {

        Page<OrganizationDto> result = service.search(name, year, size, PageRequest.of(page, sizePage));
        return ResponseEntity.ok(result);
    }

}
