package team2.hackathon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {
    @MockBean
    private UserService userService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsers_allUsersReturned(){
        System.out.println("1");
        User user = new User(BigInteger.ONE, "username", "password");

        //actual
        List<User> actual = new ArrayList<>();
        actual.add(user);

        //expected
        when(userService.getAllUsers()).thenReturn(actual);

        assertEquals(userService.getAllUsers(), actual);

        verify(userService).getAllUsers();
    }

    @Test
    public void getUserById_userReturned(){
        System.out.println("2");

        //Actual
        User user = new User(BigInteger.ONE, "username", "password");

        //Expected
        when(userService.findUserByUserId(eq(BigInteger.ONE))).thenReturn(user);

        assertEquals(userService.findUserByUserId(BigInteger.ONE), user);

        verify(userService).findUserByUserId(any());
    }

    @Test
    public void getUserById_userDoesNotExist(){
        System.out.println("3");
        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.findUserByUserId(eq(BigInteger.ONE))).thenReturn(user);
        when(userService.findUserByUserId(any())).thenReturn(null);

        assertNull(userService.findUserByUserId(BigInteger.TEN));

        verify(userService).findUserByUserId(any());
    }

    @Test
    public void insertNewUser_userInserted(){
        System.out.println("4");

        User user = new User(BigInteger.TWO, "username2", "password2");
        when(userService.insertNewUser(any())).thenReturn(BigInteger.TWO);

        BigInteger id = userService.insertNewUser(user);

        assertSame(id, BigInteger.TWO);

        verify(userService).insertNewUser(any());
    }

    @Test
    public void updateUserPassword_successfulChange(){
        System.out.println("5");

        User user = new User(BigInteger.ONE, "username", "password");
        when(userService.updateUserPassword(any(), eq("password"), anyString())).thenReturn(user.getUserId());

        BigInteger id = userService.updateUserPassword(1, "password", "newPassword");

        assertSame(id, user.getUserId());
        verify(userService).updateUserPassword(any(), anyString(), anyString());
    }


    @Test
    public void updateUserPassword_wrongOldPasswordFail(){
        System.out.println("6");

        User user = new User(BigInteger.ONE, "username", "password");

        when(userService.updateUserPassword(any(), eq("password"), anyString())).thenReturn(user.getUserId());
        when(userService.updateUserPassword(any(), anyString(), anyString())).thenReturn(BigInteger.valueOf(-1));

        BigInteger id = userService.updateUserPassword(1, "wrongPassword", "newPassword");

        assertSame(id, BigInteger.valueOf(-1));
        verify(userService).updateUserPassword(any(), anyString(), anyString());
    }

    @Test
    public void deleteUser_userDeleted(){
        System.out.println("7");

        doNothing().when(userService).deleteUserByUserId(any());
        userService.deleteUserByUserId(BigInteger.ONE);

        verify(userService).deleteUserByUserId(any());
    }

    @Test
    public void deleteAllUsers_usersDeleted(){
        System.out.println("8");

        doNothing().when(userService).deleteAllUsers();
        userService.deleteAllUsers();

        verify(userService).deleteAllUsers();
    }

}
