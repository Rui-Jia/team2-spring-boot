package team2.hackathon;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService us;

    //Get Methods
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = us.getAllUsers();
        log.info(users.toString());
        return ResponseEntity.ok().body(us.getAllUsers());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable long id) {
        User user = us.findUserByUserId(id);
        if(user == null){
            log.error("User with id: %s not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Requested user: " + user.toString());
        return ResponseEntity.ok().body(user);
    }

    //Create new user
    @PostMapping(value = "/user", consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity insertNewUser(@RequestBody User u) {
        long id = 0;
        try {
            id = us.insertNewUser(u);
        } catch(Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("Username has been taken");
        }
        URI uri = URI.create("/" + id);
        log.info("Created new user with id: %s", id);
        return ResponseEntity.created(uri).body(u);
    }

    //Update user password
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<Long> updateUserPassword(@PathVariable long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        long id_returned = us.updateUserPassword(id, oldPassword, newPassword);
        if(id_returned == id) {
            log.info("Updated user password for user with id: %s", id);
            return ResponseEntity.ok().body(id_returned);
        } else if(id_returned == -1){
            log.error("User with id: %s not found", id);
            return ResponseEntity.notFound().build();
        } else {
            log.error("Unexpected error in password update");
            return ResponseEntity.badRequest().build(); //should never reach this
        }
    }

    //Delete users
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        if (us.findUserByUserId(id) != null) {
            us.deleteUserByUserId(id);
            log.info("Deleted user with id: %s", id);
            return ResponseEntity.ok().build();
        } else {
            log.error("User with id: %s not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Delete all users
    @DeleteMapping(value = "/user")
    public ResponseEntity<String> deleteAllUsers() {
        us.deleteAllUsers();
        log.info("All users deleted");
        return ResponseEntity.ok().build();
    }
}