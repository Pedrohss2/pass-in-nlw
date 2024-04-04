package phpass.com.passin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import phpass.com.passin.domain.attendee.Attendee;
import phpass.com.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import phpass.com.passin.domain.checkin.CheckIn;
import phpass.com.passin.dto.attendee.AttendeeBadgeResponseDTO;
import phpass.com.passin.dto.attendee.AttendeeDetails;
import phpass.com.passin.dto.attendee.AttendeesListResponseDTO;
import phpass.com.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import phpass.com.passin.dto.attendee.AttendeeBadgeDTO;
import phpass.com.passin.repository.AttendeeRepository;
import phpass.com.passin.repository.CheckInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInService checkInService;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendees = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetails = attendees.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetails);
    }

    public Attendee registerAttendee(Attendee attendee) {
        this.attendeeRepository.save(attendee);
        return attendee;
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with id: " + attendeeId));
    }

    public void verifyAttendeeSubscription(String eventId, String email) {
        Optional<Attendee> attendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if(attendeeRegistered.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee is already registry");
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);

        var uriComponents = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO badgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uriComponents, attendee.getId());

        return new AttendeeBadgeResponseDTO(badgeDTO);
    }
}
