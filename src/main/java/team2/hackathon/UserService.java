package team2.hackathon;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserService extends MongoRepository<User, Long> {

    List<User> getAllUsers();

    User findUserByUserId(long id);

    User findUserByUsername(String username);

    void deleteUserByUserId(long id);

    long insertNewUser(User u);

    long updateUserPassword(long id, String oldPassword, String newPassword);

    void deleteAllUsers();

}
