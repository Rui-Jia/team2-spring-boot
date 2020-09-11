package team2.hackathon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserServiceImpl {

    @Autowired
    private UserService userService;

    //Autogen increase in userid for mongo db
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;


    public List<User> getAllUsers() {
        return userService.findAll();
    }

    public User getUserById(long id) {
        User user = userService.findUserByUserId(id);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Transactional
    public long insertNewUser(User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());
        if(existingUser != null) {
            throw new IllegalArgumentException("This username has already been taken.");
        }
        user.setUserId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        userService.save(user);
        return user.getUserId();
    }

    @Transactional
    public long updateUserPassword(long id, String oldPassword, String newPassword) {
        User user = userService.findUserByUserId(id);
        System.out.println("in updateUserPassword" + user);
        System.out.println(oldPassword + " " + user.getPassword() + " " + oldPassword.equals(user.getPassword()));
        if (user != null && oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
            userService.save(user);
            return user.getUserId();
        } else {
            return -1;
        }
    }

    @Transactional
    public void deleteUser(long id) {
        userService.deleteUserByUserId(id);
    }

    @Transactional
    public void deleteAllUsers() {
        userService.deleteAll();
    }
}
