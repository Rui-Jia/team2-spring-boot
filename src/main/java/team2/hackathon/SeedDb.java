//package team2.hackathon;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//
//@Component
//public class SeedDb {
//    @Autowired
//    private UserService userService;
//    @PostConstruct
//    public void init() {
//        System.out.println("In Seed DB");
//        userService.save(new User(1, "James", "password1"));
//        userService.save(new User(2, "Marie", "password2"));
//        userService.save(new User(3, "Peter", "password3"));
//    }
//    @PreDestroy
//    public void cleanup() {
//        userService.deleteAll();
//    }
//}