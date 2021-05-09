package ru.pronichev.phonebook.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pronichev.phonebook.dto.UserDto;
import ru.pronichev.phonebook.services.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> creatNewUser(@RequestBody UserDto userDto) {
        userDto.setId(null);
        return new ResponseEntity<>(userService.saveOrUpdate(userDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveOrUpdate(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

    @GetMapping("/find_by_login")
    public ResponseEntity<List<UserDto>> findByLogin(@RequestParam(value = "login") String login) {
        return new ResponseEntity<>(userService.findByLogin(login), HttpStatus.OK);
    }
}
