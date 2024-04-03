package phpass.com.passin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.event.Event;
import phpass.com.passin.domain.event.exceptions.EventFullException;
import phpass.com.passin.domain.event.exceptions.EventNotFoundException;
import phpass.com.passin.dto.attendee.AttendeeIdDTO;
import phpass.com.passin.dto.attendee.AttendeeRequestDTO;
import phpass.com.passin.dto.event.EventIdDTO;
import phpass.com.passin.dto.event.EventRequestDTO;
import phpass.com.passin.dto.event.EventResponseDTO;
import phpass.com.passin.repository.AttendeeRepository;
import phpass.com.passin.repository.EventRepository;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    @Transactional(readOnly = true)
    public EventResponseDTO getEventDetails(String eventId) {
        Event event = this.getEventById(eventId);
        List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendees.size());
    }

    public EventIdDTO createEvent(EventRequestDTO requestDTO) {
        Event event = new Event();
        event.setTitle(requestDTO.title());
        event.setDetails(requestDTO.details());
        event.setMaximumAttendees(requestDTO.maximumAttendees());
        event.setSlug(createSlug(requestDTO.title()));

        eventRepository.save(event);

        return new EventIdDTO(event.getId());
    }

    public AttendeeIdDTO registerAttendOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(eventId, attendeeRequestDTO.email());

        Event event = this.getEventById(eventId);
        List<Attendee> attendees = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendees.size()) throw new EventFullException("Event is full, sorry");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    public Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event not found with id: " + eventId)
        );
    }

    private String createSlug(String text) {
        String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalize.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
