package phpass.com.passin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phpass.com.passin.domain.event.Event;
import phpass.com.passin.service.EventService;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok().body("Helloo world, here is all ok");
    }

    @GetMapping(value = "{id}")
    public ResponseEntity getEvent(@PathVariable String id) {
        eventService.getEventDetail(id);
        return ResponseEntity.ok().body("event");
    }

}
