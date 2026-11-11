package com.app.ecom_application.user.controllers;


import com.app.ecom_application.user.dto.UserRequest;
import com.app.ecom_application.user.dto.UserResponse;
import com.app.ecom_application.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final UserService userService;
//    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
    	System.out.println("REQUEST RECEIVED");
        return new ResponseEntity<>(userService.fetchAllUsers(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        log.info("Fetching user with id: {}", id);
        log.trace("This is TRACE Level - Very Detailed Logs");
        log.debug("This is DEBUG Level - Used for development debugging");
        log.info("This is INFO Level - Used for development debugging");
        log.warn("This is WARN Level - Used for development debugging");
        log.error("This is ERROR Level - Used for development debugging");
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully!!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest updatedUserRequest,
                                             @PathVariable String id){
        boolean updated = userService.updateUser(id, updatedUserRequest);
        if(updated){
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
