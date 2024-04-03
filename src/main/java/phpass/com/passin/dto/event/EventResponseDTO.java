package phpass.com.passin.dto.event;

import lombok.Getter;
import phpass.com.passin.domain.event.Event;

@Getter
public class EventResponseDTO {

    EventDTO event;

    public EventResponseDTO(Event event, Integer numberOfAttendees) {
        this.event = new EventDTO(event.getId(), event.getTitle(), event.getDetails(), event.getSlug() , event.getMaximumAttendees(), numberOfAttendees);
    }
}
