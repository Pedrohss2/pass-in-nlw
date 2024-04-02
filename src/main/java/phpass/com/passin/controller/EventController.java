package phpass.com.passin.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
public class EventController {

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok().body("Heloo world, here is all ok");
    }

}
