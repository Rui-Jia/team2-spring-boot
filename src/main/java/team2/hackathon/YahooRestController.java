package team2.hackathon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/yahoo")
@CrossOrigin
public class YahooRestController {

    @Autowired
    public YahooRestClient yahooRestClient;

    @Autowired
    private UserService userService;

    @GetMapping(value="/getSummary")
    public ResponseEntity<String> getYahooSummary(@RequestHeader("Authorization") long id) {
        if(validateUser(id) == false) {
            return ResponseEntity.status(400).body(null);
        }
        String result = yahooRestClient.getSummary();
        log.info("Yahoo API call to getSummary successful");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/getMovers")
    public ResponseEntity<String> getYahooMovers(@RequestHeader("Authorization") long id) {
        if(validateUser(id) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String result = yahooRestClient.getMovers();
        log.info("Yahoo API call to getMovers successful");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value="/getTrendingTickers")
    public ResponseEntity<String> getYahooTrendingTickers(@RequestHeader("Authorization") long id) {
        if(validateUser(id) == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String result = yahooRestClient.getTrendingTickers();
        log.info("Yahoo API call to getTrendingTickers successful");
        return ResponseEntity.ok().body(result);
    }

    private boolean validateUser(long id) {
        User user = userService.findUserByUserId(BigInteger.valueOf(id));
        if(user == null){
            log.error("User with id: {} not found", id);
            return false;
        }
        return true;
    }
}