package phpass.com.passin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.event.Event;
import phpass.com.passin.domain.event.exceptions.EventNotFoundException;
import phpass.com.passin.dto.event.EventIdDTO;
import phpass.com.passin.dto.event.EventRequestDTO;
import phpass.com.passin.dto.event.EventResponseDTO;
import phpass.com.passin.repository.AttendeeRepository;
import phpass.com.passin.repository.EventRepository;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    @Transactional(readOnly = true)
    public EventResponseDTO getEventDetails(String eventId) {
        Event event = this.eventRepository.findById(eventId).orElseThrow(
                () -> new EventNotFoundException("Event not found with id: " + eventId)
        );
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

    private String createSlug(String text) {
        String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalize.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
