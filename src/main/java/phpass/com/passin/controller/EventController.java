package phpass.com.passin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import phpass.com.passin.domain.event.Event;
import phpass.com.passin.dto.event.EventIdDTO;
import phpass.com.passin.dto.event.EventRequestDTO;
import phpass.com.passin.dto.event.EventResponseDTO;
import phpass.com.passin.service.EventService;

import java.net.URI;

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
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO eventResponseDTO = eventService.getEventDetails(id);
        return ResponseEntity.ok().body(eventResponseDTO);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO eventRequestDTO, UriComponentsBuilder uri) {
        EventIdDTO eventIdDTO = this.eventService.createEvent(eventRequestDTO);

        var uriComponents = uri.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uriComponents).body(eventIdDTO);
    }

}
