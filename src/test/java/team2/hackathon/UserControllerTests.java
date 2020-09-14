package team2.hackathon;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserControllerTests {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;


    @Test
    public void getAllUsers_allUsersReturned() {

        User user = new User(BigInteger.ONE, "username", "password");

        //actual
        List<User> actual = new ArrayList<>();
        actual.add(user);

        when(userService.getAllUsers()).thenReturn(actual);

        ResponseEntity<List<User>> responseEntity = userController.getAllUsers();

        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void getUserById_userReturned() {
        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.findUserByUserId(eq(BigInteger.ONE))).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.getUserByUserId(1l);

        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void getUserById_userDoesNotExist() {
        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.findUserByUserId(eq(BigInteger.ONE))).thenReturn(user);
        when(userService.findUserByUserId(any())).thenReturn(null);

        ResponseEntity<User> responseEntity = userController.getUserByUserId(10l);

        assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void insertNewUser_userInserted() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User(BigInteger.ONE, "username2", "password2");
        when(userService.insertNewUser(any(User.class))).thenReturn(user.getUserId());
        ResponseEntity responseEntity = userController.insertNewUser(user);

        assertEquals(responseEntity.getStatusCodeValue(), 201);
        assertEquals(responseEntity.getHeaders().getLocation().getPath(), "/1");
    }

    @Test
    public void updateUserPassword_successfulChange() {
        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.updateUserPassword(any(), eq("password"), anyString())).thenReturn(user.getUserId());

        ResponseEntity<Long> responseEntity = userController.updateUserPassword(1l, "password", "newPassword");

        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    @Test
    public void updateUserPassword_wrongOldPasswordFail(){
        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.updateUserPassword(any(), eq("password"), anyString())).thenReturn(user.getUserId());
        when(userService.updateUserPassword(any(), anyString(), anyString())).thenReturn(BigInteger.valueOf(-1));

        ResponseEntity<Long> responseEntity = userController.updateUserPassword(1, "wrongPassword", "newPassword");

        assertEquals(responseEntity.getStatusCodeValue(), 404);
    }

    @Test
    public void deleteUser_userDeleted() {
        User user = new User(BigInteger.ONE, "username", "password");

        //method will call find user by id. need a user object.
        when(userService.findUserByUserId(any())).thenReturn(user);

        doNothing().when(userService).deleteUserByUserId(BigInteger.ONE);
        ResponseEntity<String> responseEntity = userController.deleteUser(1l);

        assertEquals(responseEntity.getStatusCodeValue(), 200);

    }

    @Test
    public void deleteUser_userNotExist() {
        User user = new User(BigInteger.ONE, "username", "password");

        //method will call find user by id. need a user object.
        when(userService.findUserByUserId(user.getUserId())).thenReturn(user);
        when(userService.findUserByUserId(any())).thenReturn(null);

        doNothing().when(userService).deleteUserByUserId(BigInteger.TEN);
        ResponseEntity<String> responseEntity = userController.deleteUser(10l);

        assertEquals(responseEntity.getStatusCodeValue(), 404);

    }

    @Test
    public void deleteAllUsers_usersDeleted() {
        doNothing().when(userService).deleteAllUsers();
        ResponseEntity responseEntity = userController.deleteAllUsers();

        assertEquals(responseEntity.getStatusCodeValue(), 200);
    }
}
