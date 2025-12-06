package ch.elodin.project.NotesHandler.controller;

import ch.elodin.project.NotesHandler.Repository.AppUserRepository;
import ch.elodin.project.NotesHandler.entity.AppUser;
import ch.elodin.project.NotesHandler.service.UserBootstrapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AppUserController {


    private final AppUserRepository appUserRepository;
    private final UserBootstrapService userBootstrapService;

    public AppUserController(AppUserRepository appUserRepository, UserBootstrapService userBootstrapService) {
        this.appUserRepository = appUserRepository;
        this.userBootstrapService = userBootstrapService;
    }


    @PostMapping("/register")
    public ResponseEntity<AppUser> save(@RequestBody AppUser appUser) {
        AppUser savedUser = appUserRepository.save(appUser);
        userBootstrapService.initializeDefaultData(appUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AppUser> login(@RequestBody AppUser appUser) {
        return appUserRepository.findByEmailAndPassword(appUser.getEmail(), appUser.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<AppUser>> findAll() {
        Iterable<AppUser> users = appUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/debug")
    public String debug() {
        return "AppUserController is working!";
    }

}
