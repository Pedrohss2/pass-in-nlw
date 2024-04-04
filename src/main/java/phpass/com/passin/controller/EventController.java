package phpass.com.passin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import phpass.com.passin.dto.attendee.AttendeeIdDTO;
import phpass.com.passin.dto.attendee.AttendeeRequestDTO;
import phpass.com.passin.dto.attendee.AttendeesListResponseDTO;
import phpass.com.passin.dto.event.EventIdDTO;
import phpass.com.passin.dto.event.EventRequestDTO;
import phpass.com.passin.dto.event.EventResponseDTO;
import phpass.com.passin.service.AttendeeService;
import phpass.com.passin.service.EventService;


@RestController
@RequestMapping("events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

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

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uri) {
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendOnEvent(eventId, attendeeRequestDTO);

        var uriComponents = uri.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uriComponents).body(attendeeIdDTO);
    }


    @GetMapping(value = "/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventsAttendee(@PathVariable String id) {
        AttendeesListResponseDTO attendeesListResponseDTO = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok().body(attendeesListResponseDTO);
    }
}
