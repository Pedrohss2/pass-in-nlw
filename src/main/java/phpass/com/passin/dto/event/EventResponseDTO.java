package phpass.com.passin.dto.event;

import lombok.Getter;
import phpass.com.passin.domain.event.Event;

@Getter
public class EventResponseDTO {

    EventDTO dto;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.dto = new EventDTO(event.getTitle(), event.getTitle(), event.getDetails(), event.getSlug() , event.getMaximumAttendees(), numberOfAttendees);
    }
}
