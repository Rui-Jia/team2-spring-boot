package team2.hackathon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.math.BigInteger;


@Getter @Setter @ToString
public class User {

    //To help with auto increment. @GeneratedValue not available with MongoDB
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private BigInteger userId;
    private String username;
    private String password;


    public User(BigInteger userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }


    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(o == null || o.getClass() != getClass()) {
            return false;
        }
        User u = (User) o;
        return this.userId == u.userId && this.username.equals(u.username) && this.password.equals(u.password);
    }
}
