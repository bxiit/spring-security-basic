package ru.kors.sprsecexample.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kors.sprsecexample.model.Application;
import ru.kors.sprsecexample.model.MyUser;
import ru.kors.sprsecexample.service.AppService;

import java.util.List;

@RestController
@RequestMapping("api/v1/apps")
@AllArgsConstructor
public class AppController {
    private AppService appService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the unauthorized page";
    }

    @GetMapping("/all-app")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Application> allApplications() {
        return appService.allApplications();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Application applicationByID(@PathVariable int id) {
        return appService.applicationByID(id);
    }

    @PostMapping("/new-user")
    public String addUser(@RequestBody MyUser myUser) {
        appService.addUser(myUser);
        return "User saved";
    }
}
