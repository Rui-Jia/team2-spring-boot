package team2.hackathon;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserService extends MongoRepository<User, Long> {

    List<User> getAllUsers();

    User findUserByUserId(BigInteger id);

    User findUserByUsername(String username);

    void deleteUserByUserId(BigInteger id);

    BigInteger insertNewUser(User u);

    BigInteger updateUserPassword(long id, String oldPassword, String newPassword);

    void deleteAllUsers();

}
