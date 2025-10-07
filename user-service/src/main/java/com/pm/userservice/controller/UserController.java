package com.pm.userservice.controller;

import com.pm.userservice.dto.UserDto;
import com.pm.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchByNormalizedName(@RequestParam String normalizedName) {
        return ResponseEntity.ok(userService.searchUsersByNormalizedName(normalizedName));
    }

    @GetMapping("/{id}/organizations")
    public ResponseEntity<List<String>> getUserOrganizations(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserOrganizations(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

}
